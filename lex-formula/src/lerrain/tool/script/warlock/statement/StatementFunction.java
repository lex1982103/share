package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.ArrayList;
import java.util.List;

public class StatementFunction extends Code
{
	Code f;

	String name;

	public StatementFunction(Words ws)
	{
		super(ws);

		name = ws.getWord(1);

		f = new ArithmeticFunctionDim(ws, 1);
	}

	public Code markBreakPoint(int pos)
	{
		if (f.isPointOn(pos))
			return f.markBreakPoint(pos);

		return super.markBreakPoint(pos);
	}

	public void clearBreakPoints()
	{
		f.clearBreakPoints();

		super.clearBreakPoints();
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		((Stack)factors).declare(name, f);

		return null;
	}

	public String toText(String space, boolean line)
	{
		return "DIM FUNCTION " + name + " AS " + f.toText("", line);
	}
}
