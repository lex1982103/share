package lerrain.project.insurance.plan;

import lerrain.project.insurance.product.Insurance;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnstableList implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Factors f;

	private List temp = new ArrayList();
	private List list;

	private Map cm = new HashMap();

	public UnstableList(Factors f)
	{
		this.f = f;
	}

	public Commodity addCommodity(Commodity parent, Commodity commodity, Formula c)
	{
		if (parent == null)
		{
			for (int i = 0; i < temp.size(); i++)
			{
				Commodity p = (Commodity)temp.get(i);
				if (p.getProduct().isMain() && commodity.getProduct().getSequence() < p.getProduct().getSequence())
				{
					temp.add(i, commodity);

					if (c != null)
						cm.put(commodity, c);

					return commodity;
				}
			}
		}
		else
		{
			int index = temp.indexOf(parent);
			
			for (int i = index + 1; i < temp.size(); i++)
			{
				Commodity p = (Commodity)temp.get(i);
				if (p.getProduct().isMain() || (p.getProduct().isRider() && commodity.getProduct().getSequence() < p.getProduct().getSequence()))
				{
					temp.add(i, commodity);

					if (c != null)
						cm.put(commodity, c);

					return commodity;
				}
			}
		}

		temp.add(commodity);

		if (c != null)
			cm.put(commodity, c);

		return commodity;
	}

	public void clearCache()
	{
		for (int i = 0; i < temp.size(); i++)
		{
			Commodity c = (Commodity)temp.get(i);
			c.clearCache();
		}

		list = null;
	}

	private List list()
	{
		if (list == null)
		{
			list = new ArrayList();
			for (int i = 0; i < temp.size(); i++)
			{
				Commodity p = (Commodity)temp.get(i);
				if (cm.containsKey(p))
				{
					Formula formula = (Formula)cm.get(p);
					if (Value.booleanOf(formula, f))
						list.add(p);
				}
				else
				{
					list.add(p);
				}
			}
		}

		return list;
	}
	
	public Commodity getCommodity(Insurance insurance)
	{
		for (int i = 0; i < temp.size(); i++)
		{
			Commodity p = (Commodity)temp.get(i);
			if (p.getProduct().equals(insurance))
				return p;
		}
		
		return null;
	}
	
	public Commodity getCommodity(String productId)
	{
		for (int i = 0; i < temp.size(); i++)
		{
			Commodity p = (Commodity)temp.get(i);
			if (p.getProduct().getId().equals(productId))
				return p;
		}
		
		return null;
	}
	
	public int size()
	{
		return list().size();
	}
	
	public boolean has(Object val)
	{
		return list().indexOf(val) >= 0;
	}

	public Commodity get(int index)
	{
		return (Commodity)list().get(index);
	}
	
	public boolean isEmpty()
	{
		return list().isEmpty();
	}

	public List toList()
	{
		return list();
	}
}
