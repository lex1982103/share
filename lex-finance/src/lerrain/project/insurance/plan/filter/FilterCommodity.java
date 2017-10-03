package lerrain.project.insurance.plan.filter;

import java.io.Serializable;

import lerrain.project.insurance.plan.Commodity;


public interface FilterCommodity extends Serializable
{
	public Object filtrate(Commodity product, String attachmentName);
}
