package lerrain.tool.script;

public class SyntaxException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public SyntaxException(String descr)
	{
		super(descr);
	}
	
	public SyntaxException()
	{
	}
}
