package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;

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
		
		throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException("错误的str_split运算");
	}
}
