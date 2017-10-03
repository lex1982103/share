package lerrain.project.insurance.plan.function;

import java.io.Serializable;
import java.util.Map;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Plan;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;

/**
 * 风险保额
 * @author lerrain
 *
 */
public class RiskAmount implements FunctionCommodity, FunctionPlan, Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String getName()
	{
		return "RiskAmount";
	}
	
	public Object runCommodity(Commodity c, Object[] param) 
	{
		return runPlan(c.getPlan(), param);
	}

	public Object runPlan(Plan p, Object[] param)
	{
		double amount = 0;
		
		String type = (String)param[0];
		String customerId = param.length > 1 ? (String)param[1] : null;
		
		for (int i = 0; i < p.size(); i++)
		{
			Commodity c = p.getCommodity(i);

			if (customerId == null && c.getInsurantId() != null)
				continue;
			if (customerId != null && c.getInsurantId() == null)
				continue;
			if (customerId != null && !customerId.equals(c.getInsurantId()))
				continue;
			
			Formula f = c.getProduct().getAccumulativeAmount(type);
			if (f != null)
			{
				amount += Value.doubleOf(f, c.getFactors());
			}
//			else if (type.equals(c.getProduct().getProductType()))
//			{
//				amount += c.getAmount();
//			}
			else if (c.getProduct().getProductType() != null)
			{
				Map accu = c.getCompany().getAccumulation(c.getProduct().getProductType());
				if (accu != null)
				{
					f = (Formula)accu.get(type);
					if (f != null)
						amount += Value.doubleOf(f, c.getFactors());
				}
			}
		}
		
		return new Double(amount);
	}
}