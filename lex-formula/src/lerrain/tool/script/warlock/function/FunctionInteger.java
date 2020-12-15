package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.warlock.Fixed;

public class FunctionInteger extends FixedFunction
{
	public Object run(Object[] v, Factors factors)
	{
		Object x = v[0];

		if (x instanceof Integer)
			return x;
		if (x instanceof Number)
			return ((Number) x).intValue();
		if (x instanceof String)
			return Integer.parseInt((String)x);

		throw new RuntimeException("错误的int运算");
	}
}
