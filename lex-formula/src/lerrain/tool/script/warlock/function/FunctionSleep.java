package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.ScriptRuntimeException;

public class FunctionSleep implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		try
		{
			Thread.sleep(Value.intOf(v[0]));
		}
		catch (InterruptedException e)
		{
			throw new ScriptRuntimeException("interrupt", e);
		}

		return null;
	}
}
