package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

import java.util.Date;

public class FunctionDateStr implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v == null ||v.length == 0)
		{
			return FunctionTimeStr.getString(new Date(), "yyyy-MM-dd");
		}
		else if (v.length == 1)
		{
			int d = ((Number)v[0]).intValue();
			return FunctionTimeStr.getString(new Date(System.currentTimeMillis() + d * 3600000L * 24), "yyyy-MM-dd");
		}

		throw new RuntimeException("错误的timestr运算");
	}
}
