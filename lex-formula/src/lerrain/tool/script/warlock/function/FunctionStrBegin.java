package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

public class FunctionStrBegin extends FixedFunction
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 2)
		{
			String r = (String)v[0];
			String r1 = (String)v[1];
			
			return new Boolean(r.startsWith(r1));
		}
		
		throw new RuntimeException("错误的str_begin运算");
	}
}
