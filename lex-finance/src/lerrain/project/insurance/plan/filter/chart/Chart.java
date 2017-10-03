package lerrain.project.insurance.plan.filter.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Chart implements Serializable
{
	private static final long serialVersionUID = 1L;

	List items = new ArrayList();
	
	int start;
	int end;
	int step = 1;
	
	double max;
	double min;
	
	public Chart()
	{
	}

	public void addLine(ChartLine line)
	{
		items.add(line);
	}
	
	public ChartLine getLine(int lineIndex)	
	{
		return (ChartLine)items.get(lineIndex);
	}

	public int getStart()
	{
		return start;
	}
	
	public void setStart(int start)
	{
		this.start = start;
	}

	public int getEnd()
	{
		return end;
	}
	
	public void setEnd(int end)
	{
		this.end = end;
	}

	public int getStep()
	{
		return step;
	}
	
	public void setStep(int step)
	{
		this.step = step;
	}

	public int size()
	{
		return items.size();
	}

	public double getMax()
	{
		return max;
	}

	public void setMax(double max)
	{
		this.max = max;
	}

	public double getMin()
	{
		return min;
	}

	public void setMin(double min)
	{
		this.min = min;
	}
}
