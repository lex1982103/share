package lerrain.tool.script.warlock.statement;

import java.util.Date;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticLess extends Arithmetic2Elements
{
	public ArithmeticLess(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		Value left = Value.valueOf(l, factors);
		Value right = Value.valueOf(r, factors);
		
		if (left.isDecimal() && right.isDecimal())
		{
			return new Boolean(left.doubleValue() < right.doubleValue());
//			return new Boolean(left.toDecimal().compareTo(right.toDecimal()) < 0);
		}
		else if (left.isType(Value.TYPE_DATE) && right.isType(Value.TYPE_DATE))
		{
			Date d1 = (Date)left.getValue();
			Date d2 = (Date)right.getValue();
			return new Boolean(d1.before(d2));
		}

		throw new ScriptRuntimeException(this, factors, "大小比较只可以在数字、日期上进行");
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " < " + r.toText("", line);
	}
}
