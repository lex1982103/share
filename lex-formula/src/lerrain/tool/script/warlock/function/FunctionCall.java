package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

/**
 * call(函数对象, 参数数组, 环境factors)
 * call(函数对象, 参数, 环境factors)
 * 
 * 多个参数请写成数组的形式[param1, param2, ... , paramN]
 * 
 * call(函数对象, 参数数组)
 * call(函数对象, 参数)
 * 没有传递环境factors时，使用当前的环境factors，但是这种情况毫无意义（直接函数型写法就可以了）
 * RateArray(age, 80) 等价于 call(RateArray, [age, 80]);
 * 
 * @author lerrain
 *
 */
public class FunctionCall implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length != 2 && v.length != 3)
			throw new RuntimeException("call函数限定2~3个参数");
		if (!(v[0] instanceof Function))
			throw new RuntimeException("call函数的第1个参数必须为参数表函数指针");
		if (v.length == 3 && !(v[2] instanceof Factors))
			throw new RuntimeException("call函数的第3个参数必须为参数表Factors");
		
		Object[] param;
		
		if (v[1] instanceof Object[])
			param = (Object[])v[1];
		else
			param = new Object[] { v[1] };
		
		Function f = (Function)v[0];
		return f.run(param, v.length == 3 ? (Factors)v[2] : factors);
	}
}
