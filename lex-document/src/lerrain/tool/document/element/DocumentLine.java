package lerrain.tool.document.element;

import lerrain.tool.document.LexColor;


public class DocumentLine extends LexElement
{
	private static final long serialVersionUID = 1L;
	
	LexColor color = LexColor.BLACK;
	
	public LexColor getColor()
	{
		return color;
	}

	public void setColor(LexColor color)
	{
		this.color = color;
	}

	public LexElement copy() 
	{
		DocumentLine e = new DocumentLine();
		e.setAll(this);
		e.setColor(color);
		
		return e;
	}
}
