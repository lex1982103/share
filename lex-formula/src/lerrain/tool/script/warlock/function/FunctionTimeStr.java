package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Optimized;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FunctionTimeStr implements OptimizedFunction, Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v == null ||v.length == 0)
		{
			return getString(new Date(), "yyyy-MM-dd");
		}
		else if (v.length == 1)
		{
			return getString(FunctionTime.getDate(v[0]), "yyyy-MM-dd");
		}
		else if (v.length == 2)
		{
			return getString(FunctionTime.getDate(v[0]), (String)v[1]);
		}

		throw new ScriptRuntimeException("错误的timestr运算");
	}

	@Override
	public boolean isFixed(int mode, Code p)
	{
		//程序执行的时候，如果慢当前时间会变，但考虑到通常都很快，如果需要保留同一个时间，程序自己重写一个Time函数覆盖来设定
		if ((mode & Optimized.TIME) != 0)
			return true;

		return p != null && p.isFixed(mode);
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
			throw new ScriptRuntimeException("错误的timestr运算", e);
		}
	}
}
