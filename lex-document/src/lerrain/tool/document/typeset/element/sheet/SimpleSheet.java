package lerrain.tool.document.typeset.element.sheet;

import lerrain.tool.document.typeset.element.TypesetText;

public class SimpleSheet implements SheetTheme
{
	public void styleContent(TypesetSheet table, TypesetText tx, int i, int j)
	{
		tx.setLeftBorder(0);
		tx.setTopBorder(0);
		tx.setRightBorder(0);
		tx.setBottomBorder(0);
	}

	public void styleTitle(TypesetSheet table, TypesetText tx, int i, int j)
	{
		tx.setLeftBorder(j == 0 ? -1 : 1);
		tx.setTopBorder(i == 0 ? -1 : 1);
		tx.setRightBorder(-1);
		tx.setBottomBorder(-1);
	}
}
