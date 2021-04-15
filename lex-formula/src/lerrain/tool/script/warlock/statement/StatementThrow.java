package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeError;
import lerrain.tool.script.ScriptRuntimeThrow;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.Arrays;
import java.util.List;

public class StatementThrow extends Code
{
	Code r;

	boolean error = false;
	
	public StatementThrow(Words ws)
	{
		super(ws);

		if ("error".equals(ws.getWord(1)))
		{
			r = Expression.expressionOf(ws.cut(2));
			error = true;
		}
		else
		{
			r = Expression.expressionOf(ws.cut(1));
		}
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		String msg = null;
		Object val = null;

		if (r != null)
		{
			Object v = r.run(factors);

			if (v instanceof Wrap)
			{
				val = ((Wrap)v).toArray();
			}
			else if (v instanceof String)
			{
				msg = (String)v;
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

	public String toText(String space, boolean line)
	{
		return "THROW " + r.toText("", line);
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {r};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			r = code;
	}
}
