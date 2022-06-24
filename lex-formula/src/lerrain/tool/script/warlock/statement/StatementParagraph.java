package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementParagraph extends Code
{
	Code c;
	
	public StatementParagraph(Words ws)
	{
		super(ws);

		int i = 0;
		int r = Syntax.findRightBrace(ws, i + 1);
		
		if (i != 0 || r != ws.size() - 1)
			throw new SyntaxException("表达式内部脚本作为一个值，与周围的计算无法匹配");
		
		c = new Script(ws.cut(i + 1, r));
	}

	public Code markBreakPoint(int pos)
	{
		if (c.isPointOn(pos))
			return c.markBreakPoint(pos);

		return super.markBreakPoint(pos);
	}

	public void clearBreakPoints()
	{
		c.clearBreakPoints();

		super.clearBreakPoints();
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		return c.run(Stack.newStack(factors));
	}

	@Override
	public boolean isFixed(int mode)
	{
		return c.isFixed(mode);
	}

	public String toText(String space, boolean line)
	{
		StringBuffer buf = new StringBuffer("{\n");
		buf.append(c.toText(space + "    ", line) + "\n");
		buf.append(space + "}");
		
		return buf.toString();
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {c};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			c = code;
	}
}
