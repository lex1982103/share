package lerrain.tool.formula.aries.arithmetic;

import java.util.List;

import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class ArithmeticSubtract extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticSubtract()
	{
		super.addSign("-");
		super.setPriority(100);
		super.setFuntion(false);
	}
	
	public ArithmeticSubtract(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		Value v1 = Value.valueOf(super.getParameter(0), getter);
		Value v2 = Value.valueOf(super.getParameter(1), getter);
		
		Object result = null;
		
		if (v1.isDecimal() && v2.isDecimal())
		{
			result = v1.toDecimal().subtract(v2.toDecimal());
		}
		else
		{
			throw new CalculateException("减法计算要求两方都是数字类型。");
		}
		
		return result;
	}
}
