package lerrain.tool.script;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;

public class ScriptRuntimeError extends ScriptRuntimeException
{
	Object value;

	public ScriptRuntimeError(Code code, Factors factors, String detail, Object value)
	{
		super(code, factors, detail);

		this.value = value;
	}

	public Object getValue()
	{
		return value;
	}

}
