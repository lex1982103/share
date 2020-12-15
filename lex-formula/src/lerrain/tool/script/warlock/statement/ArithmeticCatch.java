package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeError;
import lerrain.tool.script.ScriptRuntimeThrow;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticCatch extends Code
{
	Code lc, rc;

	public ArithmeticCatch(Words ws, int i)
	{
		super(ws, i);

		lc = Expression.expressionOf(ws.cut(0, i));
		rc = Expression.expressionOf(ws.cut(i + 1));
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
			if (rc == null)
				return null;

			return rc.run(factors);
		}
	}

	@Override
	public boolean isFixed()
	{
		return lc.isFixed() && (rc == null || rc.isFixed());
	}

	public String toText(String space, boolean line)
	{
		return "CATCH " + lc.toText("", line) + " AS " + rc.toText("", line);
	}
}
