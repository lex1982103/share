package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementThrow extends Code
{
	Code r;
	
	public StatementThrow(Words ws)
	{
		super(ws);

		r = Expression.expressionOf(ws.cut(1));
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		throw new RuntimeException(Value.stringOf(r, factors));
//		return Interrupt.interruptOf(Interrupt.THROW, r.run(factors));
	}

	public String toText(String space, boolean line)
	{
		return "THROW " + r.toText("", line);
	}
}
