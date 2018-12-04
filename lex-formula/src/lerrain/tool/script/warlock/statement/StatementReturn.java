package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementReturn extends Code
{
	Code r;
	
	public StatementReturn(Words ws)
	{
		super(ws);

		Words exp = ws.cut(1);
		if (exp.size() != 0)
			r = Expression.expressionOf(exp);
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		return Interrupt.interruptOf(this, Interrupt.RETURN, r == null ? null : r.run(factors));
	}

	public String toText(String space, boolean line)
	{
		return space + "RETURN " + (r == null ? "" : r.toText(space + "  ", line));
	}
}
