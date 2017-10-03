package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementBreak implements Code
{
	public StatementBreak(Words ws)
	{
	}

	public Object run(Factors factors)
	{
		return Interrupt.interruptOf(Interrupt.BREAK);
	}

	public String toText(String space)
	{
		return "BREAK";
	}
}
