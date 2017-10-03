package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticMultiply implements Code
{
	Code l, r;
	
	public ArithmeticMultiply(Words ws, int i)
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
//			return left.toDecimal().multiply(right.toDecimal());
			return Double.valueOf(left.doubleValue() * right.doubleValue());
		}
		
		throw new RuntimeException("只可以对数字做乘法运算 - " + left + " * " + right);
	}

	public String toText(String space)
	{
		return l.toText("") + " * " + r.toText("");
	}
}
