package lerrain.project.insurance.plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lerrain.project.insurance.plan.CommodityFactors.RiderFunction;
import lerrain.project.insurance.plan.function.FunctionMgr;
import lerrain.project.insurance.product.Company;
import lerrain.project.insurance.product.Variable;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Function;

public class PlanFactors implements FactorsSupport, Serializable
{
	private static final long serialVersionUID = 1L;
	
	Plan plan;
	Company assurer;
	
	Map vars = null;

	CustomerFactors applicantFactors, insurantFactors;
	
//	List otherInsurantsFactors;
	
	public PlanFactors(Plan plan)
	{
		this.plan = plan;
		
		applicantFactors = new CustomerFactors(plan, CustomerFactors.APPLICANT);
		insurantFactors = new CustomerFactors(plan, CustomerFactors.INSURANT);
		
//		List otherInsurants = plan.getOtherInsurants();
//		if (otherInsurants != null)
//		{
//			otherInsurantsFactors = new ArrayList();
//			for (int i = 0; i < otherInsurants.size(); i++)
//			{
//				otherInsurantsFactors.add(new CustomerFactors((InsuranceCustomer)otherInsurants.get(i), plan));
//			}
//		}
	}
	
	public Object get(String name)
	{
		if ("THIS".equals(name))
			return this;
		if ("this".equals(name))
			return this.getPlan();
		
		if ("INSURANT".equals(name))
			return insurantFactors;
		if ("APPLICANT".equals(name))
			return applicantFactors;
//		if ("OTHERINS".equals(name))
//			return otherInsurantsFactors;
		if ("PRIMARY".equals(name))
			return this.getPlan().isEmpty() ? null : this.getPlan().getCommodity(0).getFactors();
		
		if ("INSURANTS".equals(name))
		{
			List insurants = new ArrayList();
			for (int i=0;i<plan.size();i++)
			{
				CustomerFactors cf = (CustomerFactors)plan.getCommodity(i).getFactor("INSURANT");
				
				boolean same = false;
				for (int j=0;j<insurants.size();j++)
				{
					CustomerFactors cf2 = (CustomerFactors)insurants.get(j);
					if (((Object)cf2.getCustomer()).equals(cf.getCustomer()))
					{
						same = true;
						break;
					}
				}
				
				if (!same)
					insurants.add(cf);
			}
			return insurants;
		}
		
		if ("PRODUCTS".equals(name))
		{
			List products = new ArrayList();
			for (int i=0;i<plan.size();i++)
				products.add(plan.getCommodity(i).getFactors());
			return products;
		}
		
//		Commodity product = this.getPlan().getCommodityByProductId(name);
//		if (product != null)
//			return product.getFactors();
		
		/*
		 * 固定值或输入值，包括保额、保费、份数
		 */
		Object r = this.getPlan().getValue(name);
		if (r != null)
			return r;
		
		r = this.getPlan().getAdditional(name);
		if (r != null)
		{
			if (r instanceof Formula)
				return ((Formula)r).run(this);
			else
				return r;
		}
		
		if (assurer == null && !plan.isEmpty())
		{
			assurer = plan.primaryCommodity().getCompany();
			
			if (vars == null)
				vars = new HashMap();
			
//			Iterator iter = assurer.getPlanVars().toList().iterator();
//			while (iter.hasNext())
//			{
//				Variable v = (Variable)iter.next();
//				vars.put(v.getName(), v.getFormula());
//			}
			vars.putAll(assurer.getPlanVars().getAllVars());
		}
		
		if (vars != null)
		{
			Formula formula = (Formula)vars.get(name);
			if (formula != null)
				return formula.run(this);
		}
		
		//计算器只需直接返回计算器对象，由公式引擎调用它计算，不需要自己计算。
		r = FunctionMgr.getPlanCalculater(plan, name);
		if (r != null)
			return r;

		r = FunctionMgr.getCalculater(name);
		if (r != null)
			return r;
		
		if ("MAIN".equals(name))
			return new MainFunction(plan); 
		
		return null;
	}

	public Plan getPlan()
	{
		return plan;
	}

	/**
	 * @deprecated
	 */
	public void clearBuffer()
	{
		clearCache();
	}
	
	public void clearCache()
	{
		applicantFactors.clearCache();
		insurantFactors.clearCache();
		
//		if (otherInsurantsFactors != null)
//		{
//			for (int i = 0; i < otherInsurantsFactors.size(); i++)
//			{
//				((FactorsSupport)otherInsurantsFactors.get(i)).clearCache();
//			}
//		}
	}
	
	public static class MainFunction implements Function
	{
		Plan plan;
		
		public MainFunction(Plan plan)
		{
			this.plan = plan;
		}
		
		public Object run(Object[] v, Factors p)
		{
			if (v == null || v.length == 0)
				return plan.primaryCommodity().getFactors();

			Commodity main = plan.getCommodityByProductId((String)v[0]);
			return main != null ? main.getFactors() : null;
		}
	};
}
