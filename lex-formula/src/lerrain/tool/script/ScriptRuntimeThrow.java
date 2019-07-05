package lerrain.tool.script;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

public class ScriptRuntimeThrow extends ScriptRuntimeError
{
	public ScriptRuntimeThrow(Code code, Factors factors, String detail, Object value)
	{
		super(code, factors, detail, value);
	}
}
