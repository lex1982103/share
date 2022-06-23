package lerrain.tool.script;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

public class ScriptRuntimeException extends RuntimeException
{
	String exCode;
    String detailMsg; // 为了承载当前封装的实际异常类相关数据

	Code code;

	Factors factors;

	public ScriptRuntimeException(String detail)
	{
		super(detail);
	}

	public ScriptRuntimeException(Exception e)
	{
		super(e);
	}

	public ScriptRuntimeException(String detail, Factors factors)
	{
		super(detail);

		this.factors = factors;
	}

	public ScriptRuntimeException(Code code, Factors factors, String detail, Exception e)
	{
		super(detail, e);

		this.code = code;
		this.factors = factors;
	}

	public ScriptRuntimeException(Code code, Factors factors, String exCode, String detail)
	{
		super(detail);

		this.code = code;
		this.exCode = exCode;
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
		super((e == null ? null : e.getMessage()), e);
		detailMsg = (e == null ? null : e.toString());

		this.code = code;
		this.factors = factors;
	}

	public ScriptRuntimeException(String exCode, Exception e)
	{
		super((e == null ? null : e.getMessage()), e);

		this.exCode = exCode;
	}

	public String getExceptionCode()
	{
		return this.exCode;
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

    @Override
    public String toString() {
        return (detailMsg == null || "".equals(detailMsg)) ? super.toString() : detailMsg;
    }

    public String toStackString()
	{
		if (!Script.STACK_MESSAGE)
			return this.toString();

		try (ByteArrayOutputStream os = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(os))
		{
			Object codeTag = ((Code) code).getScriptTag();
			ps.println("---- Cause in <" + (codeTag == null ? "?" : codeTag) + "> ----");

			String msg;
			if (this.getCause() instanceof ScriptRuntimeException)
				msg = "...";
			else if (this.getCause() != null)
				msg = "... ==> " + this.toString();	// super.getMessage();
			else
				msg = this.toString();		// super.getMessage();

			((Code) code).printAll(ps, msg);

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
