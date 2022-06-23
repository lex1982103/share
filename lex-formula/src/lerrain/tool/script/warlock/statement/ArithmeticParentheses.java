package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Function;
import lerrain.tool.script.*;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;
import lerrain.tool.script.warlock.function.FunctionTry;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class ArithmeticParentheses extends Code
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
			throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException(this, factors, e);
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
