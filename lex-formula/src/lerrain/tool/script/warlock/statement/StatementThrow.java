package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementThrow implements Code
{
	Code r;
	
	public StatementThrow(Words ws)
	{
		r = Expression.expressionOf(ws.cut(1));
	}

	public Object run(Factors factors)
	{
		throw new RuntimeException(Value.stringOf(r, factors));
//		return Interrupt.interruptOf(Interrupt.THROW, r.run(factors));
	}

	public String toText(String space)
	{
		return "THROW " + r.toText("");
	}
}
