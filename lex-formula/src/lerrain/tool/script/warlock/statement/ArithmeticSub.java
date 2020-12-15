package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.Date;

public class ArithmeticSub extends Code //有可能是负数，所以不完全是二元运算
{
	Code l, r;

	public ArithmeticSub(Words ws, int i)
	{
		super(ws, i);

		if (i > 0)
			l = Expression.expressionOf(ws.cut(0, i));
		else
			l = null;
		
		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		Object l = this.l == null ? 0 : this.l.run(factors);
		Object r = this.r.run(factors);

		if (l instanceof Number && r instanceof Number)
		{
			if (isFloat(l) || isFloat(r))
				return ((Number) l).doubleValue() - ((Number) r).doubleValue();
			else if (isInt(l) && isInt(r))
				return ((Number) l).intValue() - ((Number) r).intValue();
			else
				return ((Number) l).longValue() - ((Number) r).longValue();
		}
		else if (l instanceof Date && r instanceof Date)
		{
			return ((Date)l).getTime() - ((Date)r).getTime();
		}

		throw new ScriptRuntimeException(this, factors, "只可以对数字或日期做减法运算：" + l + " - " + r);
	}

	@Override
	public boolean isFixed()
	{
		return (l == null ? true : l.isFixed()) && r.isFixed();
	}

	public String toText(String space, boolean line)
	{
		return l == null ? "-" + r.toText("", line) : (l.toText("", line) + " - " + r.toText("", line));
	}
}
