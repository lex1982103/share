package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.Stack;

import java.util.Map;

/**
 * 在指定的stack中定义变量
 */
public class FunctionDim implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v[0] instanceof Stack)
		{
			if (v[1] instanceof Map)
			{
				((Stack) v[1]).setAll((Map) v[1]);
			}
			else if (v[1] instanceof String)
			{
				if (v.length > 2)
					((Stack) v[1]).declare((String)v[2], v[3]);
				else
					((Stack) v[1]).declare((String)v[2]);
			}
		}

		return null;
	}
}
