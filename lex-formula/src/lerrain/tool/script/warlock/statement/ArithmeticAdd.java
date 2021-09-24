package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.*;

public class ArithmeticAdd extends Arithmetic2Elements
{
	public ArithmeticAdd(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		Object l = this.l.run(factors);
		Object r = this.r.run(factors);

		if (l instanceof Number && r instanceof Number)
		{
			if (isFloat(l) || isFloat(r))
				return ((Number)l).doubleValue() + ((Number)r).doubleValue();
			else if (isInt(l) && isInt(r))
				return ((Number)l).intValue() + ((Number)r).intValue();
			else
				return ((Number)l).longValue() + ((Number)r).longValue();

//			return left.toDecimal().add(right.toDecimal());
//			return Double.valueOf(left.doubleValue() + right.doubleValue());
		}
		else if ((l instanceof List || l instanceof Object[]) && (r instanceof List || r instanceof Object[]))
		{
			Object[] o1, o2;

			if (l instanceof List)
				o1 = ((List)l).toArray();
			else
				o1 = (Object[])l;

			if (r instanceof List)
				o2 = ((List)r).toArray();
			else
				o2 = (Object[])r;

			Object[] o3 = new Object[o1.length + o2.length];

//			for (int i = 0; i < o1.length; i++)
//				o3[i] = o1[i];
//			for (int i = 0; i < o2.length; i++)
//				o3[i + o1.length] = o2[i];

			System.arraycopy(o1, 0, o3, 0, o1.length);
			System.arraycopy(o2, 0, o3, o1.length, o2.length);

			return o3;
		}
		else if (l instanceof Map && r instanceof Map) //非null优先，都不是null选择左边的
		{
			Map m = new HashMap();
			m.putAll((Map)r);
			for (Map.Entry e : (Set<Map.Entry>)((Map)l).entrySet())
				if (e.getValue() != null)
					m.put(e.getKey(), e.getValue());
			return m;
		}
		else if (l == null)
		{
			return r;
		}
		else if (r == null)
		{
			return l;
		}
		else if (l instanceof Date)
		{
			if (r instanceof Number)
				return new Date(((Date) l).getTime() - ((Number) r).longValue());
			if (r instanceof String)
			{

			}

			throw new RuntimeException("不支持日期 + " + r);
		}
		else
		{
			return l.toString() + r.toString();
		}
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " + " + r.toText("", line);
	}
}