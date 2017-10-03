package lerrain.project.insurance.plan.function;

import lerrain.project.insurance.plan.Plan;

public interface FunctionPlan
{
	public String getName();
	
	public abstract Object runPlan(Plan p, Object[] v);
}
