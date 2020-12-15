package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticNotEqual extends Arithmetic2Elements
{
	public ArithmeticNotEqual(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		Object lo = l.run(factors);
		Object ro = r.run(factors);

		if (lo == ro)
		{
			return Boolean.FALSE;
		}
		else if (lo != null && ro != null)
		{
			if (lo.equals(ro))
				return Boolean.FALSE;
			else if (lo instanceof Number && ro instanceof Number)
				return Boolean.valueOf(((Number) lo).doubleValue() != ((Number) ro).doubleValue());
			else
				return Boolean.TRUE;
		}
		else
		{
			return Boolean.TRUE;
		}
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " <> " + r.toText("", line);
	}
}
