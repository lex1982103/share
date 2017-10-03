package lerrain.tool.formula.aries.arithmetic;

import java.util.List;

import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class ArithmeticOr extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticOr()
	{
		super.addSign("||");
		super.addSign("or");
		super.setPriority(10);
		super.setFuntion(false);
	}
	
	public ArithmeticOr(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		Value v1 = Value.valueOf(super.getParameter(0), getter);
		Value v2 = Value.valueOf(super.getParameter(1), getter);
		
		Object result = null;
		
		if (v1.isType(Value.TYPE_BOOLEAN) && v2.isType(Value.TYPE_BOOLEAN))
		{
			result = new Boolean(v1.booleanValue() || v2.booleanValue());
		}
		else
		{
			throw new CalculateException("“or”逻辑计算要求两方都是boolean类型。");
		}
		
		return result;
	}
}