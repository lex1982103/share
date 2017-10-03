package lerrain.project.insurance.product.attachment.axachart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;

/**
 * @author yang
 * @date 2014-7-17 下午6:21:03
 */
public class AppendYearData implements Serializable
{

	private static final long serialVersionUID = 1L;

	String code;
	Formula start;
	Formula end;
	Formula step;
	String loopVar;
	Formula condition;
	List itemList = new ArrayList();

	public AppendYearData(Formula start, Formula end, Formula step, String loopVar)
	{
		this.start = start;
		this.end = end;
		this.step = step;
		this.loopVar = loopVar;
	}

	public AppendYearData(Formula start, Formula end, String loopVar)
	{
		this(start, end, FormulaUtil.formulaOf("1"), loopVar);
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	public void setCondition(Formula condition)
	{
		this.condition = condition;
	}

	public Formula getCondition()
	{
		return condition;
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

	public void addItem(AppendYearItem item)
	{
		itemList.add(item);
	}

	public List getItemList()
	{
		return itemList;
	}

	public int size()
	{
		return itemList.size();
	}

	public AppendYearItem getItem(int index)
	{
		return (AppendYearItem) itemList.get(index);
	}

}
