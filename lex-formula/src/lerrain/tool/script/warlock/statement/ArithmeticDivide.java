package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticDivide implements Code
{
	Code l, r;
	
	public ArithmeticDivide(Words ws, int i)
	{
		l = Expression.expressionOf(ws.cut(0, i));
		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
//		if (Script.getCalculateMode() == Script.NATIVE)
//			return new Double(calculate(factors));
		
		Value left = Value.valueOf(l, factors);
		Value right = Value.valueOf(r, factors);
		
		if (left.isDecimal() && right.isDecimal())
		{
//			return left.toDecimal().divide(right.toDecimal(), Script.PRECISE_SCALE, BigDecimal.ROUND_HALF_UP);
			return Double.valueOf(left.doubleValue() / right.doubleValue());
		}
		
		throw new RuntimeException("只可以对数字做除法运算: " + toText(""));
	}

	public String toText(String space)
	{
		return l.toText("") + " / " + r.toText("");
	}

//	public double calculate(Factors p)
//	{
//		return ((Calculate)l).calculate(p) / ((Calculate)r).calculate(p);
//	}
}
