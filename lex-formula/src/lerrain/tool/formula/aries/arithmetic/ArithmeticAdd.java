package lerrain.tool.formula.aries.arithmetic;

import java.math.BigDecimal;
import java.util.List;

import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class ArithmeticAdd extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticAdd()
	{
		super.addSign("+");
		super.setPriority(100);
		super.setFuntion(false);
	}
	
	public ArithmeticAdd(List paramList)
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
			result = v1.toDecimal().add(v2.toDecimal());
		}
		else if ((v1.isDecimal() || v1.isType(Value.TYPE_STRING)) && (v2.isDecimal() || v2.isType(Value.TYPE_STRING)))
		{
			String left = v1.isDecimal() ? v1.toDecimal().toString() : v1.toString();
			String right = v2.isDecimal() ? v2.toDecimal().toString() : v2.toString();
			result = left + right;
		}
		else if (((v1.isType(Value.TYPE_BOOLEAN)) || v1.isDecimal()) && ((v2.isType(Value.TYPE_BOOLEAN)) || v2.isDecimal()))
		{
			BigDecimal val1, val2;
			
			if (v1.isType(Value.TYPE_BOOLEAN))
				val1 = new BigDecimal(v1.booleanValue() ? 1 : 0);
			else
				val1 = v1.toDecimal();

			if (v2.isType(Value.TYPE_BOOLEAN))
				val2 = new BigDecimal(v2.booleanValue() ? 1 : 0);
			else
				val2 = v2.toDecimal();
			
			return val1.add(val2);
		}
		else
		{
			if (v1.getValue() == null || v2.getValue() == null)
				throw new CalculateException("加法计算要求两方都不能为空。v1 - " + v1.getValue() + "，v2 - " + v2.getValue());

			String left = v1.isDecimal() ? v1.toDecimal().toString() : v1.getValue().toString();
			String right = v2.isDecimal() ? v2.toDecimal().toString() : v2.getValue().toString();
			result = left + right;
		}
		
		return result;
	}
}
