package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

//带动作，是不能直接fix优化的，不然return/break/throw等动作被优化没了，只剩个值
public class StatementBreak extends Code
{
	Code r;

	public StatementBreak(Words ws)
	{
		super(ws);

		Words exp = ws.cut(1);
		if (exp.size() != 0)
			r = Expression.expressionOf(exp);
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		throw new Interrupt.Break(r == null ? 1 : Value.intOf(r, factors));
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {r};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			r = code;
	}

	public String toText(String space, boolean line)
	{
		return "BREAK";
	}
}
