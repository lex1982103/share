package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticDivide extends Arithmetic2Elements
{
	public ArithmeticDivide(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		Object l = this.l.run(factors);
		Object r = this.r.run(factors);
		
		if (l instanceof Number && r instanceof Number)
			return Double.valueOf(((Number)l).doubleValue() / ((Number)r).doubleValue());

		throw new ScriptRuntimeException(this, factors, "只可以对数字做除法运算: " + toText("", true));
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " / " + r.toText("", line);
	}
}
