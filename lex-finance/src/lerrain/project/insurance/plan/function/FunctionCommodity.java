package lerrain.project.insurance.plan.function;

import lerrain.project.insurance.plan.Commodity;

public interface FunctionCommodity
{
	public String getName();

	public abstract Object runCommodity(Commodity p, Object[] v);
}
