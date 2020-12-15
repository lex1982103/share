package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticMultiply extends Arithmetic2Elements
{
	public ArithmeticMultiply(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		Object l = this.l.run(factors);
		Object r = this.r.run(factors);

		if (l instanceof Number && r instanceof Number)
		{
			if (isFloat(l) || isFloat(r))
				return ((Number) l).doubleValue() * ((Number) r).doubleValue();
			else if (isInt(l) && isInt(r))
				return ((Number) l).intValue() * ((Number) r).intValue();
			else
				return ((Number) l).longValue() * ((Number) r).longValue();
		}

		throw new ScriptRuntimeException(this, factors, "只可以对数字做乘法运算 - " + l + " * " + r);
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " * " + r.toText("", line);
	}
}
