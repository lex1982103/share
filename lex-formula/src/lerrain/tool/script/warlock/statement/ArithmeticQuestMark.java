package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.CodeImpl;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticQuestMark extends CodeImpl
{
	Code l, r;
	
	public ArithmeticQuestMark(Words ws, int i)
	{
		super(ws, i);

		l = Expression.expressionOf(ws.cut(0, i));
		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		Value v = Value.valueOf(l, factors);
		if (v.isType(Value.TYPE_BOOLEAN))
		{
			Object ro = r.run(factors);
			
			if (!(ro instanceof Code[]) || ((Code[])ro).length != 2)
				throw new ScriptRuntimeException(this, factors, "?:组合运算没有找到冒号");

			Code[] c = (Code[])ro;

			if (v.booleanValue())
				return c[0].run(factors);
			else
				return c[1].run(factors);
		}

		throw new ScriptRuntimeException(this, factors, "?!组合运算要求问号左侧值为boolean类型");
	}

	public String toText(String space)
	{
		return l.toText("") + " ? " + r.toText("");
	}
}
