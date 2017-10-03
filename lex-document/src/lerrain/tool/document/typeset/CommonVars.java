package lerrain.tool.document.typeset;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.formula.Factors;

/**
 * 使用这个类当做打印的变量表。
 * 也可以自己写一个类作为变量表，实现Parameters即可。
 * <br>
 * 模板中，value属性可以使用加减乘除等各种运算，包括调用类的方法都是可以的。
 * 注意运算公式中：用单引号代替双引号，lt代替左尖括号。
 * <br>
 * @author lex
 *
 */
public class CommonVars extends HashMap implements Factors
{
	private static final long serialVersionUID = 1L;
	
	Map fontPath = new HashMap();

	public CommonVars()
	{
		initValue();
	}
	
	public CommonVars(Map map)
	{
		this.putAll(map);
		
		initValue();
	}
	
	private void initValue()
	{
		this.put("FONT_PATH", fontPath);
	}
	
	public Object get(String name)
	{
		return this.get(name);
	}

	/**
	 * 增加一个字体，在模板中使用。
	 * 使用时name在FONT_PATH下。
	 * 比如 addFontPath("HEI", new File("d:/simhei.ttf"));
	 * 然后在模板中配置 <font name="hei" value="FONT_PATH.HEI"/>
	 * @param name
	 * @param path
	 */
	public void addFont(String name, File ttfFile)
	{
		fontPath.put(name, ttfFile.getAbsoluteFile());
	}
}
