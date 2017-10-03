package lerrain.project.insurance.plan.filter.liability;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Liability implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_TABLE		= 1;
	public static final int TYPE_TEXT		= 2;
	public static final int TYPE_GROUP		= 3;
	
	String title;
	
	int type = TYPE_GROUP;

	Object content;
	
	List paraList = new ArrayList();
	
	public int size()
	{
		return paraList.size();
	}
	
	public Liability getParagraph(int index)
	{
		return (Liability)paraList.get(index);
	}

	public void addParagraph(Liability l)
	{
		paraList.add(l);
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public Object getContent()
	{
		return content;
	}

	public void setContent(Object content)
	{
		this.content = content;
	}
}
