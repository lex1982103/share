package lerrain.project.insurance.product.attachment.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Formula;

public class ChartDef implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	Formula start;
	Formula end;
	
	String varName;
	
	List items = new ArrayList();
	
	public ChartDef(Formula start, Formula end)
	{
		this.start = start;
		this.end = end;
	}
	
	public void addItem(ChartItem item)
	{
		items.add(item);
	}
	
	public ChartItem getItem(int index)
	{
		return (ChartItem)items.get(index);
	}
	
	public int size()
	{
		return items.size();
	}

	public Formula getStart()
	{
		return start;
	}

	public void setStart(Formula start)
	{
		this.start = start;
	}

	public Formula getEnd()
	{
		return end;
	}

	public void setEnd(Formula end)
	{
		this.end = end;
	}

	public String getVarName()
	{
		return varName;
	}

	public void setVarName(String varName)
	{
		this.varName = varName;
	}
}
