package lerrain.tool.formula.aries.arithmetic;

import java.math.BigDecimal;
import java.util.List;

import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

/**
 * 
 * @author dingliang
 *
 */
public class FunctionPow extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public FunctionPow()
	{
		super.addSign("pow");
		super.setPriority(1000);
	}
	
	public FunctionPow(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		BigDecimal r = null;

		int size = paramList.size();
		if (size == 1)
		{
			Value key = Value.valueOf((Formula)paramList.get(0), getter);
			if(key.isDecimal())
				r = new BigDecimal(Math.pow(key.doubleValue(), 2));
			else
				throw new CalculateException("求幂次方计算要求是数字类型。");
		}
		else if(size == 2)
		{
			Value key = Value.valueOf((Formula)paramList.get(0), getter);
			Value pow = Value.valueOf((Formula)paramList.get(1), getter);
			if(key.isDecimal() && pow.isDecimal())
				r = new BigDecimal(Math.pow(key.doubleValue(), pow.doubleValue()));
			else
				throw new CalculateException("求幂次方计算要求两值都是数字类型。");
		}
		else
		{
			throw new CalculateException("求幂次方计算要求必须带有一个或两个数字类型的参数。");
		}
		
		return r;
	}
}
