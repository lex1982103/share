package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticQuestMark extends Code
{
	Code l, r1, r2;
	
	public ArithmeticQuestMark(Words ws, int i)
	{
		super(ws, i);

		l = Expression.expressionOf(ws.cut(0, i));

		int deep = 1;
		for (int j = i + 1; j < ws.size(); j++) //寻找匹配的冒号
		{
			int t = ws.getType(j);
			if (t == Words.BRACE || t == Words.BRACKET || t == Words.PRT)
			{
				j = Syntax.findRightBrace(ws, j + 1);
			}
			else if (t == Words.COLON)
			{
				deep--;
				if (deep == 0)
				{
					r1 = Expression.expressionOf(ws.cut(i + 1, j));
					r2 = Expression.expressionOf(ws.cut(j + 1));
					break;
				}
			}
			else if (t == Words.QUESTMARK)
			{
				deep++;
			}
		}

		if (r1 == null || r2 == null)
			throw new SyntaxException("?:运算组合缺少:");
	}

	public Object run(Factors factors)
	{
		Value v = Value.valueOf(l, factors);
		if (v.isType(Value.TYPE_BOOLEAN))
		{
//			Object ro = r.run(factors);
//
//			if (!(ro instanceof Code[]) || ((Code[])ro).length != 2)
//				throw new ScriptRuntimeException(this, factors, "?:组合运算没有找到冒号");
//
//			Code[] c = (Code[])ro;

			if (v.booleanValue())
				return r1.run(factors);
			else
				return r2.run(factors);
		}

		throw new ScriptRuntimeException(this, factors, "?:组合运算要求问号左侧值为boolean类型");
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {l, r1, r2};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			l = code;
		else if (i == 1)
			r1 = code;
		else if (i == 2)
			r2 = code;
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", false) + " ? " + r1.toText("", false) + " : " + r2.toText("", false);
	}

	@Override
	public boolean isFixed(int mode)
	{
		return l.isFixed(mode) && r1.isFixed(mode) && r2.isFixed(mode);
	}
}
