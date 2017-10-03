package lerrain.project.insurance.product.attachment.chart;

import java.io.Serializable;

import lerrain.tool.formula.Formula;

public class ChartItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_LINE	= 1;
	public static final int TYPE_BAR	= 2;
	
	int type;
	Formula formula;
	
	String name;
	String color;
	
	public ChartItem(int type, Formula formula)
	{
		this.type = type;
		this.formula = formula;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public Formula getFormula()
	{
		return formula;
	}

	public void setFormula(Formula formula)
	{
		this.formula = formula;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}
}
