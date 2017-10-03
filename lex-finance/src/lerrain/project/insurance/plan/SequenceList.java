package lerrain.project.insurance.plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lerrain.project.insurance.product.Insurance;

public class SequenceList implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	List list = new ArrayList();

	public Commodity addCommodity(Commodity parent, Commodity commodity)
	{
		if (parent == null)
		{
			for (int i = 0; i < list.size(); i++)
			{
				Commodity p = (Commodity)list.get(i);
				if (p.getProduct().isMain() && commodity.getProduct().getSequence() < p.getProduct().getSequence())
				{
					list.add(i, commodity);
					return commodity;
				}
			}
		}
		else
		{
			int index = list.indexOf(parent);
			
			for (int i = index + 1; i < list.size(); i++)
			{
				Commodity p = (Commodity)list.get(i);
				if (p.getProduct().isMain() || (p.getProduct().isRider() && commodity.getProduct().getSequence() < p.getProduct().getSequence()))
				{
					list.add(i, commodity);
					return commodity;
				}
			}
		}

		list.add(commodity);
		return commodity;
	}
	
	public Commodity getCommodity(Insurance insurance)
	{
		for (int i = 0; i < list.size(); i++)
		{
			Commodity p = (Commodity)list.get(i);
			if (p.getProduct().equals(insurance))
				return p;
		}
		
		return null;
	}
	
	public Commodity getCommodity(String productId)
	{
		for (int i = 0; i < list.size(); i++)
		{
			Commodity p = (Commodity)list.get(i);
			if (p.getProduct().getId().equals(productId))
				return p;
		}
		
		return null;
	}
	
	public int size()
	{
		return list.size();
	}
	
	public boolean has(Object val)
	{
		return list.indexOf(val) >= 0;
	}
	
	public void remove(Object val)
	{
		list.remove(val);
	}
	
	public void remove(int index)
	{
		list.remove(index);
	}

	public void removeAll(List l)
	{
		list.removeAll(l);
	}
	
	public void clear()
	{
		list.clear();
	}

	public Commodity get(int index)
	{
		return (Commodity)list.get(index);
	}
	
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	public List toList()
	{
		return list;
	}
}
