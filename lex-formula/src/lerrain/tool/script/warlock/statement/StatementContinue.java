package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Stack;
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

	@Override
	public boolean isFixed()
	{
		return false; //带动作，是不能直接fix优化的，不然return/break/throw等动作被优化没了，只剩个值
	}

	public String toText(String space, boolean line)
	{
		return "CONTINUE";
	}
}
