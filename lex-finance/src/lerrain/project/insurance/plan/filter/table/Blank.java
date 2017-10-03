package lerrain.project.insurance.plan.filter.table;

import java.io.Serializable;

public class Blank implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_TITLE		= 1;
	public static final int TYPE_DATA		= 2;
	public static final int TYPE_NULL		= 3;
	
	float widthScale;
	
	int rowspan = 1;
	int colspan = 1;
	
	String text;
	String align;
	
	int type = TYPE_DATA;
	
	public Blank()
	{
		type = TYPE_NULL;
	}
	
	public Blank(String text)
	{
		this.text = text;
	}
	
	public Blank(int rowspan, int colspan, String text, String align)
	{
		this.rowspan = rowspan;
		this.colspan = colspan;
		
		this.text = text;
		this.align = align;
	}
	
	public Blank(int rowspan, int colspan, String text)
	{
		this.rowspan = rowspan;
		this.colspan = colspan;
		
		this.text = text;
	}
	
	public int getRowspan()
	{
		return rowspan;
	}
	
	public int getColspan()
	{
		return colspan;
	}
	
	public String getText()
	{
		return text;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getAlign()
	{
		return align;
	}

	public void setAlign(String align)
	{
		this.align = align;
	}

	public float getWidthScale()
	{
		return widthScale;
	}

	public void setWidthScale(float widthScale)
	{
		this.widthScale = widthScale;
	}

}
