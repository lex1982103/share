package lerrain.tool.formula.aries.arithmetic;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;

public class Variable implements Formula
{
	String variableName;
	
	public Variable(String variableName)
	{
		this.variableName = variableName;
	}

	public Object run(Factors getter)
	{
		if ("null".equals(variableName))
			return null;
		else if ("true".equals(variableName))
			return new Boolean(true);
		else if ("false".equals(variableName))
			return new Boolean(false);
		
		Object obj = getter.get(variableName);
		
		/*
		 * 这里是一般还是考虑直接返回值，直接计算在一些极端情况下会出现问题，比如：
		 * FF是Formula对象，那么FF.toString()，本来是要返回他的字符串表达，而这种情况下会直接以当前参数表计算FF的值，然后在toString，
		 * 不仅结果可能不是想要的，还有可能出现计算死循环。
		 */
//		if (obj instanceof Function)
//			return ((Function)obj).run(getter);
//		else
//			return obj;
		
		return obj;
	}
	
	public String getVariableName()
	{
		return variableName;
	}

	public String toText()
	{
		return variableName;
	}
}
