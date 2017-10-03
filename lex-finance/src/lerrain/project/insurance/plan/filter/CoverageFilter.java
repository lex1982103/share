/**
 * 保险责任
 * 除了旧版本支持的文字段落排版外，额外添加的功能有：
 * 1．可以插入图片。
 * 2．可以插入表格。
 * 3．可以通过插入变量或公式来表示一段可变的文字或数字。
 */
package lerrain.project.insurance.plan.filter;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.filter.table.Table;
import lerrain.project.insurance.plan.filter.table.TableFilter;
import lerrain.project.insurance.product.Insurance;
import lerrain.project.insurance.product.attachment.coverage.Coverage;
import lerrain.project.insurance.product.attachment.coverage.CoverageParagraph;
import lerrain.project.insurance.product.attachment.table.TableDef;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;


public class CoverageFilter implements FilterCommodity
{
	private static final long serialVersionUID = 1L;
	
	public CoverageFilter()
	{
	}

	public Object filtrate(Commodity p, String attachmentName)
	{
		Insurance ins = p.getProduct();
		Coverage coverage = (Coverage)(ins.getAttachment(attachmentName));
		
		int c = coverage.getParagraphCount();

		Coverage result = new Coverage();
		for (int i = 0; i < c; i++)
		{
			CoverageParagraph cp = coverage.getParagraph(i);
			
			if (cp.getCondition() == null || Value.booleanOf(cp.getCondition(), p.getFactors()))
				result.addParagrpah(compileParagraph(cp, p.getFactors()));
		}
		
		return result;
	}
	
	private CoverageParagraph compileParagraph(CoverageParagraph cp, Factors factors)
	{
		CoverageParagraph r = new CoverageParagraph();
		r.setTitle(cp.getTitle());
		
		for (int i=0;i<cp.size();i++)
		{
			int type = cp.getType(i);
			Object o = cp.getContent(i);
			
			if (type == CoverageParagraph.TEXT)
			{
				r.addContent(type, o);
			}
			else if (type == CoverageParagraph.FORMULA)
			{
				Object value = ((Formula)o).run(factors);
				r.addContent(CoverageParagraph.TEXT, value == null ? null : value.toString());
			}
			else if (type == CoverageParagraph.TABLE)
			{
				Table table = TableFilter.filterTable((TableDef)o, factors, null);
				r.addContent(type, table);
			}
		}
		
		return r;
	}
}
