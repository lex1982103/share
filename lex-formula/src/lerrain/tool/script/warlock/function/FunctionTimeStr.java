package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FunctionTimeStr implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v == null ||v.length == 0)
		{
			return getString(new Date(), "yyyy-MM-dd");
		}
		else if (v.length == 1)
		{
			return getString((Date)v[0], "yyyy-MM-dd");
		}
		else if (v.length == 2)
		{
			return getString((Date)v[0], (String)v[1]);
		}

		throw new RuntimeException("错误的timestr运算");
	}

	public static String getString(Date date, String formatPattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		try
		{
			if ((formatPattern == null) || "".equals(formatPattern))
				formatPattern = "yyyy-MM-dd HH:mm:ss";

			sdf.applyPattern(formatPattern);
			return sdf.format(date);
		}
		catch (Exception e)
		{
			throw new RuntimeException("错误的timestr运算", e);
		}
	}
}
