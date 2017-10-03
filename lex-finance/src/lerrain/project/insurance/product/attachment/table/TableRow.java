package lerrain.project.insurance.product.attachment.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Formula;

public class TableRow implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final int TYPE_TITLE = 1;
	public static final int TYPE_DATA = 2;
	
	int type = TYPE_DATA;
	
	List blankList = new ArrayList();
	
	Formula condition;
	
	public void addBlank(TableBlank blank)
	{
		blankList.add(blank);
	}
	
	public TableBlank getBlank(int index)
	{
		return (TableBlank)blankList.get(index);
	}
	
	public int getSize()
	{
		return blankList.size();
	}
	
	public int size()
	{
		return blankList.size();
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public Formula getCondition()
	{
		return condition;
	}

	public void setCondition(Formula condition)
	{
		this.condition = condition;
	}
}
