package lerrain.tool.document;

public class DocumentExportException extends RuntimeException
{
	private static final long	serialVersionUID	= 1L;

	public DocumentExportException(String word)
	{
		super(word);
	}

	public DocumentExportException(String word, Exception e)
	{
		super(word, e);
	}
}
