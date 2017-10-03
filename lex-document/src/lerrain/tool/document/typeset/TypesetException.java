package lerrain.tool.document.typeset;

public class TypesetException extends RuntimeException
{
	private static final long	serialVersionUID	= 1L;

	public TypesetException(String word)
	{
		super(word);
	}

	public TypesetException(String word, Exception e)
	{
		super(word, e);
	}
}
