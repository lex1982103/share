package lerrain.tool.document.typeset;

/**
 * 扩展参数表，附带坐标信息
 */

import java.io.Serializable;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Stack;

public class TypesetParameters extends Stack implements Factors, Serializable
{
	private static final long serialVersionUID = 1L;
	
	TypesetPaper paper;

	Typeset typeset;
	
	int y;					//当前最靠下的元素的坐标（相对于基准坐标）
	int datum;				//当前的基准坐标，reset时，当前的y坐标会被重置为基准坐标，y则会被置0
	int streamY;			//当前画板的绝对Y坐标
	
	int windage;			//由于额外插入的元素造成的偏移量，如跨页时自动带入的表头，他们的数量通常是开始时无法预计的

	int pageTop;			//当前页面正文部分的顶部在流式文档中的坐标
	int pageBottom;			//当前页面正文部分的底部在流式文档中的坐标
	
//	Map valueMap = new HashMap();
	
	/*
	 * 之所以用extends Stack，不直接在这里new是因为：
	 * script在执行的时候，如果传入的不是stack，那么就会在动在外面包裹一层stack，这样TypesetParameters就不是最外层
	 * <script>标签下的脚本赋值都直接赋值在里层了，执行完脚本以后即被释放，无法起到调整整个环境值的作用
	 */
//	Stack stack;
	
	public TypesetParameters(Factors varSet)
	{
		super(varSet);

		if (varSet instanceof TypesetParameters)
		{
			TypesetParameters tp = (TypesetParameters)varSet;
			this.typeset = tp.typeset;
			this.paper = tp.paper;
		}

//		stack = this;
	}
	
	public TypesetParameters(Map map)
	{
		super();
		super.setAll(map);
		
//		stack = this;
	}
	
//	public void levelIn()
//	{
//		stack = new Stack(stack);
//	}
//	
//	public void levelOut()
//	{
//		stack = (Stack)stack.getParent();
//	}
	
//	public void addFont(String fontName, String fontPath)
//	{
//		Map fontMap = (Map)get("FONT_PATH");
//		if (fontMap == null)
//		{
//			fontMap = new HashMap();
//			valueMap.put("FONT_PATH", fontMap);
//		}
//		
//		fontMap.put(fontName, fontPath);
//	}
	
//	public Object get(String name)
//	{
//		Object obj = valueMap.get(name);
//		if (obj != null)
//			return obj;
//		
//		if (varSet != null)
//			return varSet.get(name);
//		
//		return null;
//	}
	
//	public void set(String name, int value)
//	{
//		valueMap.put(name, new Integer(value));
//	}
//	
//	public void set(String name, String value)
//	{
//		valueMap.put(name, value);
//	}
//	
//	public void remove(String name)
//	{
//		valueMap.remove(name);
//	}
//	
//	public void set(String name, Map value)
//	{
//		valueMap.put(name, value);
//	}
	
	public void setDatum(int datum)
	{
		this.datum = datum;
	}

	public int getDatum()
	{
		return datum;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getPageTop()
	{
		return pageTop;
	}

	public void setPageTop(int pageTop)
	{
		this.pageTop = pageTop;
	}

	public int getPageBottom()
	{
		return pageBottom;
	}

	public void setPageBottom(int pageBottom)
	{
		this.pageBottom = pageBottom;
	}

	public TypesetPaper getPaper()
	{
		return paper;
	}

	public void setPaper(TypesetPaper paper)
	{
		this.paper = paper;
	}
	
	public int getStreamY()
	{
		return streamY;
	}

	public void setStreamY(int streamY)
	{
		this.streamY = streamY;
	}

	public int getWindage()
	{
		return windage;
	}

	public void setWindage(int windage)
	{
		this.windage = windage;
	}

	public Typeset getTypeset()
	{
		return typeset;
	}

	public void setTypeset(Typeset typeset)
	{
		this.typeset = typeset;
	}
//	public Object get(String name)
//	{
//		if (stack == this)
//			return super.get(name);
//		else
//			return stack.get(name);
//	}
//	
//	public void set(String name, Object value)
//	{
//		if (stack == this)
//			super.set(name, value);
//		else
//			stack.set(name, value);
//	}
}
