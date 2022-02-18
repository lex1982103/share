package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

/**
 * 不是传统那个goto，用这个做名字是考虑这个很少有人用他当变量名，不容易冲突
 * goto(env) {
 *     代码
 * }
 * 可以切换环境执行代码
 */
public class StatementGoto extends Code
{
	Code c, fc;

	public StatementGoto(Words ws)
	{
		super(ws);

		int left = 1;
		int right = Syntax.findRightBrace(ws, left + 1);
		c = new Script(ws.cut(left + 1, right));

		left = right + 1;
		int type1 = ws.getType(left);
		if (type1 != Words.BRACE)
			throw new SyntaxException(ws, 0, "goto(...)后面需要紧跟代码体，以{}包裹");

		right = Syntax.findRightBrace(ws, left + 1);
		fc = new Script(ws.cut(left + 1, right));
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

		return fc.run((Factors)c.run(factors));
	}

	@Override
	public boolean isFixed(int mode)
	{
		return (c == null || c.isFixed(mode)) && fc.isFixed(mode);
	}

	public String toText(String space, boolean line)
	{
		StringBuffer buf = new StringBuffer("{\n");
		buf.append("GotoEnv " + c.toText(space + "    ", line) + "\n");
		buf.append(space + "}");
		
		return buf.toString();
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {c, fc};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			c = code;
		else if (i == 1)
			fc = code;
	}

}
