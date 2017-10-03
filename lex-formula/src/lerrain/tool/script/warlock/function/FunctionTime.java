package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FunctionTime implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v == null ||v.length == 0)
		{
			return new Date();
		}
		else if (v.length == 1)
		{
			Object val = v[0];
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
					if (str.length() == 8)
						return getDate((String) val, "yyyyMMdd");
					else if (str.length() == 10)
						return getDate((String) val, "yyyy-MM-dd");
					else if (str.length() == 14)
						return getDate((String) val, "yyyyMMddHHmmss");
					else if (str.length() == 19)
						return getDate((String) val, "yyyy-MM-dd HH:mm:ss");
				}
				catch (Exception e)
				{
					throw new RuntimeException("错误的time运算 - " + e.getMessage());
				}
			}
		}
		else
		{
			String str = v[0] == null ? "00000000000000" : FunctionTimeStr.getString((Date)v[0], "yyyyMMddHHmmss");
			String buf = "%04d%02d%02d%02d%02d%02d";
			Object[] vv = new Object[6];
			for (int i=0;i<6;i++)
				vv[i] = (i + 1 < v.length ? Value.intOf(v[i + 1]) : 0) + Integer.parseInt(str.substring(i == 0 ? 0 : i * 2 + 2, i * 2 + 4));
			return getDate(String.format(buf, vv), "yyyyMMddHHmmss");
		}
//
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

		throw new RuntimeException("错误的time运算");
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
			throw new RuntimeException("错误的time运算 - " + dateStr + " - " + formatPattern + " - " + e.getMessage());
		}
	}

	public static void main(String[] s)
	{
		FunctionTime ft = new FunctionTime();
		System.out.println(ft.run(new Object[] {new Date(), 0, 0, 1}, null ));
	}
}
