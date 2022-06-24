package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
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
	public boolean isFixed(int mode)
	{
		return l.isFixed(mode) && (r == null || r.isFixed(mode));
	}

	public String toText(String space, boolean line)
	{
		return "CATCH " + l.toText("", line) + " AS " + r.toText("", line);
	}
}
