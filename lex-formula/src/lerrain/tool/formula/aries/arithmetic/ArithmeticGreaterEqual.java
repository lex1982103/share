package lerrain.tool.formula.aries.arithmetic;

import java.util.List;

import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class ArithmeticGreaterEqual extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticGreaterEqual()
	{
		super.addSign(">=");
		super.addSign("ge");
		super.setPriority(50);
		super.setFuntion(false);
	}
	
	public ArithmeticGreaterEqual(List paramList)
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
			result = new Boolean(v1.toDecimal().compareTo(v2.toDecimal()) >= 0);
		}
		else
		{
			throw new CalculateException("“大于”逻辑计算要求两方都是数字类型。");
		}
		
		return result;
	}
}