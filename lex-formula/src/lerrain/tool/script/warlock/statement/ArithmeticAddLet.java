package lerrain.tool.script.warlock.statement;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticAddLet implements Code
{
	Code l, r;
	
	public ArithmeticAddLet(Words ws, int i)
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
			Double v = Double.valueOf(left.doubleValue() + right.doubleValue());
//			BigDecimal v = right.toDecimal().add(left.toDecimal());
			((Reference)l).let(factors, v);

			return v;
		}
		else if (left.isMap())
		{
			if (right.getValue() == null)
				return left.getValue();

			if (right.isMap())
				((Map) left.getValue()).putAll((Map) right.getValue());

			return left.getValue();
		}
		else if (left.isString())
		{
			String v = left.getValue().toString() + right.getValue();
			((Reference)l).let(factors, v);

			return v;
		}
		else if (left.getType() == Value.TYPE_LIST)
		{
			((List)left.getValue()).add(right.getValue());

			return left.getValue();
		}
		else if (left.getType() == Value.TYPE_DATE)
		{
			Date now = (Date)left.getValue();
			now.setTime(now.getTime() + right.longValue());

			return now;
		}

		throw new RuntimeException("只可以在数字、字符串、Map或List上做累加赋值运算");
	}

	public String toText(String space)
	{
		return l.toText("") + " += " + r.toText("");
	}
}
