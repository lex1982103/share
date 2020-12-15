package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;

import java.util.List;
import java.util.Map;

public class FunctionSleep extends FixedFunction
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
