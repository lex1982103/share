package lerrain.tool.document.typeset.environment;

import lerrain.tool.document.LexFont;
import lerrain.tool.document.typeset.TypesetCoord;

public class TextDimensionSimple implements TextDimension
{

	public TypesetCoord getSize(LexFont font, String text)
	{
		if (text == null)
			return new TypesetCoord(0, 0);
		else
			return new TypesetCoord((int)(text.length() * font.getSize()), (int)font.getSize());
	}

}
