package lerrain.project.insurance.product.attachment;

public interface AttachmentParser
{
	public static final int XML			= 1;
	public static final int DATABASE	= 2;
	
	public String getName();

	/**
	 * 
	 * @param source
	 * @param type
	 * @return
	 */
	public Object parse(Object source, int type);
}
