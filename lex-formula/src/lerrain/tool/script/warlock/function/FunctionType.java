package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeError;
import lerrain.tool.script.ScriptRuntimeThrow;

import java.util.List;
import java.util.Map;

public class FunctionType extends OptimizedFunction
{
	public Object run(Object[] v, Factors factors)
	{
		if (v[0] == null)
			return null;

		if (v[0] instanceof String)
			return "string";

		if (v[0] instanceof Number)
			return "decimal";

		if (v[0] instanceof Map)
			return "map";

		if (v[0] instanceof List)
			return "list";

		if (v[0] instanceof Object[])
			return "array";

		if (v[0] instanceof ScriptRuntimeThrow)
			return "throw";

		if (v[0] instanceof ScriptRuntimeError)
			return "error";

		if (v[0] instanceof Exception)
			return "exception";

		return "unknown";
	}
}
