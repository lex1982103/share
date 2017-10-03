package lerrain.tool.document.typeset;

import lerrain.tool.document.LexDocument;
import lerrain.tool.document.LexPage;
import lerrain.tool.document.element.DocumentPanel;
import lerrain.tool.document.element.DocumentReset;
import lerrain.tool.document.element.DocumentText;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.element.TypesetElement;
import lerrain.tool.document.typeset.element.TypesetPanel;
import lerrain.tool.document.typeset.element.TypesetText;
import lerrain.tool.document.typeset.environment.TextDimensionSimple;
import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;

import java.util.*;

public class TypesetDocument extends LexDocument
{
	private static final long	serialVersionUID	= 1L;
	
	Typeset typeset;
	
	boolean fill;
	
	public TypesetDocument(Typeset typeset)
	{
		this.typeset = typeset;
		
		check();
	}
	
	public void check()
	{
		if (TypesetText.getTextDimension() == null)
		{
			System.out.println("Typeset - WARN: TextDimension is null，");
			System.out.println("Typeset - Call TypesetUtil.setTextDimension(ITextDimension textDimension) first.");
			System.out.println("Typeset - Use TextDimensionSimple instead now, the text may be out of shape.");
			System.out.println("Typeset - If the system has graphics environment, you can use TextDimensionAwt for better result.");
			
			TypesetUtil.setTextDimension(new TextDimensionSimple());
		}
	}
	
	public void build(Map map)
	{
		build(new TypesetParameters(map));
	}
	
	public void build(Factors factors)
	{
		build(new TypesetParameters(factors));
	}

	private void build(TypesetParameters tvs)
	{
		initParameter(tvs);
		
		Iterator iter = typeset.getFontFamilies().values().iterator();
		while (iter.hasNext())
			((TypesetFontFamily)iter.next()).build(tvs);

		int num = typeset.getParagraphNum();
		for(int i = 0; i < num; i++)
		{
			try
			{
				Object paragraph = typeset.getParagraph(i);

				if (paragraph instanceof TypesetParagraph)
					buildParagraph((TypesetParagraph)paragraph, tvs);
				else if (paragraph instanceof TypesetParagraphLoop)
					((TypesetParagraphLoop)paragraph).build(tvs, this);
			}
			catch (TypesetBuildException e)
			{
				e.printStackTrace();
			}
		}


		//替换总页数
		int total = this.size();
		Map pageMap = (Map)tvs.get("PAGE");
		pageMap.put("TOTAL", total);

		for (int i=0;i<total;i++)
		{
			LexPage page = this.getPage(i);
			int c = page.getElementNum();
			for (int j=0;j<c;j++)
			{
				resetAtFinal(page.getElement(j), tvs);
			}
		}
	}

	protected void buildParagraph(TypesetParagraph paragraph, TypesetParameters tvs)
	{
		//是否跳过改段落
		boolean pass = false;

		Formula when = paragraph.getVisible();
		try
		{
			pass = when == null ? true : Value.booleanOf(when, tvs);
		}
		catch (Exception e)
		{
			if (TypesetUtil.getMode() == TypesetUtil.MODE_FAIL)
			{
				throw new TypesetBuildException("exception in condition's formula: " + when, e);
			}
			else if (TypesetUtil.getMode() == TypesetUtil.MODE_ALWAYS)
			{
				pass = true;

				System.out.println("exception in condition's formula: " + when + ", set it TRUE instead.");
			}
		}

		if (pass)
		{
			//段落模板构建
			TypesetPaper tPaper = paragraph.getTypesetPaper();
			if (tPaper == null)
				tPaper = typeset.getPageDefineDefault();

			tvs.setY(0);
			tvs.setDatum(0);
			tvs.setWindage(0);
			tvs.setPaper(tPaper);

			TypesetPanel body = tPaper.getBody();
			int bodyX = body.getX().value(tvs);
			int bodyY = body.getY().value(tvs);
			int bodyHeight = tPaper.getBody().getHeight().value(tvs);

			//页面坐标
			tvs.setPageTop(0);
			tvs.setPageBottom(bodyHeight);

			newPage(tPaper, tvs);

			//基准坐标
			tvs.setY(0);
			tvs.setDatum(0);
			tvs.setWindage(0);

			//段落排版
			List list = new ArrayList();
			int elementNum = paragraph.getElementNum();
			for (int j = 0; j < elementNum; j++)
			{
				try
				{
					TypesetElement tElement = paragraph.getElement(j);
					LexElement lElement = tElement.isShow(tvs) ? tElement.build(tvs) : null;

					if (lElement != null)
						list.add(lElement);
				}
				catch (TypesetBuildException e)
				{
					e.printStackTrace();
				}
			}
			
			//重置基准坐标，避免在构建模板时候使用错误的基准坐标
			tvs.setY(0);
			tvs.setDatum(0);
			tvs.setWindage(0);

			int paraENum = list.size();
			for (int j = 0; j < paraENum; j++)
			{
				addElement((LexElement)list.get(j), tvs, tPaper, bodyX, bodyY, bodyHeight);
			}

			//每一个章节的最后一页如果仅仅是指针移到该页,该页却没有任何内容的话,就移去该页.
			if (!fill)
				this.removePage(this.size() - 1);
		}
	}
	
	private void resetAtFinal(LexElement element, TypesetParameters tvs)
	{
		if (element instanceof DocumentText)
		{
			DocumentText text = (DocumentText)element;
//			if (text.hasMode(LexElement.MODE_RESET_AT_FINAL) && text.getPlus() instanceof Formula)
//			{
//				text.setText(Value.stringOf((Formula) text.getPlus(), tvs));
//			}
			if (text.getResetAtFinal() != null)
				text.getResetAtFinal().run(tvs);

//			String t = text.getText();
//			if (t != null)
//				text.setText(t.replaceAll("[%][$]TOTALPAGE[$][%]", this.size() + ""));
		}
		else if (element instanceof DocumentPanel)
		{
			DocumentPanel panel = (DocumentPanel)element;
			int c = panel.getElementCount();
			for (int i=0;i<c;i++)
			{
				resetAtFinal(panel.getElement(i), tvs);
			}
		}
	}
	
	/**
	 * 再LexPage上添加一个LexElement元素
	 * 避免DocumentPanel过大，跨页时不可分割造成大片空白，所以拆除DocumentPanel，将上面的元素直接放在页面上
	 * 
	 * @param page
	 * @param element
	 * @param pageTop
	 * @param pageBottom
	 * @param tvs
	 * @param tPaper
	 * @param bodyX
	 * @param bodyY
	 * @param bodyHeight
	 * @throws CalculateException
	 * @throws TypesetElementBuildException
	 */
	private void addElement(LexElement element, TypesetParameters tvs, TypesetPaper tPaper, int bodyX, int bodyY, int bodyHeight)
	{
		if (element instanceof DocumentPanel && element.canSplit() && ((DocumentPanel)element).getType() == 0)
		{
			DocumentPanel dp = (DocumentPanel)element;
			DocumentPanel title = (DocumentPanel)dp.getAdditional("title");
			
			int count = dp.getElementCount();
			for (int k = 0; k < count; k++)
			{
				LexElement panelElement = (LexElement)dp.getElement(k);
				
				panelElement.setX(panelElement.getX() + dp.getX());
				panelElement.setY(panelElement.getY() + dp.getY());
				
				if (title != null)
				{
					//panel嵌套的原因，在上面的setY中加windage会造成重复添加，所以在add到页面上才添加，但是前面判断跨页也需要windage，所以加上
					if (panelElement.getY() + panelElement.getHeight() + tvs.getWindage() > tvs.getPageBottom())
					{
						tvs.setPageTop(panelElement.getY() + tvs.getWindage());
						tvs.setPageBottom(tvs.getPageTop() + bodyHeight);
						
						newPage(tPaper, tvs);
						
						int x = title.getX() + dp.getX();
						int y = panelElement.getY();
						
//						int tc = title.getElementCount();
//						for (int l = 0; l < tc; l++)
//						{
//							LexElement titleElement = (LexElement)title.getElement(l);
//							try
//							{
//								LexElement tElement = (LexElement)titleElement.copy();
//								
//								tElement.setX(tElement.getX() + x);
//								tElement.setY(tElement.getY() + y);
//								
//								addElement(tElement, tvs, tPaper, bodyX, bodyY, bodyHeight);
//							} 
//							catch (Exception e)
//							{
//								e.printStackTrace();
//							}
//						}
						
						DocumentPanel titleCopy = (DocumentPanel)title.copy();
						titleCopy.setX(x);
						titleCopy.setY(y);
						addElement(titleCopy, tvs, tPaper, bodyX, bodyY, bodyHeight);

						tvs.setWindage(tvs.getWindage() + title.getHeight());
					}
					else if (k == 0)
					{
						if (panelElement.getY() + title.getHeight() + tvs.getWindage() > tvs.getPageBottom())
						{
							tvs.setPageTop(panelElement.getY() + tvs.getWindage());
							tvs.setPageBottom(tvs.getPageTop() + bodyHeight);
							
							newPage(tPaper, tvs);
						}
						
						int x = title.getX() + dp.getX();
						int y = panelElement.getY();
						
//						/*
//						 * 避免DocumentPanel过大，跨页时不可分割造成大片空白，所以拆除DocumentPanel，将上面的元素直接放在页面上
//						 */
////						LexElement titleCopy = title.copy();
////						titleCopy.setX(titleCopy.getX() + x);
////						titleCopy.setY(titleCopy.getY() + y);
////						addElement(titleCopy, tvs, tPaper, bodyX, bodyY, bodyHeight);
//						
//						int tc = title.getElementCount();
//						for (int l = 0; l < tc; l++)
//						{
//							LexElement titleElement = (LexElement)title.getElement(l);
//							try
//							{
//								LexElement tElement = (LexElement)titleElement.copy();
//								
//								tElement.setX(tElement.getX() + x);
//								tElement.setY(tElement.getY() + y);
//								
//								addElement(tElement, tvs, tPaper, bodyX, bodyY, bodyHeight);
//							} 
//							catch (Exception e)
//							{
//								e.printStackTrace();
//							}
//						}
						
						DocumentPanel titleCopy = (DocumentPanel)title.copy();
						titleCopy.setX(x);
						titleCopy.setY(y);
						addElement(titleCopy, tvs, tPaper, bodyX, bodyY, bodyHeight);
						
						tvs.setWindage(tvs.getWindage() + title.getHeight());
					}
				}
				
				addElement(panelElement, tvs, tPaper, bodyX, bodyY, bodyHeight);
			}
		}
		else if (element instanceof DocumentPanel && element.canSplit() && element.getY() + element.getHeight() > tvs.getPageBottom())
		{
			DocumentPanel dp = (DocumentPanel) element;
			int count = dp.getElementCount();
			for (int k = 0; k < count; k++)
			{
				LexElement panelElement = (LexElement) dp.getElement(k);
				panelElement.setX(panelElement.getX() + dp.getX());
				panelElement.setY(panelElement.getY() + dp.getY());
				addElement(panelElement, tvs, tPaper, bodyX, bodyY, bodyHeight);
			}
		}
		else if (element instanceof DocumentReset)
		{
			DocumentReset lexElement = (DocumentReset)element;
			lexElement.setY(lexElement.getY() + tvs.getWindage());

			//
			tvs.setPageTop(lexElement.getY());
			tvs.setPageBottom(tvs.getPageTop() + bodyHeight);
			
			//如果这个newpage在最后，应该删除，没有内容的页面不保留。
			newPage(tPaper, tvs);
			
			//tvs.setWindage(0);
		}
		else if (element != null)
		{
			LexElement lexElement = (LexElement)element;
			lexElement.setY(lexElement.getY() + tvs.getWindage());
			
			if (lexElement.getY() + lexElement.getHeight() > tvs.getPageBottom())
			{
				tvs.setPageTop(lexElement.getY());
				tvs.setPageBottom(tvs.getPageTop() + bodyHeight);
				
				newPage(tPaper, tvs);
			}

			lexElement.setX(bodyX + lexElement.getX());
//			int y = bodyY + lexElement.getY() - tvs.getPageTop();
//			if (y < 0)
//				y = 0;
			
			lexElement.setY(bodyY + lexElement.getY() - tvs.getPageTop());
			this.getPage(this.size() - 1).add(lexElement);
			
			fill = true;
		}
	}
	
	private void initParameter(TypesetParameters tvs)
	{
		Map systemMap = new HashMap();
		systemMap.put("PRINT_TIME", new Date().toString());
		tvs.set("SYSTEM", systemMap);
		
		if (tvs.get("RESOURCE_PATH") == null)
			tvs.set("RESOURCE_PATH", TypesetUtil.getResourcePath());

		Map pageMap = new HashMap();
		pageMap.put("SEQUENCE", null);
		pageMap.put("TOTAL", null);
		tvs.set("PAGE", pageMap);

//		if (tvs.get("FONT_PATH") == null)
//			tvs.set("FONT_PATH", new HashMap());
	}
	
	private LexPage newPage(TypesetPaper tPaper, TypesetParameters tvs)
	{
		LexPage page = new LexPage();
		page.setPaper(tPaper.getPaper());
		this.addPage(page);
		
		Map pageMap = (Map)tvs.get("PAGE");
		pageMap.put("SEQUENCE", new Integer(this.size()));

		int templateCount = tPaper.getTemplateNum();
		for (int j = 0; j < templateCount; j++) 
		{
			TypesetPanel tp = tPaper.getTemplate(j);
			LexElement lElement = tp.build(tvs);
			
			if (lElement != null)
				page.add(lElement);
		}
		
		fill = false;

		return page;
	}
}
