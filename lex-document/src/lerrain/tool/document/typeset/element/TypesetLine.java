package lerrain.tool.document.typeset.element;

import lerrain.tool.document.element.DocumentLine;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetNumber;
import lerrain.tool.document.typeset.TypesetParameters;

public class TypesetLine extends TypesetElement
{
	TypesetNumber x1, x2, y1, y2;
	
	public TypesetLine(String x1, String y1, String x2, String y2)
	{
		this.x1 = TypesetNumber.numberOf(x1);
		this.y1 = TypesetNumber.numberOf(y1);
		this.x2 = TypesetNumber.numberOf(x2);
		this.y2 = TypesetNumber.numberOf(y2);
	}
	
	public LexElement build(TypesetParameters tvs)
	{
		DocumentLine line = new DocumentLine();
		
		line.setX(x1.value(tvs));
		line.setY(tvs.getDatum() + y1.value(tvs));
		line.setWidth(x2.value(tvs) - x1.value(tvs));
		line.setHeight(y2.value(tvs) - y1.value(tvs));
		
		line.setColor(this.getColor());
		
		resetY(tvs, line);
		
		return line;
	}
}
