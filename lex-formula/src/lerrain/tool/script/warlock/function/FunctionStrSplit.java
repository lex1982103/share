package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

public class FunctionStrSplit implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 2)
		{
			String s = (String)v[0];
			if (s == null)
				return null;

			String r = (String)v[1];
			return s.split(r);
		}
		
		throw new RuntimeException("错误的str_split运算");
	}
}
