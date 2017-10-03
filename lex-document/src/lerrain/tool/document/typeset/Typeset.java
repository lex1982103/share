package lerrain.tool.document.typeset;

/**
 * 
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lerrain.tool.document.DocumentUtil;
import lerrain.tool.document.LexFontFamily;

public class Typeset implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String id;
	
	Map fontMap = new HashMap();
	Map pageMap = new HashMap();
	
	List paragraphList = new ArrayList();
	
	public Typeset()
	{
	}
	
	public void addParagraph(Object paragraph)
	{
		paragraphList.add(paragraph);
	}
	
	public int getParagraphNum()
	{
		return paragraphList.size();
	}
	
	public Object getParagraph(int index)
	{
		return (Object)paragraphList.get(index);
	}
	
	public void addFontFamily(TypesetFontFamily ff)
	{
		fontMap.put(ff.getName(), ff);
	}
	
	public Map getFontFamilies()
	{
		return fontMap;
	}
	
	public LexFontFamily getFontFamily(String name)
	{
		LexFontFamily font = (LexFontFamily)fontMap.get(name);
		
		if (font == null)
			font = DocumentUtil.getFont(name);
		
		return font;
	}
	
	public void addPageDefine(String name, TypesetPaper pageDef)
	{
		pageMap.put(name, pageDef);
	}
	
	public TypesetPaper getPageDefine(String name)
	{
		return (TypesetPaper)pageMap.get(name);
	}
	
	public void setPageDefineDefault(TypesetPaper pageDef)
	{
		pageMap.put("@default", pageDef);
	}
	
	public TypesetPaper getPageDefineDefault()
	{
		return (TypesetPaper)pageMap.get("@default");
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
