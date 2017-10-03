package lerrain.project.insurance.product.load;


public class ProductParseException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ProductParseException(String word)
	{
		super(word);
	}

	public ProductParseException(String word, Exception e)
	{
		super(word, e);
	}
}
