package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticSubLet implements Code
{
	Code l, r;
	
	public ArithmeticSubLet(Words ws, int i)
	{
		l = Expression.expressionOf(ws.cut(0, i));
		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		Value left = Value.valueOf(l, factors);
		Value right = Value.valueOf(r, factors);
		
		if (left.isDecimal() && right.isDecimal())
		{
			Double v = Double.valueOf(left.doubleValue() - right.doubleValue());
//			BigDecimal v = left.toDecimal().subtract(right.toDecimal());
			((Reference)l).let(factors, v);

			return v;
		}
		
		throw new RuntimeException("只可以在数字上做递减赋值运算");
	}

	public String toText(String space)
	{
		return l.toText("") + " -= " + r.toText("");
	}
}
