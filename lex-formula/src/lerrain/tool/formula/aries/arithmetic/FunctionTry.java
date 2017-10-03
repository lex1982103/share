package lerrain.tool.formula.aries.arithmetic;

import java.util.List;

import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;

public class FunctionTry extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public FunctionTry()
	{
		super.addSign("try");
		super.setPriority(1000);
	}
	
	public FunctionTry(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		int size = paramList.size();
		if (size == 2)
		{
			try
			{
				return ((Formula)paramList.get(0)).run(getter);
			}
			catch (Exception e)
			{
				return ((Formula)paramList.get(1)).run(getter);
			}
		}
		else if (size == 1)
		{
			try
			{
				return ((Formula)paramList.get(0)).run(getter);
			}
			catch (Exception e)
			{
				return null;
			}
		}
		
		throw new CalculateException("try运算需要一个或两个参数");
	}
}
