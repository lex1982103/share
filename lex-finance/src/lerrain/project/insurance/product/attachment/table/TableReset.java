package lerrain.project.insurance.product.attachment.table;

import java.io.Serializable;

public class TableReset implements Serializable
{
	private static final long serialVersionUID = 1L;

	int skip;
	
	boolean newPage;

	public int getSkip()
	{
		return skip;
	}
	public void setSkip(int skip)
	{
		this.skip = skip;
	}
	public boolean isNewPage()
	{
		return newPage;
	}
	public void setNewPage(boolean newPage)
	{
		this.newPage = newPage;
	}
}
