package lerrain.tool.document.typeset.element;

import java.util.HashMap;
import java.util.Map;

import lerrain.tool.document.LexColor;
import lerrain.tool.document.LexFont;
import lerrain.tool.document.element.DocumentText;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetNumber;
import lerrain.tool.document.typeset.TypesetParameters;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;

public abstract class TypesetElement
{
	public static final int MODE_COMMON			= 0;
	public static final int MODE_RESET_AT_FINAL	= 1;

	TypesetNumber x;
	TypesetNumber y;
	TypesetNumber width;
	TypesetNumber height;

	boolean absFloat;
	
	LexFont font;
	int lineHeight;

	int mode = TypesetElement.MODE_COMMON;
	
	LexColor color;
	LexColor bgColor;
	LexColor borderColor;

	Formula link;
	Formula value;
	
	TypesetNumber topBorder;
	TypesetNumber bottomBorder;
	TypesetNumber leftBorder;
	TypesetNumber rightBorder;
	
	int[] margin						= null;
	
	public static final int ALIGN_LEFT			= DocumentText.ALIGN_LEFT;
	public static final int ALIGN_CENTER		= DocumentText.ALIGN_CENTER;
	public static final int ALIGN_RIGHT			= DocumentText.ALIGN_RIGHT;
	
	public static final int ALIGN_TOP			= DocumentText.ALIGN_TOP;
	public static final int ALIGN_MIDDLE		= DocumentText.ALIGN_MIDDLE;
	public static final int ALIGN_BOTTOM		= DocumentText.ALIGN_BOTTOM;

	int align							= ALIGN_LEFT;
	int valign							= ALIGN_MIDDLE;
	
	Map style;
//	String style;
	String underline;

	Formula condition;
	
	//内容过长自动扩展
	boolean fixed = false;
	
	boolean split = true;

	public Formula getLink()
	{
		return link;
	}

	public void setLink(Formula link)
	{
		this.link = link;
	}

	public boolean isFixed()
	{
		return fixed;
	}

	public void setFixed(boolean fixed)
	{
		this.fixed = fixed;
	}
	
	public void setX(int x)
	{
		this.x = new TypesetNumber(x);
	}
	
	public void setY(int y)
	{
		this.y = new TypesetNumber(y);
	}
	
	public void setWidth(int width)
	{
		this.width = new TypesetNumber(width);
	}
	
	public void setHeight(int height)
	{
		this.height = new TypesetNumber(height);
	}

	public void setWidth(TypesetNumber width)
	{
		this.width = width;
	}

	public void setX(String x)
	{
		this.x = TypesetNumber.numberOf(x);
	}
	
	public void setY(String y)
	{
		this.y = TypesetNumber.numberOf(y);
	}
	
	public void setWidth(String width)
	{
		this.width = TypesetNumber.numberOf(width);
	}
	
	public void setHeight(String height)
	{
		this.height = TypesetNumber.numberOf(height);
	}
	
	public void setX(Formula x)
	{
		this.x = new TypesetNumber(x);
	}
	
	public void setY(Formula y)
	{
		this.y = new TypesetNumber(y);
	}
	
	public void setWidth(Formula width)
	{
		this.width = new TypesetNumber(width);
	}
	
	public void setHeight(Formula height)
	{
		this.height = new TypesetNumber(height);
	}

	public int getMode()
	{
		return mode;
	}

	public void setMode(int mode)
	{
		this.mode = mode;
	}

	public boolean isAbsFloat()
	{
		return absFloat;
	}

	public void setAbsFloat(boolean absFloat)
	{
		this.absFloat = absFloat;
	}

	public void setAlign(String str)
	{
		if ("left".equalsIgnoreCase(str))
			this.setAlign(TypesetElement.ALIGN_LEFT);
		else if ("center".equalsIgnoreCase(str))
			this.setAlign(TypesetElement.ALIGN_CENTER);
		else if ("right".equalsIgnoreCase(str))
			this.setAlign(TypesetElement.ALIGN_RIGHT);
	}
	
	public void setVerticalAlign(String str)
	{
		if ("top".equalsIgnoreCase(str))
			this.setVerticalAlign(TypesetElement.ALIGN_TOP);
		else if ("middle".equalsIgnoreCase(str))
			this.setVerticalAlign(TypesetElement.ALIGN_MIDDLE);
		else if ("bottom".equalsIgnoreCase(str))
			this.setVerticalAlign(TypesetElement.ALIGN_BOTTOM);
	}

	public int getAlign()
	{
		return align;
	}

	public void setAlign(int align)
	{
		this.align = align;
	}

	public LexColor getColor()
	{
		return color;
	}

	public void setColor(LexColor color)
	{
		this.color = color;
	}

	public LexFont getFont()
	{
		return font;
	}

	public void setFont(LexFont font)
	{
		this.font = font;
	}

	public TypesetNumber getHeight()
	{
		return height;
	}
	
	public TypesetNumber getX()
	{
		return x;
	}

	public TypesetNumber getY()
	{
		return y;
	}

	public LexColor getBgColor()
	{
		return bgColor;
	}

	public void setBgColor(LexColor bgColor)
	{
		this.bgColor = bgColor;
	}

	public int getVerticalAlign()
	{
		return valign;
	}

	public void setVerticalAlign(int valign)
	{
		this.valign = valign;
	}

	public LexColor getBorderColor()
	{
		return borderColor;
	}

	public void setBorderColor(LexColor borderColor)
	{
		this.borderColor = borderColor;
	}

	public TypesetNumber getBottomBorder()
	{
		return bottomBorder;
	}

	public void setBottomBorder(int bottomBorder)
	{
		this.bottomBorder = new TypesetNumber(bottomBorder);
	}

	public void setBottomBorder(String bottomBorder)
	{
		this.bottomBorder = TypesetNumber.numberOf(bottomBorder);
	}

	public TypesetNumber getLeftBorder()
	{
		return leftBorder;
	}

	public void setLeftBorder(int leftBorder)
	{
		this.leftBorder = new TypesetNumber(leftBorder);
	}

	public void setLeftBorder(String leftBorder)
	{
		this.leftBorder = TypesetNumber.numberOf(leftBorder);
	}

	public TypesetNumber getRightBorder()
	{
		return rightBorder;
	}

	public void setRightBorder(int rightBorder)
	{
		this.rightBorder = new TypesetNumber(rightBorder);
	}
	
	public void setRightBorder(String rightBorder)
	{
		this.rightBorder = TypesetNumber.numberOf(rightBorder);
	}

	public TypesetNumber getTopBorder()
	{
		return topBorder;
	}

	public void setTopBorder(int topBorder)
	{
		this.topBorder = new TypesetNumber(topBorder);
	}

	public void setTopBorder(String topBorder)
	{
		this.topBorder = TypesetNumber.numberOf(topBorder);
	}

	public TypesetNumber getWidth()
	{
		return width;
	}

	public abstract LexElement build(TypesetParameters varSet);
	
	public boolean isShow(TypesetParameters vs)
	{
		if (condition == null)
			return true;
		
		try
		{
			Object v = condition.run(vs);
			
			if (v != null)
				return Value.booleanOf(v);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void resetY(TypesetParameters tvs, LexElement element)
	{
		if (element.getY() + element.getHeight() > tvs.getY())
		{
			tvs.setY(element.getY() + element.getHeight());
		}
	}

	public Formula getValue()
	{
		return value;
	}

	public void setValue(Formula value)
	{
		this.value = value;
	}
	
	public int getLineHeight()
	{
		return lineHeight;
	}

	public void setLineHeight(int lineHeight)
	{
		this.lineHeight = lineHeight;
	}
	
	public TypesetParameters pack(TypesetParameters tvs)
	{
		TypesetParameters tvs2 = new TypesetParameters(tvs);
		tvs2.setPaper(tvs.getPaper());
		
		return tvs2;
	}

	public void setStyle(String styleStr)
	{
		if (styleStr != null && !"".equals(styleStr.trim()))
		{
			style = new HashMap();
			
			String[] css = styleStr.split(";");
			for (int i = 0; i < css.length; i++)
			{
				String[] ss = css[i].split(":");
				style.put(ss[0].trim(), ss[1].trim());
			}
		}
	}
	
	public Map getStyle()
	{
		return style;
	}
	
	public int valueOf(TypesetNumber num, TypesetParameters tvs)
	{
		if (num == null)
			return -1;
		
		return num.value(tvs);
	}
	
//	public Map styleOf(TypesetParameters tvs)
//	{
//		Map m = null;
//		
//		if (style != null)
//		{
//			Object s = style.run(tvs);
//			if (s instanceof Map)
//			{
//				m = (Map)s;
//			}
//			else if (s instanceof String)
//			{
//				String styleStr = (String)s;
//				if (styleStr != null && !"".equals(styleStr.trim()))
//				{
//					m = new HashMap();
//					
//					String[] css = styleStr.split(";");
//					for (int i = 0; i < css.length; i++)
//					{
//						String[] ss = css[i].split(":");
//						m.put(ss[0].trim(), ss[1].trim());
//					}
//				}
//			}
//		}
//		
//		return m;
//	}
	
	public static LexColor getColor(String color)
	{
		if (color == null || "".equals(color.trim()))
			return LexColor.WHITE;
		
		else if (color.startsWith("#"))
		{
			int r = tranHex(color.substring(1, 3));
			int g = tranHex(color.substring(3, 5));
			int b = tranHex(color.substring(5, 7));
			return new LexColor(r, g, b);
		}
		else if ("red".equalsIgnoreCase(color))
			return LexColor.RED;
		else if ("black".equalsIgnoreCase(color))
			return LexColor.BLACK;
		else if ("blue".equalsIgnoreCase(color))
			return LexColor.BLUE;
		else if ("cyan".equalsIgnoreCase(color))
			return LexColor.CYAN;
		else if ("white".equalsIgnoreCase(color))
			return LexColor.WHITE;
		else if ("darkgreen".equalsIgnoreCase(color))
			return LexColor.DARK_GREEN;
		else if ("darkcyan".equalsIgnoreCase(color))
			return LexColor.DARK_CYAN;
		else if ("gray".equalsIgnoreCase(color))
			return LexColor.GRAY;
		
		return LexColor.WHITE;
	}
	
	private static int tranHex(String hex)
	{
		int[] r1 = new int[2];
		for (int i=0;i<2;i++)
		{
			char c1 = hex.charAt(i);
			if (c1 >= '0' && c1 <= '9')
				r1[i] = c1 - '0';
			else if (c1 >= 'A' && c1 <= 'F')
				r1[i] = c1 - 'A' + 10;
			else if (c1 >= 'a' && c1 <= 'f')
				r1[i] = c1 - 'a' + 10;
		}
		
		return r1[0] * 16 + r1[1];
	}

	public Formula getCondition()
	{
		return condition;
	}

	public void setCondition(Formula condition)
	{
		this.condition = condition;
	}

	public String getUnderline()
	{
		return underline;
	}

	public void setUnderline(String underline)
	{
		this.underline = underline;
	}

	public int[] getMargin()
	{
		return margin;
	}

	public void setMargin(int[] margin)
	{
		this.margin = margin;
	}

	public boolean isSplit()
	{
		return split;
	}

	public void setSplit(boolean split)
	{
		this.split = split;
	}
}
