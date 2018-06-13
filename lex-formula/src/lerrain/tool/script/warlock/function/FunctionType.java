package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class FunctionType implements Function
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

		return "unknown";
	}
}
