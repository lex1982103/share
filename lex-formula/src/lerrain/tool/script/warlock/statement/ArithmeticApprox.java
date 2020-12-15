package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.math.BigDecimal;
import java.util.*;

public class ArithmeticApprox extends Arithmetic2Elements
{
	public ArithmeticApprox(Words ws, int i)
	{
		super(ws, i);
	}
	
	public Object run(Factors factors)
	{
		Object lo = l.run(factors);
		Object ro = r.run(factors);

		if (lo == ro)
		{
			return Boolean.TRUE;
		}
		else if (lo != null && ro != null)
		{
			return Boolean.valueOf(compare(lo, ro));
		}
		else
		{
			return Boolean.FALSE;
		}
	}

	public static boolean compare(Object v1, Object v2)
	{
		if (v1 == null)
			return "null".equalsIgnoreCase(v2.toString());

		if (v2 == null)
			return "null".equalsIgnoreCase(v1.toString());

		if (v1 instanceof Boolean && v2 instanceof String)
		{
			String s = (String)v2;
			return (Boolean)v1 ? "true".equalsIgnoreCase(s) || "Y".equalsIgnoreCase(s) : "false".equalsIgnoreCase(s) || "N".equalsIgnoreCase(s);
		}

		if (v2 instanceof Boolean && v1 instanceof String)
		{
			String s = (String)v1;
			return (Boolean)v2 ? "true".equalsIgnoreCase(s) || "Y".equalsIgnoreCase(s) : "false".equalsIgnoreCase(s) || "N".equalsIgnoreCase(s);
		}

		if (v1 instanceof Number && v2 instanceof Number)
			return ((Number)v1).doubleValue() == ((Number)v2).doubleValue();

		if (v1 instanceof String && v2 instanceof String)
		{
			String s1 = ((String)v1).trim();
			String s2 = ((String)v2).trim();

			if (s1.equals(s2))
			{
				return true;
			}
			else
			{
				try
				{
					return Double.parseDouble(s1) == Double.parseDouble(s2);
				}
				catch (Exception e)
				{
					return false;
				}
			}
		}

		if (v1 instanceof Number && v2 instanceof String)
		{
			try
			{
				return ((Number)v1).doubleValue() == Double.parseDouble(((String)v2).trim());
			}
			catch (Exception e)
			{
				try
				{
					return ((String)v2).trim().equals(v1.toString());
				}
				catch (Exception e1)
				{
					return false;
				}
			}
		}

		if (v2 instanceof Number && v1 instanceof String)
		{
			try
			{
				return ((Number)v2).doubleValue() == Double.parseDouble(((String)v1).trim());
			}
			catch (Exception e)
			{
				try
				{
					return ((String)v1).trim().equals(v2.toString());
				}
				catch (Exception e1)
				{
					return false;
				}
			}
		}

		if (v1 instanceof Map && v2 instanceof Map)
			return compare((Map)v1, (Map)v2);

		if (v1 instanceof Object[] && v2 instanceof Object[])
			return compare((Object[]) v1, (Object[]) v2);
		if (v1 instanceof List && v2 instanceof List)
			return compare((List) v1, (List) v2);
		if (v1 instanceof Object[] && v2 instanceof List)
			return compare((List) v2, (Object[]) v1);
		if (v2 instanceof Object[] && v1 instanceof List)
			return compare((List) v1, (Object[]) v2);

		return v1.equals(v2);
	}

	public static boolean compare(Object[] m1, Object[] m2)
	{
		int size = Math.min(m1.length, m2.length);
		for (int i = 0; i < size; i++)
		{
			if (!compare(m1[i], m2[i]))
				return false;
		}

		for (int i = size; i < m1.length; i++)
			if (m1[i] != null)
				return false;

		for (int i = size; i < m2.length; i++)
			if (m2[i] != null)
				return false;

		return true;
	}

	public static boolean compare(List m1, List m2)
	{
		int s1 = m1.size();
		int s2 = m2.size();
		int size = Math.min(s1, s2);
		for (int i = 0; i < size; i++)
		{
			if (!compare(m1.get(i), m2.get(i)))
				return false;
		}

		for (int i = size; i < s1; i++)
			if (m1.get(i) != null)
				return false;

		for (int i = size; i < s2; i++)
			if (m2.get(i) != null)
				return false;

		return true;
	}

	public static boolean compare(List m1, Object[] m2)
	{
		int s1 = m1.size();
		int size = Math.min(s1, m2.length);
		for (int i = 0; i < size; i++)
		{
			if (!compare(m1.get(i), m2[i]))
				return false;
		}

		for (int i = size; i < s1; i++)
			if (m1.get(i) != null)
				return false;

		for (int i = size; i < m2.length; i++)
			if (m2[i] != null)
				return false;

		return true;
	}

	public static boolean compare(Map m1, Map m2)
	{
		Map<String, Object> t1 = new HashMap();

		for (Map.Entry e : (Set<Map.Entry>)m1.entrySet())
		{
			Object key = e.getKey();
			Object val = e.getValue();

			if (val != null && key != null)
				t1.put(key.toString(), val);
		}

		int c = 0;
		for (Map.Entry e : (Set<Map.Entry>)m2.entrySet())
		{
			if (e.getKey() != null && e.getValue() != null)
			{
				String key = e.getKey().toString();
				if (!compare(t1.get(key), e.getValue()))
					return false;

				c++;
			}
		}

		if (t1.size() != c)
			return false;

		return true;
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " ~= " + r.toText("", line);
	}
}
