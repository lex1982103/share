package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;

public class FunctionStrRight implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 2)
		{
			String str = v[0].toString();
			int s = Value.intOf(v[1]);

			int len = str.length();
			if (len <= s)
				return str;
			else
				return str.substring(len - s, len);
		}
		
		throw new RuntimeException("错误的str_right运算");
	}
}
