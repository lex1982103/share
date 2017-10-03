package lerrain.project.insurance.plan.filter;

import java.io.Serializable;

import lerrain.project.insurance.plan.Plan;


public interface FilterPlan extends Serializable
{
	public Object filtrate(Plan plan, String attachmentName);
}
