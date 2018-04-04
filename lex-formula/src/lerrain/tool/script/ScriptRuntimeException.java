package lerrain.tool.script;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.CodeImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

public class ScriptRuntimeException extends RuntimeException
{
	Code code;

	Factors factors;

	public ScriptRuntimeException(Code code, Factors factors, String detail, Exception e)
	{
		super(detail, e);

		this.code = code;
		this.factors = factors;
	}

	public ScriptRuntimeException(Code code, Factors factors, String detail)
	{
		super(detail);

		this.code = code;
		this.factors = factors;
	}

	public ScriptRuntimeException(Code code, Factors factors, Exception e)
	{
		super(e);

		this.code = code;
		this.factors = factors;
	}

	public Code getCode()
	{
		return code;
	}

	public Map getStackMap()
	{
		if (factors instanceof Stack)
			return ((Stack) factors).getStackMap();

		return null;
	}

	public Factors getFactors()
	{
		return factors;
	}

	public String toStackString()
	{
		if (!Script.STACK_MESSAGE)
			return super.toString();

		try (ByteArrayOutputStream os = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(os))
		{
			String funcName = ((CodeImpl) code).getScriptName();
			ps.println("---- Cause in <" + (funcName == null ? "?" : funcName) + "> ----");

			String msg;
			if (this.getCause() instanceof ScriptRuntimeException)
				msg = "...";
			else if (this.getCause() != null)
				msg = "... ==> " + super.getMessage();
			else
				msg = super.getMessage();

			((CodeImpl) code).printAll(ps, msg);

			if (this.getCause() instanceof ScriptRuntimeException)
				ps.println(((ScriptRuntimeException)this.getCause()).toStackString());

			return os.toString();
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
	}
}
