package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.Date;

public class ArithmeticGreater extends Arithmetic2Elements
{
	public ArithmeticGreater(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		Object lo = l.run(factors);
		Object ro = r.run(factors);

		if (lo instanceof Number && ro instanceof Number)
			return Boolean.valueOf(((Number) lo).doubleValue() > ((Number) ro).doubleValue());
		else if (lo instanceof Date && ro instanceof Date)
			return Boolean.valueOf(((Date) lo).after((Date) ro));

		throw new ScriptRuntimeException(this, factors, "大小比较只可以在数字、日期上进行");
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " > " + r.toText("", line);
	}
}
