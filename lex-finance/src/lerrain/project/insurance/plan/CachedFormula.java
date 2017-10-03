package lerrain.project.insurance.plan;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;

public class CachedFormula implements Formula
{
	Formula formula;
	
	Object result = null;
	
	public CachedFormula(Formula f)
	{
		this.formula = f;
	}

	public Object run(Factors factors)
	{
		if (result == null)
			result = formula.run(factors);
		
		return result;
	}

	public void clearCache()
	{
		result = null;
	}
}
