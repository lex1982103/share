package lerrain.project.insurance.plan.function;

import java.io.Serializable;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Plan;

/**
 * 职业判定
 * 第一个参数是职业代码表名
 * 第二个参数是职业代码集合
 * @author lerrain
 *
 */
public class OccMatch implements FunctionCommodity, FunctionPlan, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "OccMatch";
	}
	
	private boolean match(String occupationCode, Object[] occupList)
	{
		if (occupationCode == null)
			return false;
		
//		Object[] occupList = null;
//		
//		Object v = param[0];
//		if (v instanceof Object[])
//		{
//			occupList = (Object[])v;
//		}
//		else
//		{
//			occupList = new Object[] { v };
//		}
		
		int pos = occupationCode.indexOf("/");
		if (pos >= 0)
			occupationCode = occupationCode.substring(pos + 1);
		
		for (int i = 0; i < occupList.length; i++)
		{
			String occupation = (String)occupList[i];
			
			if (occupationCode.equals(occupation))
				return true;
			
			if (occupation.indexOf("*") >= 0 || occupation.indexOf("?") >= 0)
			{
				occupation = occupation.replaceAll("[*]", ".*");
				occupation = occupation.replaceAll("[?]", ".?");
				occupation = "^" + occupation + "$";

				if (occupationCode.matches(occupation))
					return true;
			}
		}
		
		return false;
	}

	public Object runCommodity(Commodity c, Object[] param) 
	{
		return new Boolean(match((String)c.getFactors().get("OCCUPATION_CODE"), param));
	}

	public Object runPlan(Plan p, Object[] param)
	{
		return new Boolean(match((String)p.getFactors().get("OCCUPATION_CODE"), param));
	}
}