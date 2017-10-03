package lerrain.tool.document.typeset.parser;

import lerrain.tool.document.typeset.TypesetException;

public class TypesetParseException extends TypesetException
{
	private static final long	serialVersionUID	= 1L;

	public TypesetParseException(String word)
	{
		super(word);
	}

	public TypesetParseException(String word, Exception e)
	{
		super(word, e);
	}
}
