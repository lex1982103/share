package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementContinue extends Code
{
	public StatementContinue(Words ws)
	{
		super(ws);
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		throw new Interrupt.Continue();
	}

	public String toText(String space, boolean line)
	{
		return "CONTINUE";
	}
}
