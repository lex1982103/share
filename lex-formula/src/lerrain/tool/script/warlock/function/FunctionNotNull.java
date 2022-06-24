package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

public class FunctionNotNull implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		for (Object o : v)
			if (o != null)
				return o;

		return null;
	}
}
