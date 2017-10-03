package lerrain.project.insurance.plan.function;

import java.util.HashMap;
import java.util.Map;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Plan;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Factors;

public class FunctionMgr
{
	private static FunctionMgr mgr;
	
	private Map commodityMap = new HashMap();
	private Map planMap = new HashMap();
	private Map usualMap = new HashMap();
	
	private static synchronized FunctionMgr instance()
	{
		if (mgr == null)
		{
			mgr = new FunctionMgr();
			mgr.addCalculater(new Channel());
			mgr.addCalculater(new RateArray());
			mgr.addCalculater(new RiskAmount());
			mgr.addCalculater(new OccMatch());
			mgr.addCalculater(new HasProduct());
			mgr.addCalculater(new Premium());
			mgr.addCalculater(new PremiumExcept());
			mgr.addCalculater(new PremiumEL());
			mgr.addCalculater(new CountProduct());
			mgr.addCalculater(new CountMain());
			mgr.addCalculater(new ProductOption());
			mgr.addCalculater(new SumArray());
			mgr.addCalculater(new SumAmount());
			mgr.addCalculater(new WanNeng());
		}
		
		return mgr;
	}
	
	public static Function getCommodityCalculater(Commodity c, String name)
	{
		FunctionCommodity fc = (FunctionCommodity)instance().commodityMap.get(name);
		return fc == null ? null : new ConverterCommodity(c, fc);
	}
	
	public static Function getPlanCalculater(Plan p, String name)
	{
		FunctionPlan fp = (FunctionPlan)instance().planMap.get(name);
		return fp == null ? null : new ConverterPlan(p, fp);
	}
	
	public static Function getCalculater(String name)
	{
		return (ConverterUsual)instance().usualMap.get(name);
	}
	
	public void addCalculater(Object func)
	{
//		if (func instanceof FunctionCommodity)
//			commodityMap.put(((FunctionCommodity)func).getName(), new ConverterCommodity((FunctionCommodity)func));
//		
//		if (func instanceof FunctionPlan)
//			planMap.put(((FunctionPlan)func).getName(), new ConverterPlan((FunctionPlan)func));
//		
//		if (func instanceof FunctionUsual)
//			usualMap.put(((FunctionUsual)func).getName(), new ConverterUsual((FunctionUsual)func));
		
		if (func instanceof FunctionCommodity)
			commodityMap.put(((FunctionCommodity)func).getName(), func);
		
		if (func instanceof FunctionPlan)
			planMap.put(((FunctionPlan)func).getName(), func);
		
		if (func instanceof FunctionUsual)
			usualMap.put(((FunctionUsual)func).getName(), new ConverterUsual((FunctionUsual)func));

	}
	
	public static class ConverterCommodity implements Function
	{
		Commodity c;
		FunctionCommodity f;
		
		public ConverterCommodity(Commodity c, FunctionCommodity f)
		{
			this.c = c;
			this.f = f;
		}
		
		public Object run(Object[] v, Factors p)
		{
			return f.runCommodity(c, v);
		}
	}
	
	public static class ConverterPlan implements Function
	{
		Plan plan;
		FunctionPlan f;
		
		public ConverterPlan(Plan plan, FunctionPlan f)
		{
			this.plan = plan;
			this.f = f;
		}
		
		public Object run(Object[] v, Factors p)
		{
			return f.runPlan(plan, v);
		}
	}
	
	public static class ConverterUsual implements Function
	{
		FunctionUsual f;
		
		public ConverterUsual(FunctionUsual f)
		{
			this.f = f;
		}
		
		public Object run(Object[] v, Factors p)
		{
			return f.runUsual(v);
		}
	}
}
