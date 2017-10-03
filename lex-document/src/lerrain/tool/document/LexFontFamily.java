package lerrain.tool.document;

import java.io.Serializable;

public class LexFontFamily implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String name;
	
	String path;

//	public LexFontFamily(Function path)
//	{
//		this(null, path);
//	}
	
	public LexFontFamily(String name, String path)
	{
		this.name = name;
		this.path = path;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public void setPath(String path)
	{
		this.path = path;
	}
	
//	public Object getAdditional(String name)
//	{
//		return additional.get(name);
//	}
//	
//	public void setAdditional(String name, Object value)
//	{
//		if (additional == null)
//			additional = new HashMap();
//		
//		additional.put(name, value);
//	}
	
	public LexFont derive(float size)
	{
		return new LexFont(this, size);
	}
}
