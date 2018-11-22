package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.List;
import java.util.Map;

public class ArithmeticSubLet extends Code
{
	Code lc, rc;
	
	public ArithmeticSubLet(Words ws, int i)
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

			if (l instanceof Number && r instanceof Number)
			{
				Object v;

				if (isFloat(l) || isFloat(r))
					v = ((Number)l).doubleValue() - ((Number)r).doubleValue();
				else if (isInt(l) && isInt(r))
					v = ((Number)l).intValue() - ((Number)r).intValue();
				else
					v = ((Number)l).longValue() - ((Number)r).longValue();

				((Reference) lc).let(factors, v);
				return v;
			}
			else if (l instanceof Map)
			{
				((Map) l).remove(r);
				return l;
			}
			else if (l instanceof List)
			{
				((List) l).remove(((Number)r).intValue());
				return l;
			}
		}
		catch (Exception e)
		{
			throw new ScriptRuntimeException(this, factors, e);
		}

		throw new RuntimeException("只可以在数字、List或Map上做递减赋值运算");
	}

	public String toText(String space, boolean line)
	{
		return lc.toText("", line) + " -= " + rc.toText("", line);
	}
}
