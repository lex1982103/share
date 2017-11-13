package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementThread implements Code
{
	Code pre, code;

	public StatementThread(Words ws)
	{
		int left = 1; //callback左括号的位置
		if (ws.getType(left) != Words.PRT)
			throw new RuntimeException("thread 后面需要为小括号");

		int right = Syntax.findRightBrace(ws, left + 1);
		pre = new Script(ws.cut(left + 1, right));

		left = right + 1;
		if (ws.getType(left) != Words.BRACE)
			throw new RuntimeException("thread 代码体需要以大括号包裹");

		right = Syntax.findRightBrace(ws, left + 1);
		code = new Script(ws.cut(left + 1, right));
	}

	public Object run(final Factors factors)
	{
		final Stack stack = new Stack(factors);
		if (pre != null)
			pre.run(stack);

		Thread th = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				code.run(stack);
			}
		});

		th.start();
		return th;
	}

	public String toText(String space)
	{
		StringBuffer buf = new StringBuffer("THREAD (");
		buf.append(pre == null ? "" : pre.toText(""));
		buf.append(")\n");
		buf.append(space + "{\n");
		buf.append(code.toText(space + "    ") + "\n");
		buf.append(space + "}");

		return buf.toString();
	}
}
