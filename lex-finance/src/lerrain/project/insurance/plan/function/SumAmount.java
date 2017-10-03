package lerrain.project.insurance.plan.function;

import java.io.Serializable;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Plan;

/**
 * 求指定险种的保额之和
 * 运行在产品下，求的是该产品所有符合条件的附加险的保额之和
 * 运行在计划下，求的是所有符合条件的险种的保额之和
 * @author lerrain
 *
 */
public class SumAmount implements FunctionCommodity, FunctionPlan, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "SumAmount";
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
	
	public Object runCommodity(Commodity p, Object[] param) 
	{
		double amount = 0;
		int num = p.getPlan().size();
		
		for (int i = 0; i < num; i++)
		{
			Commodity c = p.getPlan().getCommodity(i);
			if (p.equals(c.getParent()) && has(param, c.getProduct().getId()))
				amount += c.getAmount();
		}
		
		return new Double(amount);
	}

	public Object runPlan(Plan p, Object[] param)
	{
		double amount = 0;
		int num = p.size();
		
		for (int i = 0; i < num; i++)
		{
			Commodity c = p.getCommodity(i);
			if (has(param, c.getProduct().getId()))
				amount += c.getAmount();
		}
		
		return new Double(amount);
	}
}