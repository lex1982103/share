package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;

public class FunctionStrIndex extends OptimizedFunction
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 2)
		{
			String r = (String)v[0];
			String r1 = (String)v[1];
			
			return new Integer(r.indexOf(r1));
		}
		
		throw new RuntimeException("错误的str_index运算");
	}
}
