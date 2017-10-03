package lerrain.project.insurance.product.attachment.axachart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;

public class AppendAxis implements Serializable
{
	private static final long serialVersionUID = 1L;

	Formula start;
	Formula end;
	Formula step;
	
	String axisVar;
	
	List elementList = new ArrayList();
	
	public AppendAxis(Formula start, Formula end, Formula step, String axisVar)
	{
		this.start = start;
		this.end = end;
		this.step = step;
		this.axisVar = axisVar;
	}
	
	public AppendAxis(Formula start, Formula end, String loopVar)
	{
		this(start, end, FormulaUtil.formulaOf("1"), loopVar);
	}
	
	public void addElement(Object element)
	{
		elementList.add(element);
	}
	
	public Object getElement(int index)
	{
		return elementList.get(index);
	}
	
	public int getElementNum()
	{
		return elementList.size();
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

	public Formula getStep()
	{
		return step;
	}

	public void setStep(Formula step)
	{
		this.step = step;
	}

	public String getAxisVar()
	{
		return axisVar;
	}

	public void setAxisVar(String axisVar)
	{
		this.axisVar = axisVar;
	}

}
