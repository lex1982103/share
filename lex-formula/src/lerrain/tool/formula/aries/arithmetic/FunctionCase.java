package lerrain.tool.formula.aries.arithmetic;

import java.util.List;

import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class FunctionCase extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public FunctionCase()
	{
		super.addSign("case");
		super.setPriority(1000);
	}
	
	public FunctionCase(List paramList)
	{
		super.setParameter(paramList);
	}
	
	/*
	 * case(A > 100, -1, 1)
	 * case(A, 1, -1, 2, -1, 3, -1, 1)
	 */
	public Object run(Factors getter)
	{
		int size = paramList.size();
		
//		LexValue key = ((IFormula)paramList.get(0)).getResult(getter);
		
		Value key = null;
		try
		{
			key = Value.valueOf((Formula)paramList.get(0), getter);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
//			System.out.println(paramList.get(0));
//			System.out.println(paramList.get(1));
//			System.out.println(paramList.get(2));
		}
		
		if (size == 3)
		{
			if (key.getType() == Value.TYPE_BOOLEAN)
			{
				if (key.booleanValue())
					return ((Formula)paramList.get(1)).run(getter);
				else
					return ((Formula)paramList.get(2)).run(getter);
			}
		}
		else
		{
			for (int i=1;i<size-1;i+=2)
			{
				Value v = Value.valueOf((Formula)paramList.get(i), getter);
				if (key.equals(v))
					return ((Formula)paramList.get(i+1)).run(getter);
			}
			
			if (size % 2 == 0)
				return ((Formula)paramList.get(size-1)).run(getter);
		}

		throw new CalculateException("case函数必须返回一个值");
	}
}
