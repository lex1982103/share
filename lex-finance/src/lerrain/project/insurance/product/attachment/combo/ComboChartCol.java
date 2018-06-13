package lerrain.project.insurance.product.attachment.combo;

import java.io.Serializable;

import lerrain.tool.formula.Formula;

public class ComboChartCol implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String code;
	Formula name;
	
	String addCol;
	
	int mode;
	String type;
	
	public ComboChartCol(String code, Formula name, int mode)
	{
		this.code = code;
		this.name = name;
		this.mode = mode;
	}
	
	public int getMode()
	{
		return mode;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public Formula getName()
	{
		return name;
	}
	
	public boolean equals(Object v)
	{
		ComboChartCol col = (ComboChartCol)v;
		if (code == null)
			return super.equals(col);
		
		return col.getCode().equals(code);
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getAddCol()
	{
		return addCol;
	}

	public void setAddCol(String addCol)
	{
		this.addCol = addCol;
	}
}
