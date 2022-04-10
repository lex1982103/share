package lerrain.tool.script.warlock.statement;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.WriteVariable;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticAddLet extends Arithmetic2Elements implements WriteVariable
{
	public ArithmeticAddLet(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		try
		{
			Object l = this.l.run(factors);
			Object r = this.r.run(factors);

			if (r == null)
				return l;

//			if (r instanceof Variable.LoadOnUse)
//			    r = ((Variable.LoadOnUse) r).load();

			if (l == null)
			{
				((Reference) this.l).let(factors, r);

				return r;
			}
			else if (l instanceof Number && r instanceof Number)
			{
				Object v;

				if (isFloat(l) || isFloat(r))
					v = ((Number)l).doubleValue() + ((Number)r).doubleValue();
				else if (isInt(l) && isInt(r))
					v = ((Number)l).intValue() + ((Number)r).intValue();
				else
					v = ((Number)l).longValue() + ((Number)r).longValue();

				((Reference) this.l).let(factors, v);
				return v;
			}
			else if (l instanceof Map)
			{
				if (r instanceof Map)
					((Map) l).putAll((Map) r);

				return l;
			}
			else if (l instanceof List)
			{
				((List) l).add(r);

				return l;
			}
			else if (l instanceof Stack) //会强制在最外层赋值，没有就declare，并不会向上查找覆盖
			{
				if (r instanceof Map)
					((Stack) l).setAll((Map) r);

				return l;
			}
            else if (l instanceof Object[])
            {
                List list = Arrays.asList((Object[])l);
                list.add(r);

                ((Reference) this.l).let(factors, list);
                return list;
            }
			else if (l instanceof Date)
			{
				Date now = (Date) l;
				now.setTime(now.getTime() + ((Number)r).longValue());

				return now;
			}
			else
			{
				String v = l.toString() + r.toString();
				((Reference) this.l).let(factors, v);

				return v;
			}
		}
		catch (Exception e)
		{
			throw new ScriptRuntimeException(this, factors, e);
		}
	}

	@Override
	public boolean isFixed(int mode)
	{
		return false;
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " += " + r.toText("", line);
	}

	@Override
	public Reference getReference()
	{
		return (Reference)l;
	}
}
