package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ArithmeticApprox extends Arithmetic2Elements
{
	public ArithmeticApprox(Words ws, int i)
	{
		super(ws, i);
	}
	
	public Object run(Factors factors)
	{
		Value v1 = Value.valueOf(l, factors);
		Value v2 = Value.valueOf(r, factors);
		
		if (v1.isNull() && v2.isNull())
		{
			return true;
		}
		else if (!v1.isNull() && !v2.isNull())
		{
			return compare(v1, v2);
		}

		return false;
	}

	public static boolean compare(Value v1, Value v2)
	{
		if (v1.isNull() && v2.isNull())
			return true;

		if (v1.isNull())
			return "null".equals(v2.toString());

		if (v2.isNull())
			return "null".equals(v1.toString());

		if (v1.isBoolean() && v2.isString())
		{
			String s = v2.toString();
			return v1.booleanValue() ? "true".equals(s) || "Y".equalsIgnoreCase(s) : "false".equals(s) || "N".equalsIgnoreCase(s);
		}

		if (v2.isBoolean() && v1.isString())
		{
			String s = v1.toString();
			return v2.booleanValue() ? "true".equals(s) || "Y".equalsIgnoreCase(s) : "false".equals(s) || "N".equalsIgnoreCase(s);
		}

		if (v1.isDecimal() && v2.isDecimal())
			return v1.doubleValue() == v2.doubleValue();

		if (v1.isString() && v2.isString())
		{
			String s1 = v1.toString().trim();
			String s2 = v2.toString().trim();

			if (s1.equals(s2))
			{
				return true;
			}
			else
			{
				try
				{
					return new BigDecimal(s1).compareTo(new BigDecimal(s2)) == 0;
				}
				catch (Exception e)
				{
					return false;
				}
			}
		}

		if (v1.isDecimal() && v2.isString())
		{
			try
			{
				return v1.doubleValue() == Double.parseDouble(v2.toString().trim());
			}
			catch (Exception e)
			{
				try
				{
					return v2.toString().trim().equals(v1.toString().trim());
				}
				catch (Exception e1)
				{
					return false;
				}
			}
		}

		if (v2.isDecimal() && v1.isString())
		{
			try
			{
				return v2.doubleValue() == Double.parseDouble(v1.toString().trim());
			}
			catch (Exception e)
			{
				try
				{
					return v1.toString().trim().equals(v2.toString().trim());
				}
				catch (Exception e1)
				{
					return false;
				}
			}
		}

		if (v1.isMap() && v2.isMap())
			return compare(v1.toMap(), v2.toMap());

		if (v1.isQueue() && v2.isQueue())
			return compare(v1.toArray(), v2.toArray());

		return v1.getValue().equals(v2.getValue());
	}

	public static boolean compare(Object[] m1, Object[] m2)
	{
		if (m1 != null && m2 != null)
		{
			int size = Math.min(m1.length, m2.length);
			for (int i=0;i<size;i++)
			{
				if (!compare(Value.valueOf(m1[i]), Value.valueOf(m2[i])))
					return false;
			}

			for (int i=size;i<m1.length;i++)
				if (m1[i] != null)
					return false;

			for (int i=size;i<m2.length;i++)
				if (m2[i] != null)
					return false;

			return true;
		}

		return false;
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
				if (!compare(Value.valueOf(t1.get(key)), Value.valueOf(e.getValue())))
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
