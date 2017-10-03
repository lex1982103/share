package lerrain.tool.document.typeset;

public class TypesetBuildException extends TypesetException
{
	private static final long serialVersionUID = 1L;

	public TypesetBuildException(String word)
	{
		super(word);
	}

	public TypesetBuildException(String word, Exception e)
	{
		super(word, e);
	}
}