package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticDivide extends Code
{
	Code lc, rc;
	
	public ArithmeticDivide(Words ws, int i)
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
			return Double.valueOf(((Number)l).doubleValue() / ((Number)r).doubleValue());

		throw new ScriptRuntimeException(this, factors, "只可以对数字做除法运算: " + toText("", true));
	}

	public String toText(String space, boolean line)
	{
		return lc.toText("", line) + " / " + rc.toText("", line);
	}
}
