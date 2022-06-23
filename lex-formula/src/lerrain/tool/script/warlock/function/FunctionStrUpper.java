package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;

public class FunctionStrUpper implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
		{
			return v[0].toString().toUpperCase();
		}
		
		throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException("错误的str_upper运算");
	}
}
