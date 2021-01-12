package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.CompileListener;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.Arrays;
import java.util.List;

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
	public boolean isFixed()
	{
		return r == null || r.isFixed();
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {r};
	}

	public String toText(String space, boolean line)
	{
		return "BREAK";
	}
}
