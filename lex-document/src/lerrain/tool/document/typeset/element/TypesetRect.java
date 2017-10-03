package lerrain.tool.document.typeset.element;

import lerrain.tool.document.element.DocumentRect;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetParameters;

public class TypesetRect extends TypesetElement
{
	public LexElement build(TypesetParameters tvs)
	{
		DocumentRect rect = new DocumentRect();
		
		rect.setX(x.value(tvs));
		rect.setY(tvs.getDatum() + y.value(tvs));
		rect.setWidth(width.value(tvs));
		rect.setHeight(height.value(tvs));
		
		rect.setColor(this.getColor());
		
		resetY(tvs, rect);
		
		return rect;
	}
}
