package lerrain.project.insurance.product.attachment.combo;

import java.io.Serializable;
import java.util.List;

import lerrain.tool.formula.Formula;

public class ComboCol implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final int MODE_ADD		= 1;
	public static final int MODE_ACCUMULATE	= 2;
	public static final int MODE_COVER		= 3;
	
	String code;
	Formula name;
	
	int mode;
	
	List subcol;
	ComboCol parent;
	
	String style = "###########0";
	String addCol;
	
	int row = -1;
	int col = -1;
	
	int priority = 0;
	int order = 0;
	
	Formula value; //值换算公式，为null的话，表示直接显示值
	
	public ComboCol(String code, Formula name, int mode)
	{
		this.code = code;
		this.name = name;
		this.mode = mode;
	}
	
	public ComboCol(Formula name, List subcol)
	{
		this.name = name;
		this.subcol = subcol;
	}
	
	public boolean hasSubCol()
	{
		return subcol != null;
	}
	
	public int getMode()
	{
		return mode;
	}
	
	public List getSubCol()
	{
		return subcol;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public Formula getName()
	{
		return name;
	}

	public String getStyle()
	{
		return style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}

	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public int getCol()
	{
		return col;
	}

	public void setCol(int col)
	{
		this.col = col;
	}

	public String getAddCol()
	{
		return addCol;
	}

	public void setAddCol(String addCol)
	{
		this.addCol = addCol;
	}
	
	public boolean equals(Object v)
	{
		ComboCol col = (ComboCol)v;
		if (code == null)
			return super.equals(col);
		
		return col.getCode().equals(code);
	}

	public List getSubcol()
	{
		return subcol;
	}

	public void setSubcol(List subcol)
	{
		this.subcol = subcol;
	}

	public ComboCol getParent()
	{
		return parent;
	}

	public void setParent(ComboCol parent)
	{
		this.parent = parent;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public Formula getValue()
	{
		return value;
	}

	public void setValue(Formula value)
	{
		this.value = value;
	}

	public int getOrder()
	{
		return order;
	}

	public void setOrder(int order)
	{
		this.order = order;
	}
}
