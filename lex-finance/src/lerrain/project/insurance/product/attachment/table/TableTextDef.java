package lerrain.project.insurance.product.attachment.table;

import lerrain.project.insurance.plan.filter.table.TableText;
import lerrain.tool.formula.Formula;


/**
 * @deprecated
 * @author lerrain
 *
 */
public class TableTextDef extends TableText
{
	private static final long serialVersionUID = 1L;
	
	Formula condition;

	public Formula getCondition()
	{
		return condition;
	}

	public void setCondition(Formula condition)
	{
		this.condition = condition;
	}
}
