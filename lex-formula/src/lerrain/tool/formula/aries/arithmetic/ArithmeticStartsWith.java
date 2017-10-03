package lerrain.tool.formula.aries.arithmetic;

import java.util.List;

import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class ArithmeticStartsWith extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticStartsWith()
	{
		super.addSign("sw");
		super.addSign("startswith");
		super.setPriority(75);
		super.setFuntion(false);
	}
	
	public ArithmeticStartsWith(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		Value v1 = Value.valueOf(super.getParameter(0), getter);
		Value v2 = Value.valueOf(super.getParameter(1), getter);
		
		Object result = null;
		
		if (v1.getType() == Value.TYPE_STRING && v2.getType() == Value.TYPE_STRING)
		{
			result = new Boolean(((String)v1.toString()).startsWith(v2.toString()));
		}
		else
		{
			throw new CalculateException("“startswith”逻辑计算要求两方都是字符串类型。");
		}
		
		return result;
	}
}