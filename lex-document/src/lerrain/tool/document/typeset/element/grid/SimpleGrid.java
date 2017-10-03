package lerrain.tool.document.typeset.element.grid;

import lerrain.tool.document.typeset.element.TypesetText;

public class SimpleGrid implements GridTheme
{
	public void styleContent(TypesetGrid table, TypesetText tx, int i, int j)
	{
//		tx.setLeftBorder(j == 0 ? -1 : 1);
//		tx.setTopBorder(1);
//		tx.setRightBorder(-1);
//		tx.setBottomBorder(-1);
		tx.setLeftBorder(1);
		tx.setTopBorder(1);
		tx.setRightBorder(1);
		tx.setBottomBorder(1);
	}

	public void styleTitle(TypesetGrid table, TypesetText tx, int i, int j)
	{
//		tx.setLeftBorder(j == 0 ? -1 : 1);
//		tx.setTopBorder(i == 0 ? -1 : 1);
//		tx.setRightBorder(-1);
//		tx.setBottomBorder(-1);
		tx.setLeftBorder(1);
		tx.setTopBorder(1);
		tx.setRightBorder(1);
		tx.setBottomBorder(1);
	}
}
