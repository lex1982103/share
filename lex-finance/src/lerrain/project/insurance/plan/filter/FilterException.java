package lerrain.project.insurance.plan.filter;

public class FilterException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public FilterException(String word)
	{
		super(word);
	}
	
	public FilterException(Exception e)
	{
		super(e);
	}
	
	public FilterException(String word, Exception e)
	{
		super(word, e);
	}
}
