package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;

public class FunctionInteger implements Function
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

		throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException("错误的int运算");
	}
}
