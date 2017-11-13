package lerrain.tool.script.warlock.statement;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.CodeImpl;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticAddLet extends CodeImpl
{
	Code lc, rc;
	
	public ArithmeticAddLet(Words ws, int i)
	{
		super(ws, i);

		lc = Expression.expressionOf(ws.cut(0, i));
		rc = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		try
		{
			Object l = lc.run(factors);
			Object r = rc.run(factors);

			if (r == null)
			{
				return l;
			}
			else if (l == null)
			{
				((Reference) lc).let(factors, r);

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

				((Reference) lc).let(factors, v);
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
			else if (l instanceof Date)
			{
				Date now = (Date) l;
				now.setTime(now.getTime() + ((Number)r).longValue());

				return now;
			}
			else
			{
				String v = l.toString() + r.toString();
				((Reference) lc).let(factors, v);

				return v;
			}
		}
		catch (Exception e)
		{
			throw new ScriptRuntimeException(this, factors, e);
		}
	}

	public String toText(String space)
	{
		return lc.toText("") + " += " + rc.toText("");
	}
}
