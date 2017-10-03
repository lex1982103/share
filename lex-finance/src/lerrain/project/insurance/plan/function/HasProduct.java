package lerrain.project.insurance.plan.function;

import java.io.Serializable;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Plan;

public class HasProduct implements FunctionCommodity, FunctionPlan, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "HasProduct";
	}
	
//	private boolean has(Object[] param, String p)
//	{
//		if (param == null || param.length == 0)
//			return true;
//
//		for (int i = 0; i < param.length; i++)
//		{
//			if (p.equals(param[i]))
//				return true;
//		}
//
//		return false;
//	}
	
	private boolean has(Object p, String productId)
	{
		if (p == null)
			return true;

		if (p instanceof Object[])
		{
			Object[] param = (Object[])p;
			for (int i = 0; i < param.length; i++)
			{
				if (productId.equals(param[i]))
					return true;
			}
		}
		else
		{
			if (p.equals(productId))
				return true;
		}

		return false;
	}
	
	public Object runCommodity(Commodity p, Object[] param) 
	{
		if (param == null || param.length == 0)
		{
			return new Boolean(true);
		}
		else if (param.length == 1)
		{
			int num = p.getPlan().size();
			for (int i = 0; i < num; i++)
			{
				Commodity c = p.getPlan().getCommodity(i);
				if (p.equals(c.getParent()) && has(param[0], c.getProduct().getId()))
					return new Boolean(true);
			}
		}
		else if (param.length == 2)
		{
			String insurantId = (String)param[1];
			if (insurantId != null && insurantId.equals(p.getPlan().getInsurant().getId())) //主被保险人的ID内部固定设为null，这点将来需要调整
				insurantId = null;
			
			int num = p.getPlan().size();
			for (int i = 0; i < num; i++)
			{
				Commodity c = p.getPlan().getCommodity(i);
				if (p.equals(c.getParent()) && ((c.getInsurantId() == null && insurantId == null) || (insurantId != null && insurantId.equals(c.getInsurantId()))) && has(param[0], c.getProduct().getId()))
					return new Boolean(true);
			}
		}
		
		return new Boolean(false);
	}

	public Object runPlan(Plan p, Object[] param)
	{
		if (param == null || param.length == 0)
		{
			return new Boolean(true);
		}
		else if (param.length == 1)
		{
			int num = p.size();
			for (int i = 0; i < num; i++)
			{
				Commodity c = p.getCommodity(i);
				if (has(param[0], c.getProduct().getId()))
					return new Boolean(true);
			}
		}
		else if (param.length == 2)
		{
			String insurantId = (String)param[1];
			if (insurantId != null && insurantId.equals(p.getInsurant().getId())) //主被保险人的ID内部固定设为null，这点将来需要调整
				insurantId = null;
			
			int num = p.size();
			for (int i = 0; i < num; i++)
			{
				Commodity c = p.getCommodity(i);
				if (((c.getInsurantId() == null && insurantId == null) || (insurantId != null && insurantId.equals(c.getInsurantId()))) && has(param[0], c.getProduct().getId()))
					return new Boolean(true);
			}
		}
		
		return new Boolean(false);
	}
}