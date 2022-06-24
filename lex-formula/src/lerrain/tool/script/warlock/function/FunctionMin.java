package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.ScriptRuntimeException;

import java.util.Date;

public class FunctionMin implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length > 0)
		{
			for (int j = 0; j < v.length; ++j)
			{
				if (v[j] instanceof Date)
				{
					Date min = (Date) v[j];

					for (int i = j + 1; i < v.length; i++)
					{
						if (v[i] == null)
							continue;

						Date d = ((Date) v[i]);
						if (min.after(d))
							min = d;
					}

					return min;
				}
				else if (v[j] instanceof Number)
				{
					Number min = (Number) v[j];

					for (int i = j + 1; i < v.length; i++)
					{
						if (v[i] == null)
							continue;

						Number d = (Number) v[i];
						if (min.longValue() > d.longValue() || min.doubleValue() > d.doubleValue())
							min = d;
					}

					return min;
				}
			}
		}

		throw new ScriptRuntimeException("错误的min运算");	}
}
