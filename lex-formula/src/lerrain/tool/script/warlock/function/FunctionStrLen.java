package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

public class FunctionStrLen implements Function
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
