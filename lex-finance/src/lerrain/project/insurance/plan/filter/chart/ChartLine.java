package lerrain.project.insurance.plan.filter.chart;

import java.io.Serializable;

public class ChartLine implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_LINE		= 1;
	public static final int TYPE_BAR		= 2;
	
	double[] data = new double[200];	//对应数据
	
	int type;
	
	String name;
	int color;
	
	public ChartLine(int type)
	{
		this.type = type;
	}
	
	public double[] getData()
	{
		return data;
	}
	
	public void setData(int py, double value)
	{
		data[py] = value;
	}

	public int getType()
	{
		return type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	public int getColor()
	{
		return color;
	}
	
	public void setColor(int color)
	{
		this.color = color;
	}
}
