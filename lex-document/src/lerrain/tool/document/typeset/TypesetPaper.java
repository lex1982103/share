package lerrain.tool.document.typeset;

import java.util.ArrayList;
import java.util.List;

import lerrain.tool.document.size.Paper;
import lerrain.tool.document.typeset.element.TypesetPanel;

/**
 * 
 *
 */

public class TypesetPaper
{
	String name;
	
	Paper paper;
	
	TypesetPanel body;
	
	List templateList = new ArrayList();
	
	public void addTemplate(TypesetPanel template)
	{
		templateList.add(template);
	}
	
	public int getTemplateNum()
	{
		return templateList.size();
	}
	
	public TypesetPanel getTemplate(int index)
	{
		return (TypesetPanel)templateList.get(index);
	}
	
	public void setBody(TypesetPanel body)
	{
		this.body = body;
	}

	public TypesetPanel getBody()
	{
		return body;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Paper getPaper()
	{
		return paper;
	}

	public void setPaper(Paper paper)
	{
		this.paper = paper;
	}
}
