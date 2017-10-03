package lerrain.tool.document.size;

import java.io.Serializable;



public class PaperA4Rotate implements Paper, Serializable
{
	private static final long serialVersionUID = 1L;

	public PaperA4Rotate()
	{
	}

	public int getHeight()
	{
		return 2100;
	}

	public String getName()
	{
		return "A4_rotate";
	}

	public int getWidth()
	{
		return 2970;
	}
}