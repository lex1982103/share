package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;

public class FunctionStrReplace implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 3)
		{
			String r = (String)v[0];
			String r1 = (String)v[1];
			String r2 = (String)v[2];
			
			return r.replaceAll(r1, r2);
		}
		else if (v.length == 4)
		{
			String r = (String)v[0];
			String r1 = (String)v[1];
			String r2 = (String)v[2];

			int num = Value.intOf(v[3]);
			for (int i=0;i<num;i++)
				r = r.replace(r1, r2);
			return r;
		}

		throw new RuntimeException("错误的str_replace运算");
	}
}
