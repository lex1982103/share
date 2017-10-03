package lerrain.tool.formula;

public class FormulaException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public FormulaException(String detail)
	{
		super(detail);
	}

	public FormulaException(String detail, Exception e)
	{
		super(detail, e);
	}
}
