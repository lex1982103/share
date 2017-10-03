package lerrain.project.insurance.plan.function;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lerrain.tool.formula.Value;

/**
 * 数组求和
 * @author lerrain
 *
 */
public class SumArray implements FunctionUsual, Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String getName()
	{
		return "SumArray";
	}

	public Object runUsual(Object[] v)
	{
		int len = 0;
		if (v[0] instanceof Integer)
			len = ((Integer)v[0]).intValue();
		else if (v[0] instanceof Double)
			len = ((Double)v[0]).intValue();
		else if (v[0] instanceof BigDecimal)
			len = ((BigDecimal)v[0]).intValue();
		
//		if (len <= 0)
//		{
//			for (int i = 0; i < v.length; i++)
//			{
//				int thislen = 0;
//				if (v[i] instanceof double[])
//					thislen = (double[])v[i];
//				
//				if (len < val.length)
//					len = val.length;
//			}
//		}
		
		double[] r = new double[len];
		for (int i = 0; i < v.length; i++)
		{
			if (v[i] instanceof double[])
			{
				double[] val = (double[])v[i];
				for (int j = 0; j < val.length; j++)
				{
					r[j] = r[j] + val[j];
				}
			}
			else if (v[i] instanceof int[])
			{
				int[] val = (int[])v[i];
				for (int j = 0; j < val.length; j++)
				{
					r[j] = r[j] + val[j];
				}
			}
			else if (v[i] instanceof List)
			{
				List val = (List)v[i];
				for (int j = 0; j < val.size(); j++)
				{
					r[j] = r[j] + Value.doubleOf(val.get(j));
				}
			}
			else if (v[i] instanceof Object[])
			{
				Object[] val = (Object[])v[i];
				for (int j = 0; j < val.length; j++)
				{
					r[j] = r[j] + Value.doubleOf(val[j]);
				}
			}
		}
		return r;
	}
}