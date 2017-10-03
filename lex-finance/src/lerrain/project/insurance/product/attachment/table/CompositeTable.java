package lerrain.project.insurance.product.attachment.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @deprecated
 * @author lerrain
 */
public class CompositeTable implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final int UNKNOWN		= 0;
	public static final int TABLE		= 1;
	public static final int TEXT		= 2;
	public static final int RESET		= 3;
	
	List content;
	
	public CompositeTable()
	{
		this.content = new ArrayList();
	}
	
	public CompositeTable(List content)
	{
		this.content = content;
	}
	
	public void addTable(TableDef table)
	{
		content.add(table);
	}
	
	public void addText(TableTextDef text)
	{
		content.add(text);
	}
	
	public int size()
	{
		return content.size();
	}
	
	public int getType(int index)
	{
		Object obj = content.get(index);
		
		if (obj instanceof TableDef)
			return TABLE;
		else if (obj instanceof TableTextDef)
			return TEXT;
		else if (obj instanceof TableReset)
			return RESET;
		
		return UNKNOWN;
	}
	
	public Object getElement(int index)
	{
		return content.get(index);
	}

	/**
	 * @deprecated
	 * @param index
	 * @return
	 */
	public TableDef getTable(int index)
	{
		Object obj = content.get(index);
		
		if (obj instanceof TableDef)
			return (TableDef)obj;
		
		return null;
	}
	
	/**
	 * @deprecated
	 * @param index
	 * @return
	 */
	public TableTextDef getText(int index)
	{
		Object obj = content.get(index);
		
		if (obj instanceof TableTextDef)
			return (TableTextDef)obj;
		
		return null;
	}
}
