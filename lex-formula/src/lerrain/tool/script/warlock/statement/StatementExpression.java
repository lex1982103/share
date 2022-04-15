package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class StatementExpression extends Code
{
	public Code exp;

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
	public boolean isFixed(int mode)
	{
		return exp.isFixed(mode);
	}

	public String toText(String space, boolean line)
	{
		return exp.toText(space, line);
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {exp};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			exp = code;
	}
}
