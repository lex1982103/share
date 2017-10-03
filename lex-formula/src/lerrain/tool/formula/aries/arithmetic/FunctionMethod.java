package lerrain.tool.formula.aries.arithmetic;

import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;

public class FunctionMethod implements Formula
{
	String methodName;
	List parameterList;
	
	public FunctionMethod(String methodName, List parameterList)
	{
		this.methodName = methodName;
		this.parameterList = parameterList;
	}

	public Object run(Factors getter)
	{
//		Object object = getter.getValue(objectName);
//		Method method = object.getClass().getDeclaredMethod(functionName, new Class[] {});
//		Object result = method.invoke(object, new Object[] {});
//		return new LexValue(result);
		
		List result = new ArrayList();
		result.add(methodName);
		
		int s = parameterList == null ? 0 : parameterList.size();
		for (int i=0;i<s;i++)
		{
			Formula p = (Formula)parameterList.get(i);
			result.add(p.run(getter));
		}
		
		return result;
	}
}
