package lerrain.project.insurance.plan;

import java.util.HashMap;
import java.util.Map;

import lerrain.project.insurance.product.Variable;
import lerrain.project.insurance.product.VariableDefine;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.Stack;

public class CommodityInterest extends Stack implements Function
{
	CommodityFactors factors;
	VariableDefine vars;
	
	Map cache;
	
	int version;
	
	public CommodityInterest(CommodityFactors factors, VariableDefine vars)
	{
		super(factors);
		
		this.factors = factors;
		this.vars = vars;
		
		this.version = factors.getCommodity().getProduct().getVersion();
	}

	public Object get(String name)
	{
		if (cache == null)
		{
			cache = new HashMap();
			
			if (vars.getScript() != null) //IT主脚本
				vars.getScript().run(this);
		}
		
		if ("save".equals(name)) //CommodityInterest也是一个Function，写在一起了，可以使用save方法向IT中写值
			return this;
		
		if (cache.containsKey(name))
			return cache.get(name);
		
		Variable v = (Variable)vars.get(name);
		if (v != null) //定义的函数
		{
			Object r = v.run(version == 1 ? (Factors)factors : (Factors)this);
			cache.put(name, r);
			return r;
		}
		
		/*
		 * 现在CommodityInterest本身是一个Stack，也就是IT主脚本中的全局变量会保留，可以直接大写命名全局函数，不需要在使用save函数保存。
		 * save函数用于向下支持，以及一些特殊的情况下直接写值。
		 */
		return super.get(name); 
	}
	
	public void clear()
	{
		cache = null;
	}

	/**
	 * interest标签脚本中的save函数
	 */
	public Object run(Object[] v, Factors p)
	{
		cache.put(v[0], v[1]);
		
		return null;
	}
}
