package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

@Deprecated
public class ArithmeticParentheses extends Arithmetic
{
	public Code body;

	public ArithmeticParentheses(Words ws, int i)
	{
		super(ws, i);

		int r = Syntax.findRightBrace(ws, i + 1);
		body = Expression.expressionOf(ws.cut(i + 1, r));
	}

	public Object run(Factors factors)
	{
		try
		{
			return body.run(factors);
		}
		catch (Exception e)
		{
			throw new ScriptRuntimeException(this, factors, e);
		}
	}

	@Override
	public boolean isFixed(int mode)
	{
		return body == null || body.isFixed(mode);
	}

	public String toText(String space, boolean line)
	{
		return "(" + body.toText("", line) + ")";
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {body};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			body = code;
	}
}
