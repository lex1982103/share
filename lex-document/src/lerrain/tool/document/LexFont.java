package lerrain.tool.document;

import java.io.Serializable;

public class LexFont implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	LexFontFamily family;

	public LexFontFamily getFamily()
	{
		return family;
	}

	public void setFamily(LexFontFamily family)
	{
		this.family = family;
	}

	float size;
	
	public LexFont(LexFontFamily family, float size)
	{
		this.family = family;
		this.size = size;
	}
	
	public String getName()
	{
		return family.getName();
	}
	
	public float getSize()
	{
		return size;
	}
	
	public void setSize(float size)
	{
		this.size = size;
	}
	
	public LexFont derive(float size)
	{
		return new LexFont(family, size);
	}
}
