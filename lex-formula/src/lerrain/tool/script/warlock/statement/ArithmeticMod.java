package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticMod implements Code
{
	Code l, r;
	
	public ArithmeticMod(Words ws, int i)
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
			return Integer.valueOf(left.intValue() % right.intValue());
		}
		
		throw new RuntimeException("只可以对数字做取余运算");
	}

	public String toText(String space)
	{
		return l.toText("") + " - " + r.toText("");
	}
}
