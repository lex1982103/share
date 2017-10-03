package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

public class FunctionStrIndex implements Function
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
