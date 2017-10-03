package lerrain.project.insurance.product.attachment.table;

import java.io.Serializable;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;

public class TableSpan implements Formula, Serializable
{
	private static final long serialVersionUID = 1L;

	Integer value;
	
	public TableSpan(int v)
	{
		value = new Integer(v);
	}
	
	public String toString()
	{
		return value.toString();
	}

	public Object run(Factors factors) 
	{
		return value;
	}
}
