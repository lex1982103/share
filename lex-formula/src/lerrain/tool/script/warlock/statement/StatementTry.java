package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementTry extends Code
{
	Code code;
	Code catchAll;

	public StatementTry(Words ws)
	{
		super(ws);

		int left = 1;
		int right = Syntax.findRightBrace(ws, left + 1);
		code = new Script(ws.cut(left + 1, right));
		right++; //catch的位置为右括号+1

		if (right > 0 && right < ws.size() && "catch".equals(ws.getWord(right)))
		{
			left = right + 1; //catch后面左括号的位置
			right = Syntax.findRightBrace(ws, left + 1);
			catchAll = new Script(ws.cut(left + 1, right));
		}
	}

	public Code markBreakPoint(int pos)
	{
		if (code.isPointOn(pos))
			return code.markBreakPoint(pos);
		else if (catchAll != null && catchAll.isPointOn(pos))
			return catchAll.markBreakPoint(pos);

		return super.markBreakPoint(pos);
	}

	public void clearBreakPoints()
	{
		code.clearBreakPoints();

		if (catchAll != null)
			catchAll.clearBreakPoints();

		super.clearBreakPoints();
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		try
		{
			code.run(new Stack(factors));
		}
		catch (Exception e)
		{
			if (catchAll == null)
				throw e;

			catchAll.run(new Stack(factors));
		}
		
		return null;
	}

	public String toText(String space, boolean line)
	{
		StringBuffer buf = new StringBuffer("TRY");
		buf.append(space + "{\n");
		buf.append(code.toText(space + "    ", line) + "\n");
		buf.append(space + "}");
		
		if (catchAll != null)
		{
			buf.append("\n");
			buf.append(space + "CATCH\n");
			buf.append(space + "{\n");
			buf.append(catchAll.toText(space + "    ", line) + "\n");
			buf.append(space + "}");
		}
		
		return buf.toString();
	}
}
