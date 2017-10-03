package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticSub implements Code
{
	Code l, r;
	
	public ArithmeticSub(Words ws, int i)
	{
		if (i > 0)
			l = Expression.expressionOf(ws.cut(0, i));
		else
			l = null;
		
		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		Value left = l == null ? new Value(0) : Value.valueOf(l, factors);
		Value right = Value.valueOf(r, factors);
		
		if (left.isDecimal() && right.isDecimal())
		{
//			return left.toDecimal().subtract(right.toDecimal());
			return Double.valueOf(left.doubleValue() - right.doubleValue());
		}
		
		throw new RuntimeException("只可以对数字做减法运算");
	}

	public String toText(String space)
	{
		return l == null ? "-" + r.toText("") : (l.toText("") + " - " + r.toText(""));
	}
}
