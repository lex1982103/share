package lerrain.tool.data;

public class DataParseException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DataParseException(String word)
	{
		super(word);
	}

	public DataParseException(String word, Exception e)
	{
		super(word, e);
	}
}
