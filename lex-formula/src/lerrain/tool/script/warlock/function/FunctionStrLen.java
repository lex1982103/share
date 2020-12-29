package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;

public class FunctionStrLen extends OptimizedFunction
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
		{
			return v[0].toString().length();
		}
		
		throw new RuntimeException("错误的str_len运算");
	}
}
