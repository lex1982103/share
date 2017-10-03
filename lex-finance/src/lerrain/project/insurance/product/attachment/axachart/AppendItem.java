package lerrain.project.insurance.product.attachment.axachart;

import java.io.Serializable;

import lerrain.tool.formula.Formula;

public class AppendItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	Formula start;
	Formula end;
	Formula content;
	
	String name;
	String type;
	String color;
	
	public AppendItem()
	{
	}
	
	public AppendItem(String name, String type, String color)
	{
		this.name = name;
		this.type = type;
		this.color = color;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	public String getColor()
	{
		return color;
	}
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public Formula getStart()
	{
		return start;
	}
	public void setStart(Formula start)
	{
		this.start = start;
	}
	
	public Formula getEnd()
	{
		return end;
	}
	public void setEnd(Formula end)
	{
		this.end = end;
	}
	
	public Formula getContent()
	{
		return content;
	}

	public void setContent(Formula content)
	{
		this.content = content;
	}
}
