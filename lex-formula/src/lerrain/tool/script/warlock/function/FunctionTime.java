package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Optimized;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FunctionTime implements OptimizedFunction
{
	public Object run(Object[] v, Factors factors)
	{
		if (v == null ||v.length == 0)
		{
			return new Date();
		}
		else if (v.length == 1)
		{
			return getDate(v[0]);
		}
		else
		{
			String str = v[0] == null ? "00000000000000" : FunctionTimeStr.getString((Date)v[0], "yyyyMMddHHmmss");
			String buf = "%04d-%02d-%02d %02d:%02d:%02d";
			Object[] vv = new Object[6];
			for (int i=0;i<6;i++)
				vv[i] = (i + 1 < v.length ? Value.intOf(v[i + 1]) : 0) + Integer.parseInt(str.substring(i == 0 ? 0 : i * 2 + 2, i * 2 + 4));

			return getDate(String.format(buf, vv), "yyyy-MM-dd HH:mm:ss");
		}
//		else if (v.length == 3)
//		{
//			return getDate(String.format("%04d%02d%02d", Value.intOf(v[0]),  Value.intOf(v[1]),  Value.intOf(v[2])), "yyyyMMdd");
//		}
//		else if (v.length == 4)
//		{
//			Date now = (Date)v[3];
//			String str = FunctionTimeStr.getString(now, "yyyyMMdd");
//			return getDate(String.format("%04d%02d%02d",
//					Value.intOf(v[0]) + Integer.parseInt(str.substring(0, 4)),
//					Value.intOf(v[1]) + Integer.parseInt(str.substring(4, 6)),
//					Value.intOf(v[2]) + Integer.parseInt(str.substring(6))), "yyyyMMdd");
//		}
//		else if (v.length == 6)
//		{
//			return getDate(String.format("%04d%02d%02d%02d%02d%02d", Value.intOf(v[0]),  Value.intOf(v[1]),  Value.intOf(v[2]), Value.intOf(v[3]),  Value.intOf(v[4]),  Value.intOf(v[5])), "yyyyMMddHHmmss");
//		}
//		else if (v.length == 7)
//		{
//			String str = FunctionTimeStr.getString((Date)v[6], "yyyyMMddHHmmss");
//			return getDate(String.format("%04d%02d%02d%02d%02d%02d",
//					Value.intOf(v[0]) + Integer.parseInt(str.substring(0, 4)),
//					Value.intOf(v[1]) + Integer.parseInt(str.substring(4, 6)),
//					Value.intOf(v[2]) + Integer.parseInt(str.substring(6, 8)),
//					Value.intOf(v[3]) + Integer.parseInt(str.substring(8, 10)),
//					Value.intOf(v[4]) + Integer.parseInt(str.substring(10, 12)),
//					Value.intOf(v[5]) + Integer.parseInt(str.substring(12))
//			), "yyyyMMddHHmmss");
//		}
	}

	public static Date getDate(Object val)
	{
		if (val == null)
		{
			return new Date();
		}
		else if (val instanceof java.sql.Date)
		{
			return new Date(((java.sql.Date) val).getTime());
		}
		else if (val instanceof Date)
		{
			return (Date) val;
		}
		else if (val instanceof Long)
		{
			return new Date((Long) val);
		}
		else if (val instanceof Number)
		{
			return new Date(((Number) val).longValue());
		}
		else if (val instanceof String)
		{
			try
			{
				String str = (String) val;
				str = str.replaceAll("/", "-");

				int pos = str.indexOf("-");
				if (pos > 0)
				{
					if (str.length() <= 10)
						return getDate(str, "yyyy-MM-dd");
					else
						return getDate(str, "yyyy-MM-dd HH:mm:ss");
				}

				if (str.length() == 8)
				{
					return getDate(str, "yyyyMMdd");
				}
				else if (str.length() == 14)
				{
					return getDate(str, "yyyyMMddHHmmss");
				}
			}
			catch (Exception e)
			{
				throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException("错误的time运算 - " + e.getMessage());
			}

			throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException("timestr不支持的string格式 - " + val);
		}

		throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException("timestr不支持的格式 - " + val.getClass().toString());
	}

	public static Date getDate(String dateStr, String formatPattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		try
		{
			if ((formatPattern == null) || "".equals(formatPattern))
				formatPattern = "yyyy-MM-dd HH:mm:ss";

			sdf.applyPattern(formatPattern);
			return sdf.parse(dateStr);
		}
		catch (Exception e)
		{
			throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException(dateStr + " - " + formatPattern + " - " + e.getMessage());
		}
	}

	@Override
	public boolean isFixed(int mode, Code p)
	{
		//程序执行的时候，如果慢当前时间会变，但考虑到通常都很快，如果需要保留同一个时间，程序自己重写一个Time函数覆盖来设定
		if ((mode & Optimized.TIME) != 0)
			return true;

		return p != null && p.isFixed(mode);
	}

	public static void main(String[] s)
	{
		System.out.println(getDate("2015-08/09"));
	}
}
