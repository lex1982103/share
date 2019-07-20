package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeError;
import lerrain.tool.script.ScriptRuntimeThrow;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticThrow extends Code
{
	Code lc, rc;

	boolean error = false;

	public ArithmeticThrow(Words ws, int i)
	{
		super(ws, i);

		lc = Expression.expressionOf(ws.cut(0, i));

		if ("error".equals(ws.getWord(i + 1)))
		{
			rc = Expression.expressionOf(ws.cut(i + 2));
			error = true;
		}
		else
		{
			rc = Expression.expressionOf(ws.cut(i + 1));
		}
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		try
		{
			return lc.run(factors);
		}
		catch (Exception e)
		{
			String msg = null;
			Object val = null;

			if (rc != null)
			{
				Object v = rc.run(factors);

				if (v instanceof Wrap)
				{
					val = ((Wrap) v).toArray();
				}
				else if (v instanceof String)
				{
					msg = (String) v;
					val = v;
				}
				else
				{
					val = v;
				}
			}

			if (error)
				throw new ScriptRuntimeError(this, factors, msg, val);
			else
				throw new ScriptRuntimeThrow(this, factors, msg, val);
		}
	}

	public String toText(String space, boolean line)
	{
		return "TRY " + lc.toText("", line) + " THROW " + rc.toText("", line);
	}
}
