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

	public String getMessage()
	{
		if (!Script.STACK_MESSAGE)
			return super.getMessage();

		try (ByteArrayOutputStream os = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(os))
		{
			ps.println(super.getMessage());
			((CodeImpl) code).printAll(ps, super.getMessage());
			return os.toString();
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
	}

//	public void printStackTrace(PrintStream ps)
//	{
//		if (code instanceof CodeImpl)
//			((CodeImpl)code).printAll(ps, this);
//		else
//			ps.println(code.toText(""));
//
//		super.printStackTrace(ps);
//	}
//
//	public void printStackTrace(PrintWriter ps)
//	{
//		if (code instanceof CodeImpl)
//			((CodeImpl)code).printAll(ps, this);
//		else
//			ps.println(code.toText(""));
//
//		super.printStackTrace(ps);
//	}
}
