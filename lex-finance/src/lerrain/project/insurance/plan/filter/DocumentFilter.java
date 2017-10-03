/**
 * 保险责任
 * 除了旧版本支持的文字段落排版外，额外添加的功能有：
 * 1．可以插入图片。
 * 2．可以插入表格。
 * 3．可以通过插入变量或公式来表示一段可变的文字或数字。
 */
package lerrain.project.insurance.plan.filter;

import java.util.ArrayList;
import java.util.List;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.filter.table.Table;
import lerrain.project.insurance.plan.filter.table.TableFilter;
import lerrain.project.insurance.product.Insurance;
import lerrain.project.insurance.product.attachment.document.DynamicText;
import lerrain.project.insurance.product.attachment.table.TableDef;
import lerrain.tool.formula.Value;


public class DocumentFilter implements FilterCommodity
{
	private static final long serialVersionUID = 1L;
	
	public DocumentFilter()
	{
	}

	public Object filtrate(Commodity p, String attachmentName)
	{
		List result = null;
		
		Insurance ins = p.getProduct();
		List list = (List)(ins.getAttachment(attachmentName));

		if (list != null)
		{
			result = new ArrayList();
			for (int i = 0; i < list.size(); i++)
			{
				Object obj = list.get(i);
				if (obj instanceof DynamicText)
				{
					DynamicText dt = (DynamicText)obj;
					if (dt.getCondition() == null || Value.booleanOf(dt.getCondition(), p.getFactors()))
						result.add(StaticText.textOf(dt, p.getFactors()));
				}
				else if (obj instanceof TableDef)
				{
					TableDef table = (TableDef)obj;
					Table dt = TableFilter.filterTable(table, p.getFactors(), null);
					if (dt != null)
						result.add(dt);
				}
			}
		}
		
		return result;
	}
}
