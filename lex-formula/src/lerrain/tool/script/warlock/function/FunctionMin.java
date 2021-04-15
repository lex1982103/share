package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;

public class FunctionMin implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length > 0)
		{
			double min = Value.valueOf(v[0]).doubleValue();
			
			for (int i = 1; i < v.length; i++)
			{
				double d = Value.valueOf(v[i]).doubleValue();
				if (min > d)
					min = d;
			}
			
			return Double.valueOf(min);
		}
		
		throw new RuntimeException("错误的min运算");
	}
}
