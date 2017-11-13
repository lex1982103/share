package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.CodeImpl;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticAdd extends CodeImpl
{
	Code lc, rc;
	
	public ArithmeticAdd(Words ws, int i)
	{
		super(ws, i);

		lc = Expression.expressionOf(ws.cut(0, i));
		rc = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		Object l = lc.run(factors);
		Object r = rc.run(factors);

		if (l instanceof Number && r instanceof Number)
		{
			if (isFloat(l) || isFloat(r))
				return ((Number)l).doubleValue() + ((Number)r).doubleValue();
			else if (isInt(l) && isInt(r))
				return ((Number)l).intValue() + ((Number)r).intValue();
			else
				return ((Number)l).longValue() + ((Number)r).longValue();

//			return left.toDecimal().add(right.toDecimal());
//			return Double.valueOf(left.doubleValue() + right.doubleValue());
		}
		else if (l == null)
		{
			return r;
		}
		else if (r == null)
		{
			return l;
		}
		else
		{
			return l.toString() + r.toString();
		}
	}

	public String toText(String space)
	{
		return lc.toText("") + " + " + rc.toText("");
	}
}