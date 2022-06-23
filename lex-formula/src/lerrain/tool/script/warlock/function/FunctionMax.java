package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;

import java.util.Date;

public class FunctionMax implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length > 0)
		{
			for (int j = 0; j < v.length; ++j)
			{
				if (v[j] instanceof Date)
				{
					Date max = (Date) v[j];

					for (int i = j + 1; i < v.length; i++)
					{
						if (v[i] == null)
							continue;

						Date d = ((Date) v[i]);
						if (max.before(d))
							max = d;
					}

					return max;
				}
				else if (v[j] instanceof Number)
				{
					Number max = (Number) v[j];

					for (int i = j + 1; i < v.length; i++)
					{
						if (v[i] == null)
							continue;

						Number d = (Number) v[i];
						if (max.longValue() < d.longValue() || max.doubleValue() < d.doubleValue())
							max = d;
					}

					return max;
				}
			}
		}
		
		throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException("错误的max运算");
	}
}
