package lerrain.tool.script.warlock.statement;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.CodeImpl;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticAddLet extends CodeImpl
{
	Code l, r;
	
	public ArithmeticAddLet(Words ws, int i)
	{
		super(ws, i);

		l = Expression.expressionOf(ws.cut(0, i));
		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		try
		{
			Value left = Value.valueOf(l, factors);
			Value right = Value.valueOf(r, factors);

			if (right.isNull())
			{
				return left.getValue();
			}
			else if (left.isNull() && (l instanceof Reference))
			{
				((Reference) l).let(factors, right.getValue());
				return right.getValue();
			}
			else if (left.isDecimal() && right.isDecimal())
			{
				Double v = Double.valueOf(left.doubleValue() + right.doubleValue());
//			BigDecimal v = right.toDecimal().add(left.toDecimal());
				((Reference) l).let(factors, v);

				return v;
			}
			else if (left.isMap())
			{
				if (right.isNull())
					return left.getValue();

				if (right.isMap())
					((Map) left.getValue()).putAll((Map) right.getValue());

				return left.getValue();
			}
			else if (left.isString())
			{
				String v = left.getValue().toString() + right.getValue();
				((Reference) l).let(factors, v);

				return v;
			}
			else if (left.getType() == Value.TYPE_LIST)
			{
				((List) left.getValue()).add(right.getValue());

				return left.getValue();
			}
			else if (left.getType() == Value.TYPE_DATE)
			{
				Date now = (Date) left.getValue();
				now.setTime(now.getTime() + right.longValue());

				return now;
			}
		}
		catch (Exception e)
		{
			throw new ScriptRuntimeException(this, factors, e);
		}

		throw new ScriptRuntimeException(this, factors, "只可以在数字、字符串、Map或List上做累加赋值运算");
	}

	public String toText(String space)
	{
		return l.toText("") + " += " + r.toText("");
	}
}
