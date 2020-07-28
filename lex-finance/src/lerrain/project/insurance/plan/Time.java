package lerrain.project.insurance.plan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time 
{
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

	public static Date getDate(Object val, Date defoult)
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
}
