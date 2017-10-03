package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementReturn implements Code
{
	Code r;
	
	public StatementReturn(Words ws)
	{
		Words exp = ws.cut(1);
		if (exp.size() != 0)
			r = Expression.expressionOf(exp);
	}

	public Object run(Factors factors)
	{
		return Interrupt.interruptOf(Interrupt.RETURN, r == null ? null : r.run(factors));
	}

	public String toText(String space)
	{
		return "RETURN" + (r == null ? "" : " " + r.toText(""));
	}
}
