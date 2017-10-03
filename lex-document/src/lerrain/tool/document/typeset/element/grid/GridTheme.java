package lerrain.tool.document.typeset.element.grid;

import lerrain.tool.document.typeset.element.TypesetText;

public interface GridTheme
{
	public void styleTitle(TypesetGrid table, TypesetText text, int i, int j);

	public void styleContent(TypesetGrid table, TypesetText text, int i, int j);
}
