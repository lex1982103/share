package lerrain.tool.document.typeset.element;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lerrain.tool.document.element.DocumentPanel;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetBuildException;
import lerrain.tool.document.typeset.TypesetParagraph;
import lerrain.tool.document.typeset.TypesetParameters;
import lerrain.tool.document.typeset.TypesetUtil;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Stack;

public class TypesetLoop extends TypesetPanel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	Formula from;
	Formula to;
	Formula step;
	
	String name;
	
	int loopValue;
	Factors varSet;
	
	public TypesetLoop(Formula from, Formula to, Formula step, String name)
	{
		this.from = from;
		this.to = to;
		this.step = step;
		
		this.name = name == null ? "I" : name;
	}
	
	public TypesetLoop(Formula from, Formula to, Formula step)
	{
		this(from, to, step, null);
	}
	
	public TypesetLoop(String name)
	{
		this.name = name == null ? "I" : name;
	}
	
//	public TypesetLoop(Factors varSet)
//	{
//		this.varSet = varSet;
//	}

	public LexElement build(TypesetParameters tvs)
	{
		try
		{
			DocumentPanel dPanel = new DocumentPanel();
			
			if (this.getX() != null)
				dPanel.setX(this.getX().value(tvs));
			else
				dPanel.setX(0);
			
			if (this.getY() != null)
				dPanel.setY(tvs.getDatum() + this.getY().value(tvs));
			else
				dPanel.setY(tvs.getDatum());
			
			TypesetParameters tvs2 = pack(tvs);
//			tvs2.setStreamY(tvs.getStreamY() + dPanel.getY());

			int width = 0, height = 0;

			Object forValue = value == null ? null : value.run(tvs);
			if (value != null && forValue == null) //有循环的list value，不过是空，那么直接跳过
			{
			}
			else if (forValue instanceof Map) // value = "..."
			{
				tvs2.declare(name);

				Map map = (Map)forValue;
				for (Iterator iter = map.keySet().iterator(); iter.hasNext();)
				{
					tvs2.set(name, iter.next());

					int num = getElementNum();
					for (int j = 0; j < num; j++)
					{
						TypesetElement iye = (TypesetElement)this.getElement(j);
						LexElement ile = iye.isShow(tvs2) ? iye.build(tvs2) : null;

						if (ile != null)
						{
							dPanel.add(ile);

							if (ile.getX() + ile.getWidth() > width)
								width = ile.getX() + ile.getWidth();
							if (ile.getY() + ile.getHeight() > height)
								height = ile.getY() + ile.getHeight();
						}
					}
				}
			}
			else if (forValue != null) // value = "..."
			{
				int s = 0;
				if (forValue instanceof List)
					s = ((List)forValue).size();
				else if (forValue instanceof Object[])
					s = ((Object[])forValue).length;
				
				tvs2.declare(name);
//				tvs2.levelIn();
				for (int i = 0; i < s; i++)
				{
					if (forValue instanceof List)
						tvs2.set(name, ((List)forValue).get(i));
					else if (forValue instanceof Object[])
						tvs2.set(name, ((Object[])forValue)[i]);
					
					int num = getElementNum();
					for (int j = 0; j < num; j++)
					{
						TypesetElement iye = (TypesetElement)this.getElement(j);
						LexElement ile = iye.isShow(tvs2) ? iye.build(tvs2) : null;
						
						if (ile != null)
						{
							dPanel.add(ile);
							
							if (ile.getX() + ile.getWidth() > width)
								width = ile.getX() + ile.getWidth();
							if (ile.getY() + ile.getHeight() > height)
								height = ile.getY() + ile.getHeight();
						}
					}
				}
//				tvs2.levelOut();
			}
			else // from="..." to="..." step="..."
			{
				int f = 0, t = 2, s = 1;
				try
				{
					f = Value.intOf(from, tvs);
				}
				catch (Exception e)
				{
					if (TypesetUtil.getMode() == TypesetUtil.MODE_FAIL)
					{
						throw new TypesetBuildException("exception in loop<from>'s formula: " + this.getValue(), e);
					}
					else if (TypesetUtil.getMode() == TypesetUtil.MODE_ALWAYS)
					{
						f = 0;
						System.out.println("exception in loop<from> formula: " + this.getValue() + ", set it 0 instead.");
					}
				}
				
				try
				{
					t = Value.intOf(to, tvs);
				}
				catch (Exception e)
				{
					if (TypesetUtil.getMode() == TypesetUtil.MODE_FAIL)
					{
						throw new TypesetBuildException("exception in loop<to>'s formula: " + this.getValue(), e);
					}
					else if (TypesetUtil.getMode() == TypesetUtil.MODE_ALWAYS)
					{
						t = 1;
						System.out.println("exception in loop<to> formula: " + this.getValue() + ", set it 1 instead.");
					}
				}
				
				try
				{
					s = step == null ? 1 : Value.intOf(step, tvs);
				}
				catch (Exception e)
				{
					if (TypesetUtil.getMode() == TypesetUtil.MODE_FAIL)
					{
						throw new TypesetBuildException("exception in loop<step>'s formula: " + this.getValue(), e);
					}
					else if (TypesetUtil.getMode() == TypesetUtil.MODE_ALWAYS)
					{
						s = 1;
						System.out.println("exception in loop<step> formula: " + this.getValue() + ", set it 1 instead.");
					}
				}
				
				tvs2.declare(name);
//				tvs2.levelIn();
				for (int i = f; i <= t; i += s)
				{
					tvs2.set(name, new Integer(i));
					
					int num = getElementNum();
					for (int j = 0; j < num; j++)
					{
						TypesetElement iye = (TypesetElement)this.getElement(j);
						LexElement ile = iye.isShow(tvs2) ? iye.build(tvs2) : null;
						
						if (ile != null)
						{
							dPanel.add(ile);
							
							if (ile.getX() + ile.getWidth() > width)
								width = ile.getX() + ile.getWidth();
							if (ile.getY() + ile.getHeight() > height)
								height = ile.getY() + ile.getHeight();
						}
					}
				}
//				tvs2.levelOut();
			}
			
//			//基准坐标可以作为推移画板的手段
//			if (height < tvs2.getDatum())
//				height = tvs2.getDatum();
				
			if (this.getWidth() != null)
				dPanel.setWidth(this.getWidth().value(tvs));
			else
				dPanel.setWidth(width);
			
			if (this.getHeight() != null)
				dPanel.setHeight(this.getHeight().value(tvs));
			else
				dPanel.setHeight(height);
			
			resetY(tvs, dPanel);
			
			return dPanel;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			return null;
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
