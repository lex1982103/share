package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticEqual extends Arithmetic2Elements
{
	public ArithmeticEqual(Words ws, int i)
	{
		super(ws, i);
	}
	
	public Object run(Factors factors)
	{
		Object lo = l.run(factors);
		Object ro = r.run(factors);

		if (lo == ro)
		{
			return Boolean.TRUE;
		}
		else if (lo instanceof CharSequence && ro instanceof CharSequence)
		{
			CharSequence c1 = (CharSequence)lo;
			CharSequence c2 = (CharSequence)ro;

			int len = c1.length();
			if (len != c2.length())
				return false;

			for (int i = 0; i < len; ++i)
				if (c1.charAt(i) != c2.charAt(i))
					return false;

			return true;
		}
		else if (lo != null && ro != null)
		{
			if (lo instanceof Number && ro instanceof Number)
				return Boolean.valueOf(((Number) lo).doubleValue() == ((Number) ro).doubleValue());
			else if (lo.equals(ro))
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		}
		else
		{
			return Boolean.FALSE;
		}
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " == " + r.toText("", line);
	}
}
