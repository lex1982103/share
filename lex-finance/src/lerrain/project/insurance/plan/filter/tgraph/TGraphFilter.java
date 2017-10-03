package lerrain.project.insurance.plan.filter.tgraph;

import java.util.ArrayList;
import java.util.List;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.filter.FilterCommodity;
import lerrain.project.insurance.product.attachment.tgraph.TGraphItemDynamic;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;


public class TGraphFilter implements FilterCommodity
{
	private static final long serialVersionUID = 1L;

	public TGraphFilter()
	{
	}

	public Object filtrate(Commodity product, String attachmentName)
	{
		List items = (List)product.getProduct().getAttachment(attachmentName);
		if (items == null)
			return null;

		List result = new ArrayList();
		Factors factors = product.getFactors();
		
		for(int i=0;i<items.size();i++)
		{
			Object line = items.get(i);
			
			if (line instanceof TGraphItemDynamic)
			{
				TGraphItemDynamic ff = (TGraphItemDynamic)line;
				
				boolean pass = ff.getCondition() == null ? true : Value.booleanOf(ff.getCondition(), factors);
				
				if (pass)
				{
					String text = (String)ff.getText(factors);
					TGraphItem item = ff.hasValue() ? new TGraphItem(text, ff.getValue(factors)) : new TGraphItem(text);
					item.setBold(ff.isBold());
					
					result.add(item);
				}
			}
		}
		
		return result;
	}
}
