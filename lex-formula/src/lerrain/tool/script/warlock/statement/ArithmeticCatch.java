package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeError;
import lerrain.tool.script.ScriptRuntimeThrow;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticCatch extends Arithmetic2Elements
{
	public ArithmeticCatch(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		try
		{
			return l.run(factors);
		}
		catch (Exception e)
		{
			if (r == null)
				return null;

			return r.run(factors);
		}
	}

	@Override
	public boolean isFixed()
	{
		return l.isFixed() && (r == null || r.isFixed());
	}

	public String toText(String space, boolean line)
	{
		return "CATCH " + l.toText("", line) + " AS " + r.toText("", line);
	}
}
