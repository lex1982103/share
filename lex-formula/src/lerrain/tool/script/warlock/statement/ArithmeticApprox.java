package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.CodeImpl;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ArithmeticApprox extends CodeImpl
{
	Code l, r;

	public ArithmeticApprox(Words ws, int i)
	{
		super(ws, i);

		l = Expression.expressionOf(ws.cut(0, i));
		r = Expression.expressionOf(ws.cut(i + 1));
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

		if (v1.isNull() || v2.isNull())
			return false;

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
					return Double.parseDouble(s1) == Double.parseDouble(s2);
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
				return false;
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
				return false;
			}
		}

		if (v1.isMap() && v2.isMap())
			return compare(v1.toMap(), v2.toMap());

		return v1.getValue().equals(v2.getValue());
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

	public String toText(String space)
	{
		return l.toText("") + " ~= " + r.toText("");
	}
}
