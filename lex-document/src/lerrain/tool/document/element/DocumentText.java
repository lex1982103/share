package lerrain.tool.document.element;

import lerrain.tool.document.LexColor;
import lerrain.tool.document.LexFont;

public class DocumentText extends LexElement
{
	private static final long serialVersionUID = 1L;

	LexFont font;
	
	String text;

	int lineHeight;
	
	String underline;
	
	boolean bold;
	
	public DocumentText(String text, LexColor color, LexColor bgColor, LexFont font, int lineHeight, int width, int height, int horizontalAlign, int verticalAlign, int leftBorder, int topBorder, int rightBorder, int bottomBorder, LexColor borderColor)
	{
		setText(text);
		setColor(color);
		setBgColor(bgColor);
		setFont(font);
		setLineHeight(lineHeight);
		setHorizontalAlign(horizontalAlign);
		setVerticalAlign(verticalAlign);
		setBorder(leftBorder, topBorder, rightBorder, bottomBorder);
		setBorderColor(borderColor);
		setSize(width, height);
	}
	
	public DocumentText(String text, LexColor color, LexColor bgColor, LexFont font, int lineHeight, int width, int height, int horizontalAlign, int verticalAlign)
	{
		this(text, color, bgColor, font, lineHeight, width, height, horizontalAlign, verticalAlign, -1, -1, -1, -1, null);
	}
	
	public DocumentText()
	{
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}

	public LexFont getFont()
	{
		return font;
	}

	public void setFont(LexFont font)
	{
		this.font = font;
	}

	public int getLineHeight()
	{
		return lineHeight;
	}

	public void setLineHeight(int lineHeight)
	{
		this.lineHeight = lineHeight;
	}

	public LexElement copy() 
	{
		DocumentText e = new DocumentText();
		e.setAll(this);
		e.setLineHeight(lineHeight);
		e.setText(text);
		e.setFont(font);
		
		return e;
	}

	public String getUnderline()
	{
		return underline;
	}

	public void setUnderline(String underline)
	{
		this.underline = underline;
	}

	public boolean isBold()
	{
		return bold;
	}

	public void setBold(boolean bold)
	{
		this.bold = bold;
	}
}