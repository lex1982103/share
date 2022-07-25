package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.WriteVariable;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticSubSub extends Arithmetic implements WriteVariable
{
	public Code l, r;
	
	public ArithmeticSubSub(Words ws, int i)
	{
		super(ws, i);

		if (i == 0)
			r = Expression.expressionOf(ws.cut(i + 1));
		else if (i == ws.size() - 1)
			l = Expression.expressionOf(ws.cut(0, i));
		
		if ((l == null && r == null) || (!(l instanceof Reference) && !(r instanceof Reference)))
			throw new SyntaxException(ws, i, "递减运算要求左右之一为引用");
	}

	public Object run(Factors factors)
	{
		try
		{
			if (l != null)
			{
				Number v = (Number)l.run(factors);
				if (isInt(v))
					((Reference) l).let(factors, v.intValue() - 1);
				else if (isLong(v))
					((Reference) l).let(factors, v.longValue() - 1);
				else
					((Reference) l).let(factors, v.doubleValue() - 1);
				return v;
			}
			else
			{
				Number v = (Number)r.run(factors);
				Object n;

				if (isInt(v))
					n = v.intValue() - 1;
				else if (isLong(v))
					n = v.longValue() - 1;
				else
					n = v.doubleValue() - 1;

				((Reference) r).let(factors, n);
				return n;
			}
		}
		catch (Exception e)
		{
			throw new ScriptRuntimeException(this, factors, e);
		}
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {l, r};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			l = code;
		else if (i == 1)
			r = code;
	}

	public String toText(String space, boolean line)
	{
		if (l != null)
			return l.toText("", line) + "--";
		else
			return "--" + l.toText("", line);
	}

	@Override
	public Reference getReference()
	{
		return (Reference)(l == null ? r : l);
	}
}
