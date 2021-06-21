package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.warlock.Counter;

public class FunctionAbs implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v[0] instanceof Long)
			return Math.abs((Long)v[0]);
		if (v[0] instanceof Integer || v[0] instanceof Counter)
			return Math.abs(((Number)v[0]).intValue());
		if (v[0] instanceof Number)
			return Math.abs(((Number)v[0]).doubleValue());

		throw new RuntimeException("错误的abs运算");
	}
}
