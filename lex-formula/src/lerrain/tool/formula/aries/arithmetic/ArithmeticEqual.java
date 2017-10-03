package lerrain.tool.formula.aries.arithmetic;

import java.util.List;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class ArithmeticEqual extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticEqual()
	{
		super.addSign("=");
		super.addSign("==");
		super.setPriority(50);
		super.setFuntion(false);
	}
	
	public ArithmeticEqual(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		Value v1 = Value.valueOf(super.getParameter(0), getter);
		Value v2 = Value.valueOf(super.getParameter(1), getter);
		
		Object result = null;
		
		if (v1.getValue() == null || v2.getValue() == null)
		{
			result = new Boolean(v1.getValue() == v2.getValue());
		}
		else if (v1.isDecimal() && v2.isDecimal())
		{
			result = new Boolean(v1.toDecimal().compareTo(v2.toDecimal()) == 0);
		}
		else if (v1.isType(Value.TYPE_BOOLEAN) && v2.isType(Value.TYPE_BOOLEAN))
		{
			result = new Boolean(v1.booleanValue() == v2.booleanValue());
		}
		else if (v1.getType() == Value.TYPE_STRING && v2.getType() == Value.TYPE_STRING)
		{
			result = new Boolean(v1.toString().equals(v2.toString()));
		}
		else if (v1.getValue() == v2.getValue())
		{
			result = new Boolean(true);
		}
		else
		{
			result = new Boolean(false);
		}
		
		return result;
	}
}