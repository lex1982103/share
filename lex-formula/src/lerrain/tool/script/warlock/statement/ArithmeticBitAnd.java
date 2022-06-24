package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticBitAnd extends Arithmetic2Elements
{
	public ArithmeticBitAnd(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		Object l1 = l.run(factors);
		Object l2 = r.run(factors);

		if (l1 instanceof Long || l2 instanceof Long)
			return ((Number)l1).longValue() & ((Number)l2).longValue();
		else
			return ((Number)l1).intValue() & ((Number)l2).intValue();
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " AND " + r.toText("", line);
	}
}
