package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementTry extends Code
{
	Code code;

	String excVar;
	Code catchAll;

	Code throwIt;

	public StatementTry(Words ws)
	{
		super(ws);

		int left = 1;
		int right = Syntax.findRightBrace(ws, left + 1);
		code = new Script(ws.cut(left + 1, right));
		right++; //catch的位置为右括号+1

		if (right > 0 && right < ws.size())
		{
			String after = ws.getWord(right);

			if ("catch".equals(after))
			{
				left = right + 1; //catch后面左括号的位置
				if (ws.getType(left) == Words.PRT)
				{
					if (ws.getType(left + 1) == Words.PRT_R)
					{
						left = left + 2;
					}
					else if (ws.getType(left + 2) == Words.PRT_R)
					{
						excVar = ws.getWord(left + 1);
						left = left + 3;
					}
				}

				if (ws.getType(left) != Words.BRACE)
					throw new SyntaxException(ws, right, "catch后面没有找到代码段");

				right = Syntax.findRightBrace(ws, left + 1);
				catchAll = new Script(ws.cut(left + 1, right));
			}
			else if ("throw".equals(after))
			{
				throwIt = new StatementThrow(ws.cut(right));
			}
		}
	}

	public Code markBreakPoint(int pos)
	{
		if (code.isPointOn(pos))
			return code.markBreakPoint(pos);
		else if (catchAll != null && catchAll.isPointOn(pos))
			return catchAll.markBreakPoint(pos);
		else if (throwIt != null && throwIt.isPointOn(pos))
			return throwIt.markBreakPoint(pos);

		return super.markBreakPoint(pos);
	}

	public void clearBreakPoints()
	{
		code.clearBreakPoints();

		if (catchAll != null)
			catchAll.clearBreakPoints();
		if (throwIt != null)
			throwIt.clearBreakPoints();

		super.clearBreakPoints();
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		try
		{
			code.run(Stack.newStack(factors));
		}
		catch (Interrupt e)
		{
			throw e;
		}
		catch (Exception e)
		{
			if (catchAll != null)
			{
				Stack exc = Stack.newStack(factors);
				if (excVar != null)
					exc.set(excVar, e);

				catchAll.run(exc);
			}
			else if (throwIt != null)
			{
				throwIt.run(factors);
			}
			else
			{
				throw e;
			}
		}
		
		return null;
	}

	@Override
	public boolean isFixed(int mode)
	{
		return code.isFixed(mode)
				&& (catchAll == null || catchAll.isFixed(mode))
				&& (throwIt == null || throwIt.isFixed(mode));
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

	@Override
	public Code[] getChildren()
	{
		return new Code[] {code, catchAll, throwIt};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			this.code = code;
		else if (i == 1)
			catchAll = code;
		else if (i == 2)
			throwIt = code;
	}

}
