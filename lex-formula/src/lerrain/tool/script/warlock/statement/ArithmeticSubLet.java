package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.WriteVariable;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ArithmeticSubLet extends Arithmetic2Elements implements WriteVariable
{
	public ArithmeticSubLet(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		try
		{
			Object l = this.l.run(factors);
			Object r = this.r.run(factors);

			if (l instanceof Number && r instanceof Number)
			{
				Object v;

				if (isFloat(l) || isFloat(r))
					v = ((Number)l).doubleValue() - ((Number)r).doubleValue();
				else if (isInt(l) && isInt(r))
					v = ((Number)l).intValue() - ((Number)r).intValue();
				else
					v = ((Number)l).longValue() - ((Number)r).longValue();

				((Reference) this.l).let(factors, v);
				return v;
			}
			else if (l instanceof Date)
			{
				Date now = (Date) l;
				now.setTime(now.getTime() - ((Number)r).longValue());

				return now;
			}
			else if (l instanceof Map)
			{
				((Map) l).remove(r);
				return l;
			}
			else if (l instanceof List)
			{
				((List) l).remove(((Number)r).intValue());
				return l;
			}
		}
		catch (Exception e)
		{
			throw new ScriptRuntimeException(this, factors, e);
		}

		throw new RuntimeException("只可以在数字、List或Map上做递减赋值运算");
	}

	@Override
	public boolean isFixed(int mode)
	{
		return false;
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " -= " + r.toText("", line);
	}

	@Override
	public Reference getReference()
	{
		return (Reference)l;
	}
}
