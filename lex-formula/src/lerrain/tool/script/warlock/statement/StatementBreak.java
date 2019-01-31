package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementBreak extends Code
{
	Words ws;

	public StatementBreak(Words ws)
	{
		super(ws);
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		throw new Interrupt.Break();
	}

	public String toText(String space, boolean line)
	{
		return "BREAK";
	}
}
