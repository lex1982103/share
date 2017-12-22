package lerrain.tool.document.element;

import java.util.HashMap;
import java.util.Map;



public class DocumentImage extends LexElement
{
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_FILE	= 1; //文件
	public static final int TYPE_PATH	= 2; //文件路径
	public static final int TYPE_SRC	= 3; //文件相对路径
	public static final int TYPE_URL	= 4; //url
	public static final int TYPE_BIN	= 5; //byte[]
	public static final int TYPE_BASE64 = 6; //base64
	public static final int TYPE_QRCODE = 7; //qrcode
	public static final int TYPE_OTHER	= 99;

	boolean sign = false;

	Map imageMap = new HashMap();
	
	public DocumentImage()
	{
	}

	public void addImageSource(Object image, int type)
	{
		imageMap.put(new Integer(type), image);
	}

	public Object getImage(int type)
	{
		return imageMap.get(new Integer(type));
	}

	public boolean hasImage()
	{
		return !imageMap.isEmpty();
	}
	
	public boolean hasImage(int type)
	{
		return imageMap.containsKey(new Integer(type));
	}

	public boolean isSign()
	{
		return sign;
	}

	public void setSign(boolean sign)
	{
		this.sign = sign;
	}

	public LexElement copy()
	{
		DocumentImage e = new DocumentImage();
		e.imageMap = imageMap;
		e.sign = sign;
		
		e.setAll(this);
		
		return e;
	}
}
