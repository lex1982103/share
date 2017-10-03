package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticAdd implements Code
{
	Code l, r;
	
	public ArithmeticAdd(Words ws, int i)
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
//			return left.toDecimal().add(right.toDecimal());
			return Double.valueOf(left.doubleValue() + right.doubleValue());
		}
		else if (left.isNull())
		{
			return right.getValue();
		}
		else if (right.isNull())
		{
			return left.getValue();
		}
		else
		{
			return left.toString() + right.toString();
		}
	}

	public String toText(String space)
	{
		return l.toText("") + " + " + r.toText("");
	}
}
