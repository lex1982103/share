package lerrain.tool.document.typeset.environment;

import lerrain.tool.document.LexFont;
import lerrain.tool.document.typeset.TypesetCoord;

public interface TextDimension
{
	/**
	 * 返回单行字符串在font字体时的长和高，用于text对象切割字符串换行。
	 * @param font
	 * @param text
	 * @return
	 */
	public TypesetCoord getSize(LexFont font, String text);
}
