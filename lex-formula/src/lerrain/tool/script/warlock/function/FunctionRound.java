package lerrain.tool.script.warlock.function;

import java.math.BigDecimal;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;

public class FunctionRound implements Function
{
	public static final double windage =  + 0.00000001f;

	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
			return Integer.valueOf((int)Math.round(Value.doubleOf(v[0]) + windage));
		
		if (v.length == 2)
		{
			int scale = Value.valueOf(v[1]).intValue();
			BigDecimal d = BigDecimal.valueOf(Value.doubleOf(v[0]) + windage);


//			BigDecimal d = BigDecimal.valueOf(Value.doubleOf(v[0]) + 0.0000000001f);
//			BigDecimal d = Value.valueOf(v[0]).toDecimal().add(new BigDecimal(0.0000001f));
//			BigDecimal d = BigDecimal.valueOf(Value.doubleOf(v[0]));
			return d.setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		else if (v.length == 3)
		{
			int scale = Value.valueOf(v[1]).intValue();
			BigDecimal d = BigDecimal.valueOf(Value.doubleOf(v[0]) + windage);

			if ("even".equals(v[2]))
			{
				d = d.setScale(7, BigDecimal.ROUND_FLOOR);
				return d.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
			}
			else
			{
				return d.setScale(scale, BigDecimal.ROUND_HALF_UP);
			}
		}
		
		throw new RuntimeException("错误的round运算");
	}
}
