package lerrain.tool.script.warlock.statement;

import java.util.Date;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticGreater implements Code
{
	Code l, r;
	
	public ArithmeticGreater(Words ws, int i)
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
			return new Boolean(left.doubleValue() > right.doubleValue());
//			return new Boolean(left.toDecimal().compareTo(right.toDecimal()) > 0);
		} 
		else if (left.isType(Value.TYPE_DATE) && right.isType(Value.TYPE_DATE))
		{
			Date d1 = (Date)left.getValue();
			Date d2 = (Date)right.getValue();
			return new Boolean(d1.after(d2));
		}
		
		throw new RuntimeException("大小比较只可以在数字上进行");
	}

	public String toText(String space)
	{
		return l.toText("") + " > " + r.toText("");
	}
}
