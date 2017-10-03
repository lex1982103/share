package lerrain.project.insurance.plan.function;

import java.io.Serializable;

import lerrain.project.insurance.plan.Commodity;

public class ProductOption implements FunctionCommodity, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "ProductOption";
	}
	
	public Object runCommodity(Commodity p, Object[] param) 
	{
		return p.getInput((String)param[0]);
	}
}