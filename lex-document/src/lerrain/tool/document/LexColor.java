package lerrain.tool.document;

import java.io.Serializable;

public class LexColor implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final LexColor BLACK = new LexColor(0, 0, 0);
	public static final LexColor WHITE = new LexColor(255, 255, 255);
	public static final LexColor BLUE = new LexColor(0, 0, 255);
	public static final LexColor RED = new LexColor(255, 0, 0);
	public static final LexColor CYAN = new LexColor(0, 255, 255);
	public static final LexColor GRAY = new LexColor(196, 196, 196);
	public static final LexColor DARK_GREEN = new LexColor(0, 128, 0);
	public static final LexColor DARK_CYAN = new LexColor(0, 128, 128);
	
	int r, g, b;
	
	public LexColor(int r, int g, int b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public boolean equals(LexColor c)
	{
		if (c == null)
			return false;
		
		return r == c.r && g == c.g && b == c.b;
	}

	public int getRed()
	{
		return r;
	}

	public void setRed(int r)
	{
		this.r = r;
	}

	public int getGreen()
	{
		return g;
	}

	public void setGreen(int g)
	{
		this.g = g;
	}

	public int getBlue()
	{
		return b;
	}

	public void setBlue(int b)
	{
		this.b = b;
	}
	
	public String toString()
	{
		return "<" + r + "," + g + "," + b + ">";
	}
}
