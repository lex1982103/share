package lerrain.project.insurance.plan.function;

import java.io.Serializable;
import java.util.Map;

import lerrain.project.insurance.plan.Commodity;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

/**
 * 获取一段时间内，每年的费率
 * 
 * 一个短期险的费率，随着每年的年龄增长，每年都是不同的。
 * 当一个长期险需要计算一个短期附加险在连续续保的情况下的利益情况，就需要用这个函数查询一个长时间跨度内每年的费率。
 * @author lerrain
 *
 */
public class RateArray implements FunctionCommodity, Serializable, Factors
{
	private static final long serialVersionUID = 1L;
	
	Factors f;
	
	int age;

	public String getName()
	{
		return "RateArray";
	}

	public Object runCommodity(Commodity c, Object[] param) 
	{
		int length = 0;
		
		int age1 = Value.valueOf(param[0]).intValue();
		int age2 = Value.valueOf(param[1]).intValue();
		
		String groupName = "RATE";
		if (param.length > 2)
			groupName = (String)param[2];
		if (param.length > 3)
			length = Value.valueOf(param[3]).intValue();
		
		f = c.getFactors();
		
		if (length == 0)
		{
			double[] r = new double[age2 - age1 + 1];
			
			for (age = age1; age <= age2; age++)
			{
				Map map = (Map)c.getProduct().getDataHub().run(this);
				r[age - age1] = ((double[][])map.get(groupName))[0][0];
			}	
			return r;
		}
		else
		{
			double[][] r = new double[age2 - age1 + 1][length];
			
			for (age = age1; age <= age2; age++)
			{
				Map map = (Map)c.getProduct().getDataHub().run(this);
				
				for (int i = 0; i < length; i++)
					r[age - age1][i] = ((double[][])map.get(groupName))[0][i];
			}
			return r;
		}
	}

	public Object get(String name)
	{
		if ("AGE".equals(name))
			return new Integer(age);
		
		return f.get(name);
	}
}