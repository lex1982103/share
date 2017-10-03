package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;

public class FunctionStr implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
		{
			if (v[0] == null)
				return null;
			else
				return v[0].toString();
		}
		else if (v.length == 2)
		{
			String r = (String)v[0];
			int r1 = Value.intOf(v[1]);
			
			return r.substring(r1);
		}
		else if (v.length == 3)
		{
			String r = (String)v[0];
			int r1 = Value.intOf(v[1]);
			int r2 = Value.intOf(v[2]);
			
			return r.substring(r1, r2);
		}
		
		throw new RuntimeException("错误的str运算");
	}
}
