package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticAnd extends Arithmetic2Elements
{
	public ArithmeticAnd(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		try
		{
			return Boolean.valueOf(v(l, factors) && v(r, factors));
		}
		catch (Exception e)
		{
			throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException(this, factors, e);
		}
	}
	
	private boolean v(Code c, Factors factors)
	{
		return Value.booleanOf(c.run(factors));
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " AND " + r.toText("", line);
	}
}
