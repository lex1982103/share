package lerrain.tool.document;

/**
 * 文档
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lerrain.tool.document.export.Painter;


public class LexDocument implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	List pageList;

	Map info = new HashMap();

	public LexDocument()
	{
	}

	public void put(String key, Object inf)
	{
		info.put(key, inf);
	}

	public Object get(String key)
	{
		return info.get(key);
	}

	public void addPage(LexPage page)
	{
		if (pageList == null)
			pageList = new ArrayList();
		
		pageList.add(page);
	}
	
	public int size()
	{
		return pageList == null ? 0 : pageList.size();
	}
	
	public LexPage getPage(int index)
	{
		return (LexPage)pageList.get(index);
	}
	
	public LexPage lastPage()
	{
		int s = size();
		return s == 0 ? null : getPage(s - 1);
	}
	
	public LexPage newPage()
	{
		LexPage page = new LexPage();
		addPage(page);
		
		return page;
	}
	
	public void removePage(int index)
	{
		if (index < size())
			pageList.remove(index);
	}
	
	public void export(Painter p, Object f)
	{
		p.paint(this, f, Painter.AUTO);
	}
	
	public void export(Painter p, Object f, int destType)
	{
		p.paint(this, f, destType);
	}

}
