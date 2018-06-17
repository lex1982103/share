package lerrain.project.mshell;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Common
{
	public static BigDecimal decimalOf(Object val)
	{
		if (val instanceof Number)
		{
			return BigDecimal.valueOf(((Number) val).doubleValue());
		}
		else if (val instanceof String)
		{
			try
			{
				return new BigDecimal((String)val);
			}
			catch (Exception e)
			{
				return null;
			}
		}

		return null;
	}

	public static double doubleOf(Object val, double defoult)
	{
		if (val instanceof Number)
		{
			return ((Number)val).doubleValue();
		}
		else if (val instanceof String)
		{
			try
			{
				return Double.parseDouble((String) val);
			}
			catch (Exception e)
			{
				return defoult;
			}
		}

		return defoult;
	}

	public static Long toLong(Object val)
	{
		if (val instanceof Number)
		{
			return ((Number)val).longValue();
		}
		else if (val instanceof String)
		{
			try
			{
				return Long.parseLong((String) val);
			}
			catch (Exception e)
			{
				return null;
			}
		}

		return null;
	}

	public static Integer toInteger(Object val)
	{
		if (val instanceof Number)
		{
			return ((Number)val).intValue();
		}
		else if (val instanceof String)
		{
			try
			{
				return Integer.parseInt((String) val);
			}
			catch (Exception e)
			{
				return null;
			}
		}

		return null;
	}

	public static Double toDouble(Object val)
	{
		if (val instanceof Number)
		{
			return ((Number)val).doubleValue();
		}
		else if (val instanceof String)
		{
			try
			{
				return Double.parseDouble((String) val);
			}
			catch (Exception e)
			{
				return null;
			}
		}

		return null;
	}

	public static int intOf(Object val, int defoult)
	{
		if (val instanceof Number)
		{
			return ((Number)val).intValue();
		}
		else if (val instanceof String)
		{
			try
			{
				return (int)Math.round(Double.parseDouble((String)val));
			}
			catch (Exception e)
			{
				return defoult;
			}
		}

		return defoult;
	}

	public static boolean boolOf(Object val, boolean defoult)
	{
		if (val instanceof Boolean)
		{
			return (Boolean)val;
		}
		else if (val instanceof Number)
		{
			return ((Number)val).intValue() != 0;
		}
		else if (val instanceof String)
		{
			if ("Y".equalsIgnoreCase((String)val) || "YES".equalsIgnoreCase((String)val) || "true".equalsIgnoreCase((String)val))
				return true;
			if ("N".equalsIgnoreCase((String)val) || "NO".equalsIgnoreCase((String)val) || "false".equalsIgnoreCase((String)val))
				return false;
			try
			{
				return Integer.parseInt((String)val) == 0;
			}
			catch (Exception e)
			{
			}
		}

		return defoult;
	}

	public static Boolean toBoolean(Object val)
	{
		if (val instanceof Boolean)
		{
			return (Boolean)val;
		}
		else if (val instanceof Number)
		{
			return ((Number)val).intValue() != 0;
		}
		else if (val instanceof String)
		{
			if ("Y".equalsIgnoreCase((String)val) || "YES".equalsIgnoreCase((String)val) || "true".equalsIgnoreCase((String)val))
				return true;
			if ("N".equalsIgnoreCase((String)val) || "NO".equalsIgnoreCase((String)val) || "false".equalsIgnoreCase((String)val))
				return false;
			try
			{
				return Integer.parseInt((String)val) == 0;
			}
			catch (Exception e)
			{
				return null;
			}
		}

		return null;
	}

	public static Date dateOf(Object val)
	{
		return dateOf(val, null);
	}

	public static Date dateOf(Object val, Date defoult)
	{
		if (val instanceof java.sql.Date)
		{
			return new Date(((java.sql.Date)val).getTime());
		}
		else if (val instanceof Date)
		{
			return (Date)val;
		}
		else if (val instanceof Number)
		{
			return new Date(((Number)val).longValue());
		}
		else if (val instanceof String)
		{
			try
			{
				String str = (String)val;
				str = str.replaceAll("[\\\\/]", "-");

				int mode;
				if (str.indexOf(":") > 0)
					mode = 4;
				else if (str.length() > 10)
					mode = 3;
				else if (str.indexOf("-") < 0)
					mode = 1;
				else
					mode = 2;

				if (mode == 1)
					return getDate(str, "yyyyMMdd");
				else if (mode == 2)
					return getDate(str, "yyyy-MM-dd");
				else if (mode == 3)
					return getDate(str, "yyyyMMddHHmmss");
				else if (mode == 4)
					return getDate(str, "yyyy-MM-dd HH:mm:ss");
				else
					return defoult;
			}
			catch (Exception e)
			{
				return defoult;
			}
		}

		return defoult;
	}

	public static boolean isEmpty(Object val)
	{
		if (val == null)
			return true;

		if (val instanceof String)
			return "".equals(((String)val).trim());

		if (val instanceof Map)
			return ((Map<?, ?>)val).isEmpty();

		if (val instanceof List)
			return ((List<?>)val).isEmpty();

		if (val instanceof Object[])
			return ((Object[])val).length == 0;

		return false;
	}

	static List<String> listOf(Object val)
	{
		if (val instanceof List)
		{
			List<String> r = new ArrayList<String>();
			for (Object ss : (List<?>)val)
				r.add(ss == null ? null : ss.toString());
			return r;
		}
		else if (val instanceof String[])
		{
			String[] s = (String[])val;
			List<String> r = new ArrayList<String>();
			for (String ss : s)
				r.add(ss);
			return r;
		}
		else if (val instanceof Object[])
		{
			Object[] s = (Object[])val;
			List<String> r = new ArrayList<String>();
			for (Object ss : s)
				r.add(ss == null ? null : ss.toString());
			return r;
		}

		return null;
	}

	public static String[] stringArrayOf(Object val)
	{
		String[] s = null;
		if (val instanceof List)
		{
			int c = 0;
			s = new String[((List<?>)val).size()];
			for (Object ss : (List<?>)val)
				s[c++] = ss == null ? null : ss.toString();
		}
		else if (val instanceof String[])
		{
			s = (String[])val;
		}

		return s;
	}

	public static String trimStringOf(Object val)
	{
		if (val == null)
			return null;

		return val.toString().trim();
	}

	public static double round(double val, int scale)
	{
		return BigDecimal.valueOf(val).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String pathOf(String... path)
	{
		String path1 = null;

		for (String path2 : path)
		{
			if (path1 == null)
			{
				path1 = path2;
			}
			else if (path2 != null)
			{
				if (!path1.endsWith("/") && !path1.endsWith("\\") && !path1.equals(File.separator))
					path1 += File.separator;
				if (path2.startsWith("/") || path2.startsWith("\\") || path2.startsWith(File.separator))
					path2 = path2.substring(1);

				path1 += path2;
			}
		}

		return path1;
	}

/*
	public static String pathOf(String path1, String path2)
	{
		if (Common.isEmpty(path1))
			return path2;
		if (Common.isEmpty(path2))
			return path1;
		if (!path1.endsWith("/") && !path1.endsWith("\\") && !path1.equals(File.separator))
			path1 += File.separator;
		if (path2.startsWith("/") || path2.startsWith("\\") || path2.startsWith(File.separator))
			path2 = path2.substring(1);

		return path1 + path2;
	}
*/

	public static int getAge(Date birthday)
	{
		return getAge(birthday, new Date());
	}

	public static int getAge(Date birthday, Date today)
	{
		Calendar c1 = Calendar.getInstance();
		c1.setTime(birthday);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(today);

		int y = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int m = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		int d = c2.get(Calendar.DATE) - c1.get(Calendar.DATE);

		return y - (m < 0 || (m == 0 && d < 0)? 1 : 0);
	}

	public static int getAgeMonth(Date birthday, Date today)
	{
		Calendar c1 = Calendar.getInstance();
		c1.setTime(birthday);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(today);

		int y = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int m = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		int d = c2.get(Calendar.DATE) - c1.get(Calendar.DATE);

		return y * 12 + m - (d < 0 ? 1 : 0);
	}

	public static Date getDate(String dateStr)
	{
		return getDate(dateStr, "yyyy-MM-dd");
	}

	public static Date getDate(int year, int month, int day)
	{
		return getDate(year + "-" + (month < 10 ? "0" + month : month + "") + "-" + (day < 10 ? "0" + day : day + ""), "yyyy-MM-dd");
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
			return null;
		}
	}

	public static String getString(Date date)
	{
		return getString(date, "yyyy-MM-dd");
	}

	public static String getTimeString(Date time)
	{
		return getString(time, "yyyy-MM-dd HH:mm:ss");
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
			return null;
		}
	}

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String encodeToString(byte[] bytes)
	{
		char[] encodedChars = encode(bytes);
		return new String(encodedChars);
	}

	private static char[] encode(byte[] data)
	{
		int l = data.length;
		char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++)
		{
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}
		return out;
	}

	public static String getStackString(Exception e)
	{
		StringWriter sw = new StringWriter();
		try (PrintWriter pw = new PrintWriter(sw))
		{
			e.printStackTrace(pw);
		}
		return sw.toString();
	}

	public static byte[] byteOf(InputStream is) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] b = new byte[1024];
		int c = 0;
		while ((c = is.read(b)) >= 0)
		{
			baos.write(b, 0, c);
		}

		baos.close();

		return baos.toByteArray();
	}

	public static String stringOf(InputStream is, String charset) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] b = new byte[1024];
		int c = 0;
		while ((c = is.read(b)) >= 0)
		{
			baos.write(b, 0, c);
		}

		baos.close();

		return baos.toString(charset);
	}

	public static String stringOfACS(InputStream is1, String charset)
	{
		try (InputStream is = is1)
		{
			return stringOf(is, charset);
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
