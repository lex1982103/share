package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.ScriptRuntimeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionMatch implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length > 1)
		{
			Pattern pattern = Pattern.compile((String)v[0]);

			int r = 0;
			for (int i = 1; i < v.length; i++)
			{
				Matcher matcher = pattern.matcher((String) v[i]);
				r += matcher.matches() ? 1 : 0;
			}

			return r;
		}
		
		throw new ScriptRuntimeException("错误的match运算");
	}
}
