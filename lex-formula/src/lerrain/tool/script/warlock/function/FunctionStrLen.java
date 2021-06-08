package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

public class FunctionStrLen implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
		{
			if (v[0] == null)
				return -1;

			String str = v[0].toString();
			if (str == null)
				return -1;

			return str.length();
		}
		
		throw new RuntimeException("错误的str_len运算");
	}
}
