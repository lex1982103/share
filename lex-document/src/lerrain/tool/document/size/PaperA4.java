package lerrain.tool.document.size;

import java.io.Serializable;




public class PaperA4 implements Paper, Serializable
{
	private static final long serialVersionUID = 1L;

	public PaperA4()
	{
	}

	public int getHeight()
	{
		return 2970;
	}

	public String getName()
	{
		return "A4";
	}

	public int getWidth()
	{
		return 2100;
	}
}
