/**
 * 保险责任
 * 除了旧版本支持的文字段落排版外，额外添加的功能有：
 * 1．可以插入图片。
 * 2．可以插入表格。
 * 3．可以通过插入变量或公式来表示一段可变的文字或数字。
 */
package lerrain.project.insurance.plan.filter.liability;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.filter.FilterCommodity;
import lerrain.project.insurance.plan.filter.table.TableFilter;
import lerrain.project.insurance.product.Insurance;
import lerrain.project.insurance.product.attachment.coverage.CoverageParagraph;
import lerrain.project.insurance.product.attachment.liability.LiabilityDef;
import lerrain.project.insurance.product.attachment.table.TableDef;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;


public class LiabilityFilter implements FilterCommodity
{
	private static final long serialVersionUID = 1L;
	
	public LiabilityFilter()
	{
	}

	public Object filtrate(Commodity p, String attachmentName)
	{
		Insurance ins = p.getProduct();
		LiabilityDef coverage = (LiabilityDef)(ins.getAttachment(attachmentName));
		
		int c = coverage.size();

		Liability result = new Liability();
		for (int i = 0; i < c; i++)
		{
			LiabilityDef cp = coverage.getParagraph(i);
			
			if (cp.getCondition() == null || Value.booleanOf(cp.getCondition(), p.getFactors()))
				result.addParagraph(compileParagraph(cp, p.getFactors()));
		}
		
		return result;
	}
	
	private Liability compileParagraph(LiabilityDef cp, Factors factors)
	{
		Liability r = new Liability();
		r.setTitle(cp.getTitle());
		
		for (int i=0;i<cp.size();i++)
		{
			LiabilityDef j = cp.getParagraph(i);
			
			int type = j.getType();
			Formula c = j.getCondition();
			Object o = j.getContent();
			
			if (c != null && !Value.booleanOf(c, factors))
				continue;
			
			if (type == LiabilityDef.TYPE_TEXT)
			{
				Liability k = new Liability();
				k.setType(LiabilityDef.TYPE_TEXT);
				k.setContent(o instanceof Formula ? Value.stringOf((Formula)o, factors) : o.toString());
				r.addParagraph(k);
			}
			else if (type == LiabilityDef.TYPE_TABLE)
			{
				Liability k = new Liability();
				k.setType(LiabilityDef.TYPE_TABLE);
				k.setContent(TableFilter.filterTable((TableDef)o, factors, null));
				r.addParagraph(k);
			}
		}
		
		return r;
	}
}
