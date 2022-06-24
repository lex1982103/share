package lerrain.tool.script;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ScriptRuntimeException extends RuntimeException
{
	Code code;

	Factors factors;

	public ScriptRuntimeException(String msg, Exception e)
	{
		super(msg, e, Script.EXC_DETAIL, Script.EXC_DETAIL);
	}

	public ScriptRuntimeException(String msg)
	{
		this(msg, null);
	}

	public ScriptRuntimeException(Exception e)
	{
		this(null, e);
	}

	public ScriptRuntimeException(Code code, Factors factors, String msg, Exception e)
	{
		this(msg, e);

		this.code = code;
		this.factors = factors;
	}

	public ScriptRuntimeException(Code code, Factors factors, String msg)
	{
		this(msg, null);

		this.code = code;
		this.factors = factors;
	}

	public ScriptRuntimeException(Code code, Factors factors, Exception e)
	{
		this(null, e);

		this.code = code;
		this.factors = factors;
	}

	public Code getCode()
	{
		return code;
	}

	public Factors getFactors()
	{
		return factors;
	}

    public String toStackString()
	{
		if (!Script.STACK_MESSAGE)
			return this.toString();

		try (ByteArrayOutputStream os = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(os))
		{
			Object codeTag = code.getScriptTag();
			ps.println("---- Cause in <" + (codeTag == null ? "?" : codeTag) + "> ----");

			String msg;
			if (this.getCause() instanceof ScriptRuntimeException)
				msg = "...";
			else if (this.getCause() != null)
				msg = "... ==> " + this.toString();	// super.getMessage();
			else
				msg = this.toString();		// super.getMessage();

			code.printAll(ps, msg);

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
