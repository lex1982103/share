package lerrain.tool.document.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DocumentPanel extends LexElement
{
	private static final long serialVersionUID = 1L;
	
	List elementList = new ArrayList();
	
	Map additional;
	
	int type;
	
	public void add(LexElement element)
	{
		elementList.add(element);
	}
	
	public int getElementCount()
	{
		return elementList.size();
	}
	
	public LexElement getElement(int index)
	{
		return (LexElement)elementList.get(index);
	}
	
	public void clear()
	{
		elementList.clear();
	}

	public void setAdditional(String name, Object value)
	{
		if (additional == null)
			additional = new HashMap();
		
		additional.put(name, value);
	}
	
	public Object getAdditional(String name)
	{
		return additional == null ? null : additional.get(name);
	}
	
	public LexElement copy() 
	{
		DocumentPanel e = new DocumentPanel();
		e.setAll(this);
		e.setType(type);
		
		if (elementList != null)
		{
			Iterator iter = elementList.iterator();
			while (iter.hasNext())
			{
				e.add(((LexElement)iter.next()).copy());
			}
		}
		
		if (additional != null)
		{
			e.additional = new HashMap();
			e.additional.putAll(additional);
		}
		
		return e;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}
}