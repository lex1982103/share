package lerrain.tool.document.element;


public class DocumentReset extends LexElement
{
	private static final long serialVersionUID = 1L;

	boolean newPage;
	
	public DocumentReset(boolean newPage)
	{
		this.newPage = newPage;
	}
	
	public boolean isNewPage()
	{
		return newPage;
	}

	public void setNewPage(boolean newPage)
	{
		this.newPage = newPage;
	}
	
	public LexElement copy() 
	{
		DocumentReset e = new DocumentReset(newPage);
		e.setAll(this);
		
		return e;
	}
}
