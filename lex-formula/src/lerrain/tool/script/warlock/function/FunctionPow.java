package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;

public class FunctionPow implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 2)
		{
			double v1 = Value.valueOf(v[0]).doubleValue();
			double v2 = Value.valueOf(v[1]).doubleValue();
			
			return Double.valueOf(Math.pow(v1, v2));
		}
		
		throw new RuntimeException("错误的pow运算");
	}
}
