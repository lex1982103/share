package lerrain.tool.transaction;


public interface Behavior
{
	public boolean perform();
	
	public void rollback();
	
	public void commit();
}
