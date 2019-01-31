package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementWhile extends Code
{
	Code c, fc;
	
	public StatementWhile(Words ws)
	{
		super(ws);

		int left = 1;
		int right = Syntax.findRightBrace(ws, left + 1);
		c = new Script(ws.cut(left + 1, right));
		
		left = right + 1;
		int type1 = ws.getType(left);
		if (type1 == Words.BRACE)
		{
			right = Syntax.findRightBrace(ws, left + 1);
			fc = new Script(ws.cut(left + 1, right));
		}
		else
		{
			fc = new Script(ws.cut(left, ws.size()));
		}
	}

	public Code markBreakPoint(int pos)
	{
		if (fc.isPointOn(pos))
			return fc.markBreakPoint(pos);
		else if (c != null && c.isPointOn(pos))
			return c.markBreakPoint(pos);

		return super.markBreakPoint(pos);
	}

	public void clearBreakPoints()
	{
		fc.clearBreakPoints();

		if (c != null)
			c.clearBreakPoints();

		super.clearBreakPoints();
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		Stack stack = new Stack(factors);
		
		while (Value.booleanOf(c, stack))
		{
			try
			{
				fc.run(stack);
			}
			catch (Interrupt.Break e)
			{
				break;
			}
			catch (Interrupt.Continue e)
			{
			}
		}
		
		return null;
	}

	public String toText(String space, boolean line)
	{
		StringBuffer buf = new StringBuffer("WHILE (");
		buf.append(c.toText("", line));
		buf.append(")\n");
		buf.append(space + "{\n");
		buf.append(fc.toText(space + "    ", line) + "\n");
		buf.append(space + "}");
		
		return buf.toString();
	}
}
