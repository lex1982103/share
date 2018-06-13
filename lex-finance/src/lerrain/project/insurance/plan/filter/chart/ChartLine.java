package lerrain.project.insurance.plan.filter.chart;

import java.io.Serializable;

public class ChartLine implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	double[] data = new double[200];	//对应数据
	
	String type;
	
	String name;
	int color;
	
	public ChartLine(String type)
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

	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
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
