package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FunctionTrim implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		List res = new ArrayList();

		for (Object m : v)
		{
			if (m instanceof List)
			{
				for (Object x : (List) m)
				{
					if (x != null)
						res.add(x);
				}
			}
			else if (m instanceof Object[])
			{
				for (Object x : (Object[]) m)
				{
					if (x != null)
						res.add(x);
				}
			}
			else if (m != null)
			{
				res.add(m);
			}
		}

		return res;
	}
}
