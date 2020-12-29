package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;

public class FunctionStrEnd extends OptimizedFunction
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 2)
		{
			String r = (String)v[0];
			String r1 = (String)v[1];
			
			return new Boolean(r.endsWith(r1));
		}
		
		throw new RuntimeException("错误的str_end运算");
	}
}
