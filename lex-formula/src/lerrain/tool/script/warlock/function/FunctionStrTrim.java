package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;

public class FunctionStrTrim implements OptimizedFunction
{
	public Object run(Object[] v, Factors factors)
	{
		if (v[0] == null)
		{
			if (v.length > 1)
				return v[1].toString().trim();
			else
				return null;
		}

		return v[0].toString().trim();
	}
}
