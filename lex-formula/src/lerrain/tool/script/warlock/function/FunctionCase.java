package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.ScriptRuntimeException;

public class FunctionCase implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		int num = v.length;
		if (num == 3) //case(xxx>0, 1, -1)
		{
			if (Value.valueOf(v[0]).booleanValue())
				return v[1];
			else
				return v[2];
		}
		else if (num >= 4)//case(x, 1, 100, 2, 200, 300)
		{
			Object k = v[0];
			for (int j = 2; j <= num; j = j + 2)
			{
				Object key = v[j - 1];
				if (k == key || (k != null && k.equals(key)))
				{
					return v[j];
				}
			}
			
			if (num % 2 == 0) //偶数个参数，最后一个参数为都不符合的情况下的值。
				return v[num - 1];
		}
		
		throw new ScriptRuntimeException("错误的case运算");
	}
	
//	public Object run(Object[] v, Factors factors)
//	{
//		int num = Value.valueOf(factors.get("#0")).intValue();
//		if (num == 3) //case(xxx>0, 1, -1)
//		{
//			if (Value.valueOf(factors.get("#1")).booleanValue())
//				return factors.get("#2");
//			else
//				return factors.get("#3");
//		}
//		else if (num >= 4)//case(x, 1, 100, 2, 200, 300)
//		{
//			Object k = factors.get("#1");
//			for (int j = 2; j <= num; j = j + 2)
//			{
//				Object key = factors.get("#" + j);
//				if (k == key || (k != null && k.equals(key)))
//				{
//					return factors.get("#" + (j + 1));
//				}
//			}
//			
//			if (num % 2 == 0) //偶数个参数，最后一个参数为都不符合的情况下的值。
//				return factors.get("#" + num);
//		}
//		
//		throw new RuntimeException("错误的case运算");
//	}
}
