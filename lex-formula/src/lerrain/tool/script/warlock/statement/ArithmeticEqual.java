package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.CodeImpl;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticEqual extends CodeImpl
{
	Code l, r;
	
	public ArithmeticEqual(Words ws, int i)
	{
		super(ws, i);

		l = Expression.expressionOf(ws.cut(0, i));
		r = Expression.expressionOf(ws.cut(i + 1));
	}
	
	public Object run(Factors factors)
	{
		Value v1 = Value.valueOf(l, factors);
		Value v2 = Value.valueOf(r, factors);
		
		if (v1.isNull() && v2.isNull())
			return new Boolean(true);
		else if (!v1.isNull() && !v2.isNull())
		{
			if (v1.isDecimal() && v2.isDecimal())
				return new Boolean(v1.doubleValue() == v2.doubleValue());
			else
				return new Boolean(v1.getValue().equals(v2.getValue()));
		}
		else
			return new Boolean(false);
	}

	public String toText(String space)
	{
		return l.toText("") + " == " + r.toText("");
	}
}
