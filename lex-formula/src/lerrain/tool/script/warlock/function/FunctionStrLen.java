package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;

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
		
		throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException("错误的str_len运算");
	}
}
