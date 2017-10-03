package lerrain.project.insurance.product.attachment.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableDef implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final int UNKNOWN		= 0;
	public static final int ROW			= 1;
	public static final int LOOP		= 2;
	
	String name;
	
	String code;

	List rowList = new ArrayList();
	
	Map additional;
	
	public void addRow(TableRow row)
	{
		rowList.add(row);
	}
	
	public void addLoop(TableLoop loop)
	{
		rowList.add(loop);
	}
	
	public Object getElement(int index)
	{
		return rowList.get(index);
	}
	
	public int getType(int index)
	{
		Object obj = rowList.get(index);
		
		if (obj instanceof TableRow)
			return ROW;
		else if (obj instanceof TableLoop)
			return LOOP;
		
		return UNKNOWN;
	}
	
	public int size()
	{
		return rowList.size();
	}
	
	public void setAdditional(String name, Object value)
	{
		if (additional == null)
			additional = new HashMap();
		
		additional.put(name, value);
	}
	
	public Object getAdditional(String name)
	{
		if (additional == null)
			return null;
		
		return additional.get(name);
	}
	
	public Map getAdditional()
	{
		return additional;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
