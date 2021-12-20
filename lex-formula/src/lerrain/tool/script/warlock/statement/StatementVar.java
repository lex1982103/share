package lerrain.tool.script.warlock.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

/**
 * 这个类将来要改成个Arithmetic
 */
public class StatementVar extends Code
{
	Code[] r;
	
	List names = new ArrayList();
	
	public StatementVar(Words ws)
	{
		super(ws);

		List<Code> list = new ArrayList<>();
		int j = -1, k = -1;
		for (int i = 1; i < ws.size(); i++)
		{
			if (ws.getType(i) == Words.VARIABLE)
			{
				names.add(ws.getWord(i));
				j = i;
			}
			else if (ws.getType(i) == Words.LET)
			{
				k = i;
			}
			else if (Syntax.isLeftBrace(ws, i))
			{
				i = Syntax.findRightBrace(ws, i + 1);
			}
			else if (ws.getType(i) == Words.COMMA)
			{
				if (j < 0)
					throw new RuntimeException("错误的赋值操作");
				if (k >= 0)
					list.add(new ArithmeticLet(ws.cut(j, i), k - j));

				k = -1;
				j = -1;
			}
		}

		if (k >= 0)
		{
			if (j < 0)
				throw new RuntimeException("末尾错误的赋值操作");

			list.add(new ArithmeticLet(ws.cut(j), k));
		}

		if (!list.isEmpty())
			r = list.toArray(new Code[list.size()]);
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		for (int i = 0; i < names.size(); i++)
			((Stack)factors).declare((String)names.get(i));
		
		if (r != null)
			for (Code c : r)
				c.run(factors);

		return null;
	}

	public String toText(String space, boolean line)
	{
		StringBuffer sb = new StringBuffer("VAR ");
		sb.append(names.toString());
		sb.append("; ");

		if (r != null)
			for (Code c : r)
			{
				sb.append(c.toText("", false));
				sb.append("; ");
			}

		return sb.toString();
	}

	@Override
	public Code[] getChildren()
	{
		return r;
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		r[i] = code;
	}

}
