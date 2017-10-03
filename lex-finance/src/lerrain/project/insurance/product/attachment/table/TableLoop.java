package lerrain.project.insurance.product.attachment.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;

public class TableLoop implements Serializable
{
	private static final long serialVersionUID = 1L;

	Formula start;
	Formula end;
	Formula step;
	
	String loopVar;
	
	List elementList = new ArrayList();
	
	public TableLoop(Formula start, Formula end, Formula step, String loopVar)
	{
		this.start = start;
		this.end = end;
		this.step = step;
		this.loopVar = loopVar;
	}
	
	public TableLoop(Formula start, Formula end, String loopVar)
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

	public String getLoopVar()
	{
		return loopVar;
	}

	public void setLoopVar(String loopVar)
	{
		this.loopVar = loopVar;
	}
}
