package lerrain.tool.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.size.Paper;

public class LexPage implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	Paper paper;
	
	List elementList = new ArrayList();
	
	public void add(LexElement element)
	{
		elementList.add(element);
	}
	
	public void remove(LexElement element)
	{
		elementList.remove(element);
	}
	
	public int getElementNum()
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

	public Paper getPaper()
	{
		return paper;
	}

	public void setPaper(Paper paper)
	{
		this.paper = paper;
	}

	public int find(LexElement element)
	{
		return elementList.indexOf(element);
	}
}
