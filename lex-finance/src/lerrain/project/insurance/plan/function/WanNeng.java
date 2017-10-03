package lerrain.project.insurance.plan.function;

import java.io.Serializable;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Input;

/**
 * 期交保费 (期初加入)
 * 追加保费 (期初加入)
 * 保单管理费 (期初扣除)
 * 各类保费 (期末扣除)
 * 
 * 收益率 [R1, R2, R3, ..., Rn]
 * 
 * @author lerrain
 *
 */
public class WanNeng implements FunctionCommodity, Serializable
{
	private static final long serialVersionUID = 1L;
	
	int age;
	
	public String getName()
	{
		return "WanNeng";
	}
	
	public Object runCommodity(Commodity c, Object[] param) 
	{
		//初始账户价值 = 0
		//每年追加入账户的价值
		double[] append = (double[])param[0];
		//年收益率
		double[] benefitRate = (double[])param[1];
		//获取主险责任的保额，主险的保额实际上包含了账户价值，计算保费要扣除账户价值再乘以费率
		double[] amount = (double[])param[2];
		double[] rate = (double[])param[3];
		//确定各种附加保险责任的保额
		Object[] cAmount = (Object[])param[4];
		//查找各种附加保险责任的费率
		Object[] cRate = (Object[])param[5];

		int age = ((Integer)c.getFactors().get("AGE")).intValue();
		int toAge = Input.periodOf(c.getInsure());
		
		double[][] value = new double[toAge][benefitRate.length];

		for (int i = 0; i < toAge; i++)
		{
			double premium = i < rate.length ? amount[i] * rate[i] : 0;
			
			for (int j = 0; j < cAmount.length; j++)
			{
				double[] coverageAmount = (double[])cAmount[j];
				double[][] coverageRate = (double[][])cRate[j];
				
				if (coverageAmount != null && i < coverageAmount.length)
				{
					premium += coverageAmount[j] * coverageRate[j + age][0];
				}
			}
			
			for (int j = 0; j < value[0].length; j++)
			{
				value[i][j] = (i == 0 ? 0 : value[i - 1][j]) + append[j] - premium;
				value[i][j] = value[i][j] * benefitRate[j];
			}
		}
		
		return value;
	}
	
//	private double[] getRate(Commodity c, String groupName, int age1, int age2)
//	{
//		double[] r = new double[age2 - age1 + 1];
//		
//		for (age = age1; age <= age2; age++)
//		{
//			Map map = (Map)c.getProduct().getDataHub().run(this);
//			r[age - age1] = ((double[][])map.get(groupName))[0][0];
//		}
//		return r;
//	}
//	
//	public Object get(String name)
//	{
//		if ("AGE".equals(name))
//			return new Integer(age);
//		
//		return f.get(name);
//	}
}