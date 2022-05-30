package lerrain.tool.script.warlock.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

/**
 * 这个类将来要改成个Arithmetic
 */
public class StatementVar extends Code
{
	Code[] r;
	
	String[] names;

	int forMode;
	
	public StatementVar(Words ws)
	{
		super(ws);

		List<Code> list = new ArrayList<>();
		List<String> names = new ArrayList<>();

		int j = -1, k = -1;
		boolean fast = false;
		for (int i = 1; i < ws.size(); i++)
		{
		    int t = ws.getType(i);
			if (t == Words.VARIABLE && j < 0 && k < 0) //=左边的才算
			{
				names.add(ws.getWord(i));
				j = i;
			}
			else if (t == Words.LET || t == Words.FASTLET)
			{
				k = i;
                fast = t == Words.FASTLET;
			}
            else if (t == Words.COLON || t == Words.COLON_FLAG || t == Words.COLON_SPLIT)
            {
                if (k < 0) //没遇到=先遇到了:，通常是for(var i, j : map)，这种写法不在这里执行，直接把代码返回到for里面
                {
                    if (!list.isEmpty())
                        throw new RuntimeException("不支持混写:和=");
                    list.add(new ArithmeticColon(ws.cut(1, ws.size()), i - 1));
                    forMode = t;
                    break;
                }
            }
			else if (Syntax.isLeftBrace(ws, i))
			{
				i = Syntax.findRightBrace(ws, i + 1);
			}
			else if (t == Words.COMMA)
			{
				if (j < 0)
					throw new RuntimeException("错误的赋值操作");
				if (k >= 0)
                    list.add(fast ? new ArithmeticFastLet(ws.cut(j, i), k - j) : new ArithmeticLet(ws.cut(j, i), k - j));

				k = -1;
				j = -1;
			}
		}

		if (k >= 0)
		{
			if (j < 0)
				throw new RuntimeException("末尾错误的赋值操作");

			list.add(fast ? new ArithmeticFastLet(ws.cut(j), k - j) : new ArithmeticLet(ws.cut(j), k - j));
		}

		if (!list.isEmpty())
			r = list.toArray(new Code[list.size()]);

		this.names = names.toArray(new String[names.size()]);
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		for (String name : names)
			((Stack)factors).declare(name);

		Object v = null;

		if (r != null)
			for (Code c : r)
				v = c.run(factors);

		return v;
	}

	public String toText(String space, boolean line)
	{
		StringBuffer sb = new StringBuffer("VAR ");
		for (String name : names)
		{
			sb.append(name);
			sb.append(" ");
		}
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

	public String[] getVars()
	{
		return names;
	}
}
