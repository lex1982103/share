package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.CompileListener;
import lerrain.tool.script.ScriptRuntimeError;
import lerrain.tool.script.ScriptRuntimeThrow;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.Arrays;
import java.util.List;

public class ArithmeticThrow extends Code
{
	Code l, r;

	boolean error = false;

	public ArithmeticThrow(Words ws, int i)
	{
		super(ws, i);

		l = Expression.expressionOf(ws.cut(0, i));

		if ("error".equals(ws.getWord(i + 1)))
		{
			r = Expression.expressionOf(ws.cut(i + 2));
			error = true;
		}
		else
		{
			r = Expression.expressionOf(ws.cut(i + 1));
		}
	}

	@Override
	public boolean isFixed()
	{
		return l.isFixed() && r.isFixed();
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		try
		{
			return l.run(factors);
		}
		catch (Exception e)
		{
			String msg = null;
			Object val = null;

			if (r != null)
			{
				Object v = r.run(factors);

				if (v instanceof Wrap)
				{
					val = ((Wrap) v).toArray();
				}
				else if (v instanceof String)
				{
					msg = (String) v;
					val = v;
				}
				else
				{
					val = v;
				}
			}

			if (error)
				throw new ScriptRuntimeError(this, factors, msg, val);
			else
				throw new ScriptRuntimeThrow(this, factors, msg, val);
		}
	}

	public String toText(String space, boolean line)
	{
		return "TRY " + l.toText("", line) + " THROW " + r.toText("", line);
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
}
