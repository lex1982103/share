package lerrain.project.insurance.plan.function;


public class FunctionCalculateException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public FunctionCalculateException(String word)
	{
		super(word);
	}

	public FunctionCalculateException(String word, Exception e)
	{
		super(word, e);
	}
}
