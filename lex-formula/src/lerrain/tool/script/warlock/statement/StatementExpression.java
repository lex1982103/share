package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementExpression extends Code
{
	Code exp;

	public StatementExpression(Words ws)
	{
		super(ws);

		exp = Expression.expressionOf(ws);
	}

	public Object run(Factors factors)
	{
		super.debug(factors);
		
		return exp.run(factors);
	}

	@Override
	public boolean isFixed()
	{
		return exp.isFixed();
	}

	public String toText(String space, boolean line)
	{
		return exp.toText(space, line);
	}
}
