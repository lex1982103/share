package lerrain.project.insurance.product.attachment.coverage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Formula;

public class CoverageParagraph implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static int UNKNOWN		= 0;
	public static int TABLE 		= 1;
	public static int TEXT			= 2;
	public static int FORMULA		= 9;
	
	String title;
	
	Formula condition;
	
	List typeList = new ArrayList();
	List itemList = new ArrayList();

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Formula getCondition()
	{
		return condition;
	}

	public void setCondition(Formula condition)
	{
		this.condition = condition;
	}
	
	public int getType(int index)
	{
//		Object obj = itemList.get(index);
//		
//		if (obj instanceof String)
//			return TEXT;
//		else if (obj instanceof Formula)
//			return FORMULA;
//		else if (obj instanceof TableDef)
//			return TABLE;
//		
//		return UNKNOWN;
		
		return ((Integer)typeList.get(index)).intValue();
	}
	
	public int size()
	{
		return itemList.size();
	}

	public Object getContent(int index)
	{
		return itemList.get(index);
	}
	
	public void addContent(int type, Object content)
	{
		typeList.add(new Integer(type));
		itemList.add(content);
	}

	/**
	 * @deprecated
	 */
	public int getItemCount()
	{
		return itemList.size();
	}
	
	/**
	 * @deprecated
	 */
	public String getItem(int index)
	{
		return (String)itemList.get(index);
	}
	
	/**
	 * @deprecated
	 */
	public void addItem(String item)
	{
		itemList.add(item);
	}
}
