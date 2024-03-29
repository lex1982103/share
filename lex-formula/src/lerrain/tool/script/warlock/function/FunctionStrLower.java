package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.ScriptRuntimeException;

public class FunctionStrLower implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
		{
			return v[0].toString().toLowerCase();
		}
		
		throw new ScriptRuntimeException("错误的str_lower运算");
	}
}
