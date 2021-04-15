package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

public class FunctionStrUpper implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
		{
			return v[0].toString().toUpperCase();
		}
		
		throw new RuntimeException("错误的str_upper运算");
	}
}
