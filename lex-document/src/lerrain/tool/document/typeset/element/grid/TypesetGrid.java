package lerrain.tool.document.typeset.element.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lerrain.tool.document.LexColor;
import lerrain.tool.document.element.DocumentPanel;
import lerrain.tool.document.element.DocumentText;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetParameters;
import lerrain.tool.document.typeset.element.TypesetElement;
import lerrain.tool.document.typeset.element.TypesetText;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.formula.Value;

public class TypesetGrid extends TypesetElement
{
	Formula header, content;
	Formula colWidth, colAlign;
	
	GridTheme theme;
	
	int rowHeight = 60; //行高
	boolean expand = true; //每一行是否固定行高

	boolean nextHeader = true; //到下一页时，是否重画表头
	int headerPadding = 0;
	LexColor headerColor = LexColor.WHITE;
	LexColor headerBgColor = LexColor.DARK_CYAN;
	LexColor headerBorderColor = LexColor.WHITE;
	
	LexColor innerBorderColor = null;
	
	int measureMode = 0; //标题和内容平方差、标题平方差、内容平方差、内容最大
	int[] colMargin = new int[4];
	
	//临时计算的变量，理论上一个模板在多个并发同时进行排版的时候有可能出现冲突，所以build前面要加锁，这个问题是很容易解决的，以后有时间改一下
	DocumentPanel dPanel;
	
	private void initStyle(TypesetParameters tvs)
	{
		Map style = getStyle();
		if (style != null)
		{
			String str = (String)style.get("line-color");
			if (str != null)
				innerBorderColor = getColor(str);

			str = (String)style.get("header-color");
			if (str != null)
				headerColor = getColor(str);

			str = (String)style.get("header-bgcolor");
			if (str != null)
				headerBgColor = getColor(str);

			str = (String)style.get("header-border-color");
			if (str != null)
				headerBorderColor = getColor(str);
			
			str = (String)style.get("col-margin");
			if (str != null)
			{
				if (str.indexOf(",") >= 0)
				{
					String[] s = str.split(",");
					for (int i=0;i<s.length;i++)
						colMargin[i] = Integer.parseInt(s[i]);
				}
				else
				{
					for (int i=0;i<4;i++)
						colMargin[i] = Integer.parseInt(str);
				}
			}

			String measure = (String)style.get("measure");
			if ("title".equals(measure))
				measureMode = 1;
			else if ("content".equals(measure))
				measureMode = 2;
			else if ("max".equals(measure))
				measureMode = 3;
			else if ("equal".equals(measure))
				measureMode = 4;
		}
	}

	public synchronized LexElement build(TypesetParameters tvs)
	{
		initStyle(tvs);
		
		dPanel = new DocumentPanel();
		dPanel.setSplit(this.isSplit());
		dPanel.setBorder(valueOf(getLeftBorder(), tvs), valueOf(getTopBorder(), tvs), valueOf(getRightBorder(), tvs), valueOf(getBottomBorder(), tvs));
		dPanel.setBorderColor(this.getBorderColor());
		
		if (this.getX() != null)
			dPanel.setX(this.getX().value(tvs));
		else
			dPanel.setX(0);
		
		if (this.getY() != null)
			dPanel.setY(tvs.getDatum() + this.getY().value(tvs));
		else
			dPanel.setY(tvs.getDatum());

		String[][] header = this.header == null ? null : toArray2D(this.header.run(tvs));
		String[][] content = this.content == null ? null : toArray2D(this.content.run(tvs));

		double[] colsw = this.colWidth == null ? null : toArrayF(this.colWidth.run(tvs));
		int[] cols = getCols(tvs, content[0].length, colsw);
		
		int[] aligns = this.colAlign == null ? null : toArrayInt(this.colAlign.run(tvs));
		
		LexColor color = this.getColor();
		LexColor bgColor = this.getBgColor();
		
		if (dPanel.canSplit())
		{
			dPanel.setAdditional("title", headerOf(pack(tvs), header, cols));
			insertTable(pack(tvs), dPanel, 0, content, false, color != null ? color : this.getColor(), bgColor != null ? bgColor : this.getBgColor(), innerBorderColor == null ? -1 : 0, innerBorderColor, aligns, cols);
		}
		else
		{
			int y = insertTable(pack(tvs), dPanel, 0, header, true, headerColor, headerBgColor, headerPadding, headerBorderColor, null, cols);
			insertTable(pack(tvs), dPanel, y, content, false, color != null ? color : this.getColor(), bgColor != null ? bgColor : this.getBgColor(), innerBorderColor == null ? -1 : 0, innerBorderColor, aligns, cols);
		}
		
		if (this.getWidth() != null)
			dPanel.setWidth(this.getWidth().value(tvs));
		
		if (this.getHeight() != null)
			dPanel.setHeight(this.getHeight().value(tvs));
		
		resetY(tvs, dPanel);
		
		return dPanel;
	}
	
	private int getAlign(int v)
	{
		if (v == 1 || v == 4 || v == 7)
			return DocumentText.ALIGN_LEFT;
		if (v == 3 || v == 6 || v == 9)
			return DocumentText.ALIGN_RIGHT;
		return DocumentText.ALIGN_CENTER;
	}
	
	private int getVerticalAlign(int v)
	{
		if (v == 1 || v == 2 || v == 3)
			return DocumentText.ALIGN_TOP;
		if (v == 7 || v == 8 || v == 9)
			return DocumentText.ALIGN_BOTTOM;
		return DocumentText.ALIGN_MIDDLE;
	}

	private int[] getCols(TypesetParameters tvs, int len, double[] colsw)
	{
		int max = this.getWidth() != null ? this.getWidth().value(tvs) : tvs.getPaper().getBody().getWidth().value(tvs);
		int[] cols = new int[len];
		
		if (colsw == null)
		{
			colsw = new double[len];
			for (int i=0;i<len;i++)
				colsw[i] = 1;
		}
		
		double totalw = 0;
		for (int i=0;i<len;i++)
		{
			totalw += colsw[i];
		}
		
		int full = 0;
		for (int i=0;i<len-1;i++)
		{
			cols[i] = (int)Math.round(max * colsw[i] / totalw);
			full += cols[i];
		}
		cols[len - 1] = max - full;
		
		return cols;
	}
	
//	private int[] getSize(LexFont font, String text)
//	{
//		if (text == null)
//			return new int[] {0, 0};
//		
//		int w = 0, h = 0;
//		
//		String s[] = text.split("\n");
//		for (int i=0;i<s.length;i++)
//		{
//			TypesetCoord tc = TypesetText.getTextDimension().getSize(this.getFont(), s[i]);
//			if (tc.x > w)
//				w = tc.x;
//			h += tc.y;
//		}
//		
//		return new int[] {w, h};
//	}
	
	/**
	 * 获取该blank的colspan
	 * @param c
	 * @param i1
	 * @param j1
	 * @return
	 */
	private int getColspan(String[][] c, int i1, int j1)
	{
		for (int i=i1;i<c.length;i++)
		{
			if (i > i1 && c[i][j1] != null)
				break;
			
			for (int j=j1;j<c[0].length;j++)
			{
				if (i == i1 && j == j1)
					continue;
				
				if (c[i][j] != null)
				{
					if (c[i][j].startsWith("@"))
					{
						String[] xy = c[i][j].substring(1).split(",");
						int m = Integer.parseInt(xy[0]);
						int n = Integer.parseInt(xy[1]);
						if (m == i1 && n == j1)
							return j - j1 + 1;
					}
					break;
				}
			}
		}
		
		return 1;
	}
	
	private int getRowspan(String[][] c, int i1, int j1)
	{
		for (int j=j1;j<c[0].length;j++)
		{
			if (j > j1 && c[i1][j] != null)
				break;
			
			for (int i=i1;i<c.length;i++)
			{
				if (i == i1 && j == j1)
					continue;
				
				if (c[i][j] != null)
				{
					if (c[i][j].startsWith("@"))
					{
						String[] xy = c[i][j].substring(1).split(",");
						int m = Integer.parseInt(xy[0]);
						int n = Integer.parseInt(xy[1]);
						if (m == i1 && n == j1)
							return i - i1 + 1;
					}
					break;
				}
			}
		}
		
		return 1;
	}
	
	private DocumentPanel headerOf(TypesetParameters tvs, String[][] content, int[] cols)
	{
		DocumentPanel dPanel = new DocumentPanel();
		dPanel.setSplit(false);
		insertTable(tvs, dPanel, 0, content, true, headerColor, headerBgColor, headerPadding, headerBorderColor, null, cols);
		
		return dPanel;
	}
	
	private int insertTable(TypesetParameters tvs, DocumentPanel dPanel, int y, String[][] content, boolean header, LexColor color, LexColor bgColor, int tableLine, LexColor borderColor, int[] aligns, int[] cols)
	{
		if (content == null || content.length == 0)
			return 0;
		
//		tableLine = 1;
//		bgColor = LexColor.RED;
//		borderColor = LexColor.RED;
		
		int x = 0;
		int width = 0, height = 0;
			
		TypesetText[][] tt = new TypesetText[content.length][content[0].length];
		for (int i=0;i<content.length;i++)
		{
			int lineH = rowHeight;
			
			for (int j=0;j<content[i].length;j++)
			{
				String t = content[i][j];
				if (t != null && !t.startsWith("@"))
				{
					int ww = 0;
					int cs = getColspan(content, i, j);
					int rs = getRowspan(content, i, j);
					for (int k=0;k<cs;k++)
						ww += cols[j + k];
					
					if (expand && rs == 1)
					{
						Object[] r = TypesetText.format(t, this.getFont(), ww, -1);
						int cy = ((Integer)r[0]).intValue() + colMargin[1] + colMargin[3];
						
						if (lineH < cy)
							lineH = cy;
					}
					
					TypesetText tx = new TypesetText();
					tx.setMargin(colMargin);
					tx.setX(x);
					tx.setY(y);
					tx.setWidth(ww);
					tx.setHeight(lineH);
					tx.setValue(FormulaUtil.formulaOf("'" + t + "'"));

					tx.setFixed(false);
					tx.setAlign(getAlign(aligns == null ? 0 : aligns[j]));
					tx.setVerticalAlign(getVerticalAlign(aligns == null ? 0 : aligns[j]));
					tx.setColor(color);
					tx.setBgColor(bgColor);
					tx.setFont(this.getFont());
					tx.setBorderColor(borderColor);
					
					if (theme != null)
					{
						if (header)
							theme.styleTitle(this, tx, i, j);
						else
							theme.styleContent(this, tx, i, j);
					}
					
					tt[i][j] = tx;
				}
				
				x += cols[j];
				if (x > width)
					width = x;
			}

			if (lineH > rowHeight) //本行拉伸到指定位置
			{
				for (int j=0;j<content[i].length;j++)
				{
					if (tt[i][j] != null)
						tt[i][j].setHeight(lineH);
				}
			}

			y += lineH;
			if (y > height)
				height = y;
			
			x = 0;
			for (int j=0;j<content[i].length;j++) //处理前面的跨行到本行的格子
			{
				x += cols[j];
				if (x > width)
					width = x;

				if (content[i][j] != null && content[i][j].startsWith("@"))
				{
					String[] xy = content[i][j].substring(1).split(",");
					int m = Integer.parseInt(xy[0]);
					int n = Integer.parseInt(xy[1]);
					tt[m][n].setWidth(x - tt[m][n].getX().value(tvs));
					tt[m][n].setHeight(y - tt[m][n].getY().value(tvs));
				}
			}
			
			x = 0;
		}
		
		for (int i=0;i<content.length;i++)
		{
			for (int j=0;j<content[i].length;j++)
			{
				if (tt[i][j] != null)
				{
					LexElement dt = tt[i][j].build(tvs);
					if (dt instanceof DocumentPanel)
						dt.setSplit(false);
					dPanel.add(dt);
				}
			}
		}

		dPanel.setWidth(width);
		dPanel.setHeight(height);
		
		return height;
	}
	
	public double[] toArrayF(Object obj)
	{
		if (obj == null)
			return null;
		if (obj instanceof double[])
		{
			return (double[])obj;
		}
		else if (obj instanceof float[])
		{
			double[] s = null;
			float[] l1 = (float[])obj;
			if (l1 != null) 
			{
				s = new double[l1.length];
				for (int i = 0; i < l1.length; i++)
					s[i] = l1[i];
			}
			return s;
		}
		else if (obj instanceof Object[])
		{
			double[] s = null;
			Object[] l1 = (Object[])obj;
			if (l1 != null) 
			{
				s = new double[l1.length];
				for (int i = 0; i < l1.length; i++)
					s[i] = Value.doubleOf(l1[i]);
			}
			return s;
		}
		
		return null;
	}
	
	public int[] toArrayInt(Object obj)
	{
		if (obj == null)
			return null;
		if (obj instanceof int[])
		{
			return (int[])obj;
		}
		else if (obj instanceof Object[])
		{
			int[] s = null;
			Object[] l1 = (Object[])obj;
			if (l1 != null) 
			{
				s = new int[l1.length];
				for (int i = 0; i < l1.length; i++)
					s[i] = Value.intOf(l1[i]);
			}
			return s;
		}
		
		return null;
	}

	public String[] toArray(Object obj)
	{
		if (obj == null)
			return null;
		if (obj instanceof String[])
			return (String[])obj;
		if (obj instanceof Object[])
		{
			Object[] r1 = (Object[])obj;
			String[] r = new String[r1.length];
			for (int i=0;i<r.length;i++)
				r[i] = r1[i] == null ? null : r1[i].toString();
			return r;
		}
		if (obj instanceof List)
		{
			List l1 = (List)obj;
			if (l1.isEmpty())
				return null;
			String[] s = new String[l1.size()];
			for (int i=0;i<s.length;i++)
			{
				Object val = l1.get(i);
				s[i] = val == null ? null : val.toString();
			}
			return s;
		}
		return null;
	}
	
	public String[][] toArray2D(Object obj)
	{
		if (obj == null)
			return null;
		if (obj instanceof String[][])
			return (String[][])obj;
		if (obj instanceof Object[])
		{
			Object[] r1 = (Object[])obj;
			String[][] res = new String[r1.length][];
			for (int i=0;i<r1.length;i++)
			{
				res[i] = toArray(r1[i]);
			}
			return res;
		}
		if (obj instanceof List)
		{
			List l1 = (List)obj;
			if (l1.isEmpty())
				return null;
			List l2 = new ArrayList();
			
			int max = 0;
			for (int i=0;i<l1.size();i++)
			{
				String[] s2 = toArray(l1.get(i));
				if (s2 == null)
					s2 = new String[0];
				if (s2.length > max)
					max = s2.length;
				l2.add(s2);
			}
			
			String[][] s = new String[l2.size()][max];
			for (int i=0;i<l2.size();i++)
			{
				String[] s2 = (String[])l2.get(i);
				for (int j=0;j<s2.length;j++)
					s[i][j] = s2[j];
			}
			return s;
		}
		return null;
	}

	public void setHeader(Formula header) 
	{
		this.header = header;
	}

	public void setContent(Formula content) 
	{
		this.content = content;
	}

	public void setMeasureMode(int measureMode)
	{
		this.measureMode = measureMode;
	}

	public GridTheme getTheme()
	{
		return theme;
	}

	public void setTheme(GridTheme theme)
	{
		this.theme = theme;
	}

	public Formula getColWidth()
	{
		return colWidth;
	}

	public void setColWidth(Formula colWidth)
	{
		this.colWidth = colWidth;
	}

	public Formula getColAlign()
	{
		return colAlign;
	}

	public void setColAlign(Formula colAlign)
	{
		this.colAlign = colAlign;
	}
}
