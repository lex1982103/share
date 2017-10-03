package lerrain.project.insurance.product.attachment.liability;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Formula;

public class LiabilityDef implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_TABLE		= 1;
	public static final int TYPE_TEXT		= 2;
	public static final int TYPE_GROUP		= 3;
	
	String title;
	
	int type = TYPE_GROUP;
	
	Formula condition;

	Object content;
	
	List paraList = new ArrayList();
	
	public int size()
	{
		return paraList.size();
	}
	
	public LiabilityDef getParagraph(int index)
	{
		return (LiabilityDef)paraList.get(index);
	}

	public void addParagraph(LiabilityDef l)
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

	public Formula getCondition()
	{
		return condition;
	}

	public void setCondition(Formula condition)
	{
		this.condition = condition;
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
