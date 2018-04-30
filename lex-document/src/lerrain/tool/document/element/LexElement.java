package lerrain.tool.document.element;

import java.io.Serializable;

import lerrain.tool.document.LexColor;
import lerrain.tool.formula.Formula;



public abstract class LexElement implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static int ALIGN_CENTER 			= 0x1;
	public static int ALIGN_LEFT 			= 0x2;
	public static int ALIGN_RIGHT			= 0x3;
	public static int ALIGN_MIDDLE			= 0x10;
	public static int ALIGN_TOP				= 0x20;
	public static int ALIGN_BOTTOM			= 0x30;

	int x;
	int y;
	int width;
	int height;

	LexColor color = LexColor.BLACK;
	LexColor bgColor = null;
	LexColor borderColor = LexColor.BLACK;
	
	int topBorder = -1;
	int bottomBorder = -1;
	int leftBorder = -1;
	int rightBorder = -1;

	boolean split	= true;
	boolean extend	= true;
	boolean absFloat = false;

	int horizontalAlign = LexElement.ALIGN_CENTER;
	int verticalAlign = LexElement.ALIGN_MIDDLE;

	String link;
	
	Formula resetAtFinal;

	public boolean isAbsFloat()
	{
		return absFloat;
	}

	public void setAbsFloat(boolean absFloat)
	{
		this.absFloat = absFloat;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public int getX()
	{
		return x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}

	public void setSize(int width, int height)
	{
		setWidth(width);
		setHeight(height);
	}
	
	public void setLocation(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	public void setBorder(int[] border)
	{
		setLeftBorder(border[0]);
		setTopBorder(border[1]);
		setRightBorder(border[2]);
		setBottomBorder(border[3]);
	}
	
	public void setBorder(int left, int top, int right, int bottom)
	{
		setLeftBorder(left);
		setRightBorder(right);
		setTopBorder(top);
		setBottomBorder(bottom);
	}

	/**
	 * 跨页时是否自动分割
	 */
	public boolean canSplit()
	{
		return split;
	}

	public void setSplit(boolean split)
	{
		this.split = split;
	}

	/**
	 * 内容超出时是否自动向下伸展
	 */
	public boolean canExtend()
	{
		return extend;
	}

	public void setExtend(boolean extend)
	{
		this.extend = extend;
	}
	
	public void build(int x, int y)
	{
		setLocation(x, y);
	}
	
	public LexColor getColor()
	{
		return color;
	}

	public void setColor(LexColor color)
	{
		this.color = color;
	}

	public LexColor getBgColor()
	{
		return bgColor;
	}

	public void setBgColor(LexColor bgColor)
	{
		this.bgColor = bgColor;
	}

	public LexColor getBorderColor()
	{
		return borderColor;
	}

	public void setBorderColor(LexColor borderColor)
	{
		this.borderColor = borderColor;
	}

	public int getBottomBorder()
	{
		return bottomBorder;
	}

	public void setBottomBorder(int bottomBorder)
	{
		this.bottomBorder = bottomBorder;
	}

	public int getLeftBorder()
	{
		return leftBorder;
	}

	public void setLeftBorder(int leftBorder)
	{
		this.leftBorder = leftBorder;
	}

	public int getRightBorder()
	{
		return rightBorder;
	}

	public void setRightBorder(int rightBorder)
	{
		this.rightBorder = rightBorder;
	}

	public int getTopBorder()
	{
		return topBorder;
	}

	public void setTopBorder(int topBorder)
	{
		this.topBorder = topBorder;
	}

	public void setAll(LexElement e)
	{
		setX(e.getX());
		setY(e.getY());
		setWidth(e.getWidth());
		setHeight(e.getHeight());

		setColor(e.getColor());
		setBgColor(e.getBgColor());
		
		setBorderColor(e.getBorderColor());
		setBottomBorder(e.getBottomBorder());
		setLeftBorder(e.getLeftBorder());
		setRightBorder(e.getRightBorder());
		setTopBorder(e.getTopBorder());
		
		setHorizontalAlign(e.getHorizontalAlign());
		setVerticalAlign(e.getVerticalAlign());
		
		setSplit(e.canSplit());
		setExtend(e.canExtend());
	}

	public int getHorizontalAlign()
	{
		return horizontalAlign;
	}

	public void setHorizontalAlign(int horizontalAlign)
	{
		this.horizontalAlign = horizontalAlign;
	}

	public int getVerticalAlign()
	{
		return verticalAlign;
	}

	public void setVerticalAlign(int verticalAlign)
	{
		this.verticalAlign = verticalAlign;
	}
	
	public abstract LexElement copy();

	public Formula getResetAtFinal()
	{
		return resetAtFinal;
	}

	public void setResetAtFinal(Formula resetAtFinal)
	{
		this.resetAtFinal = resetAtFinal;
	}
}