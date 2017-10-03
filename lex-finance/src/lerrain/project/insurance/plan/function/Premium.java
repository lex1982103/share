package lerrain.project.insurance.plan.function;

import java.io.Serializable;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Plan;

/**
 * 获取保费
 * 如果是险种，则返回该险种和它所有附加险保费和。
 * 如果是计划，则返回整个计划的保费和。
 * 
 * @author lerrain
 */
public class Premium implements FunctionCommodity, FunctionPlan, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "Premium";
	}
	
	private boolean has(Object[] param, String p)
	{
		if (param == null || param.length == 0)
			return true;

		for (int i = 0; i < param.length; i++)
		{
			if (p.equals(param[i]))
				return true;
		}

		return false;
	}

	public Object runPlan(Plan p, Object[] param)
	{
		double premium = 0;

		for (int i = 0; i < p.size(); i++)
		{
			Commodity product = p.getCommodity(i);
			if (has(param, product.getProduct().getId()))
				premium += product.getPremium();
		}

		return Double.valueOf(premium);
	}

	public Object runCommodity(Commodity p, Object[] param)
	{
		double premium = 0;

		Plan plan = p.getPlan();
		for (int i = 0; i < plan.size(); i++)
		{
			Commodity r = plan.getCommodity(i);
			if ((r.getParent() == p || r == p) && has(param, r.getProduct().getId()))
				premium += r.getPremium();
		}

		return Double.valueOf(premium);
	}
}