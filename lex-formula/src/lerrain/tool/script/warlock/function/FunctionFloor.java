package lerrain.tool.script.warlock.function;

import java.math.BigDecimal;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Fixed;

public class FunctionFloor extends FixedFunction
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
			return Integer.valueOf((int)Math.floor(Value.doubleOf(v[0])));
		
		if (v.length == 2)
		{
			int scale = Value.valueOf(v[1]).intValue();
			
			BigDecimal d = Value.valueOf(v[0]).toDecimal();
			return d.setScale(scale, BigDecimal.ROUND_FLOOR);
		}
		
		throw new RuntimeException("错误的floor运算");
	}
}
