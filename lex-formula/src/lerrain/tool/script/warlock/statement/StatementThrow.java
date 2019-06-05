package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeThrow;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementThrow extends Code
{
	Code r;
	
	public StatementThrow(Words ws)
	{
		super(ws);

		r = Expression.expressionOf(ws.cut(1));
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
				Object[] list = ((Wrap)v).toArray();
				if (list != null)
				{
					if (list.length >= 2)
						val = list[1];

					if (list.length >= 1)
						msg = list[0] == null ? null : list[0].toString();
				}
			}
			else
			{
				msg = v == null ? null : v.toString();
			}
		}

		throw new ScriptRuntimeThrow(this, factors, msg, val);
	}

	public String toText(String space, boolean line)
	{
		return "THROW " + r.toText("", line);
	}
}
