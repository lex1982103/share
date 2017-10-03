package lerrain.tool.document.typeset;

import java.util.*;

import lerrain.tool.document.LexPage;
import lerrain.tool.document.element.DocumentPanel;
import lerrain.tool.document.element.DocumentReset;
import lerrain.tool.document.element.DocumentText;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.element.TypesetElement;
import lerrain.tool.document.typeset.element.TypesetPanel;
import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;

/**
 * The definition of the document paragraph, include paper templates and paragraph elements.<p>
 *  
 * After a paragraph layout can generate one or more pages, paragraphs, arranged in sequence
 * elements in these pages.<p>
 * 
 * Paragraph of each page using the page template (including the header and footer, etc.), 
 * therefore, within the pages of the page paragraph style is the same, want to change the page 
 * style, it must be divided into multiple paragraphs.<p>
 * 
 * Adjacent two paragraphs if the page style is different, the last page of the preceding 
 * paragraphs and paragraphs of the first page of the back of a page can not be spliced.<p>

 * @author lerrain(lerrain@gmail.com)
 */

public class TypesetParagraph
{
	Formula visible;

	TypesetPaper typesetPaper;
	
	List elementList = new ArrayList();
	
	public void add(TypesetElement element)
	{
		elementList.add(element);
	}
	
	public int getElementNum()
	{
		return elementList.size();
	}
	
	public TypesetElement getElement(int index)
	{
		return (TypesetElement)elementList.get(index);
	}

	public Formula getVisible()
	{
		return visible;
	}

	public void setVisible(Formula visible)
	{
		this.visible = visible;
	}

	public TypesetPaper getTypesetPaper()
	{
		return typesetPaper;
	}

	public void setTypesetPaper(TypesetPaper typesetPaper)
	{
		this.typesetPaper = typesetPaper;
	}
}
