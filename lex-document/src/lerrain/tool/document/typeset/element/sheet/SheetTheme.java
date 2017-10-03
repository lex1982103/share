package lerrain.tool.document.typeset.element.sheet;

import lerrain.tool.document.typeset.element.TypesetText;

public interface SheetTheme
{
	public void styleTitle(TypesetSheet table, TypesetText text, int i, int j);

	public void styleContent(TypesetSheet table, TypesetText text, int i, int j);
}
