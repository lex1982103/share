package lerrain.tool.formula;

public class VariableNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public VariableNotFoundException(String desc)
	{
		super(desc);
	}

	public VariableNotFoundException(String desc, Exception e)
	{
		super(desc, e);
	}
}
