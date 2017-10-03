package lerrain.project.insurance.product.attachment.table;

import java.util.List;

/**
 * @deprecated
 * @author lerrain
 */
public class CompositeParser extends TableParser
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "table_composite";
	}

	public Object parse(Object source, int type)
	{
		List result = (List)super.parse(source, type);
		
		return new CompositeTable(result);
	}
}
