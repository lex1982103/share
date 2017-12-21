package lerrain.project.insurance.plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lerrain.project.insurance.plan.function.FunctionMgr;
import lerrain.project.insurance.product.Insurance;
import lerrain.project.insurance.product.Purchase;
import lerrain.project.insurance.product.Variable;
import lerrain.project.insurance.product.VariableArray;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Function;

public class CommodityFactors implements FactorsSupport, Serializable
{
	private static final long serialVersionUID = 1L;

	Insurance insurance;
	Commodity commodity;

	CommodityInterest it;
	Object ds;
	
	Map vars = new HashMap();
	Map cache = new HashMap();

	FactorsSupport applicantFactors, insurantFactors;

	CommodityDuty duty;
	
	public CommodityFactors(Commodity commodity)
	{
		this.commodity = commodity;
		this.insurance = commodity.getProduct();
		
		applicantFactors = new CustomerFactors(commodity, CustomerFactors.APPLICANT);
		insurantFactors = new CustomerFactors(commodity, CustomerFactors.INSURANT);

		if (insurance.getInterestVars() != null)
			it = new CommodityInterest(this, insurance.getInterestVars());
		
//		//vars里面都是Formula
//		vars.put("INSURANT", insurantFactors);
//		vars.put("APPLICANT", applicantFactors);
//		vars.put("PRODUCT_ID", commodity.getProduct().getId());
//		vars.put("UNIT", new BigDecimal(commodity.getProduct().getUnitAmount()));
//		vars.put("PLAN", commodity.getPlan().getFactors());
//		if (commodity.getParent() != null)
//			vars.put("PARENT", commodity.getParent().getFactors());
		
//		Iterator iter = commodity.getCompany().getProductVars().toList().iterator();
//		while (iter.hasNext())
//		{
//			Variable v = (Variable)iter.next();
//			if (v.getParam() == null)
//				vars.put(v.getName(), v.getFormula());
//			else
//				vars.put(v.getName(), v.getFormula());
//		}

//		String vendor = insurance.getVendor();
//		String company = insurance.getCompany().getId();
//		if (!company.equals(vendor))
//		{
//			vars.putAll(insurance.getCompany().getProductVars().getAllVars());
//		}

		vars.putAll(insurance.getCompany().getProductVars().getAllVars());

		Formula amount = commodity.getProduct().getPurchase().getAmount();
		Formula premium = commodity.getProduct().getPurchase().getPremium();
		Formula quantity = commodity.getProduct().getPurchase().getQuantity();
		Formula premiumFy = commodity.getProduct().getPurchase().getPremiumFirstYear();
		
		if (amount != null)
			vars.put("AMOUNT", amount);
		if (premium != null)
			vars.put("PREMIUM", premium);
		if (quantity != null)
			vars.put("QUANTITY", quantity);
		if (premiumFy != null)
			vars.put("PREMIUM_FY", premiumFy);

		if (insurance.getDutyList() != null)
			duty = new CommodityDuty(insurance.getDutyList(), this);
	}
	
	/**
	 * 执行内部的一个函数
	 * @param name
	 * @param with
	 * @return
	 */
	public Object run(String name, Object[] with)
	{
		Object n = this.getCommodity().getAdditional(name);
		
		if (n == null)
			n = vars.get(name);
		if (n == null)
			n = it.get(name);
		if (n == null)
			return null;
		
		if (n instanceof Variable)
		{
			Variable f = (Variable)n;
			if (f.getParam() != null)
			{
				VariableArray va = (VariableArray)f.run(this);
				return va.run(with, this);
			}
			else
			{
				return f.run(this);
			}
		}
		else if (n instanceof VariableArray)
		{
			return ((VariableArray)n).run(with, it.factors);
		}
		else if (n instanceof Formula)
		{
			return ((Formula)n).run(this);
		}
		else
		{
			return n;
		}
	}

	/**
	 * @deprecated
	 */
	public Object interest(String name, Object[] with)
	{
		Object n = it.get(name);

		if (n == null)
		{
			return null;
		}
		else if (n instanceof VariableArray)
		{
			return ((VariableArray)n).run(with, it.factors);
		}
		else if (n instanceof Formula)
		{
			return ((Formula)n).run(it.factors);
		}
		else
		{
			return n;
		}
	}

	/**
	 * @deprecated
	 */
	public void setFormula(String name, Formula var)
	{
		vars.put(name, var);
	}

	public void setVar(String name, Object var)
	{
		vars.put(name, var);
	}
	
//	/**
//	 * 临时计算变量，仅作为缓冲用。
//	 * @param name
//	 * @param value
//	 */
//	public void setCachedValue(String name, Object value)
//	{
//		cache.put(name, value);
//	}
	
	public Object get(String name)
	{
		Object r = null;
		
//		if ("AGE".equals(name))
//			name = name + "";
		
		if ("THIS".equals(name))
			return this;
		if ("this".equals(name))
			return this.getCommodity();
		
		/*
		 * 注意：商品的客户参数表和计划的客户参数表是不同的
		 * 
		 * 比如：
		 * 太平人寿一诺千金中的成长型年金，其生效时间是几十年后，所以它的AGE和计划的AGE差别很大。
		 */
		if ("TYPE".equals(name))
			return commodity.getProduct().getProductType();
		if ("PRODUCT_ID".equals(name))
			return commodity.getProduct().getId();
		if ("CODE".equals(name))
			return commodity.getProduct().getCode();
		if ("DUTY".equals(name))
			return duty;
		if ("NAME".equals(name))
			return commodity.getProduct().getName();
		if ("UNIT".equals(name) || "UNIT_AMOUNT".equals(name)) //UNIT_AMOUNT是以前的写法
			return Double.valueOf(commodity.getProduct().getUnit());
		if ("CURRENCY".equals(name))
			return getCurrencyWord(commodity.getProduct().getCurrency());
		if ("PAY_PER_YEAR".equals(name))
			return new Integer(commodity.getPayFreqPerYear());
		if ("PURCHASE".equals(name))
		{
			int m = commodity.getProduct().getPurchase().getPurchaseMode();
			if (m == Purchase.AMOUNT)
				return "amount";
			if (m == Purchase.RANK)
				return "rank";
			if (m == Purchase.QUANTITY)
				return "quantity";
			return null;
		}
		
		if ("PLAN".equals(name))
			return commodity.getPlan().getFactors();
		if ("plan".equals(name))
			return commodity.getPlan();
		
		if ("PARENT".equals(name) && commodity.getParent() != null)
			return commodity.getParent().getFactors();
		if ("parent".equals(name))
			return commodity.getParent();
		if ("PACK".equals(name))
			return commodity.pack.factors;

		if ("GROUP".equals(name)) //?
		{
			Plan plan = commodity.getPlan();
			for (int i = 0; i < plan.size(); i++)
			{
				UnstableList l = plan.getCommodity(i).getChildren();
				if (l != null && l.has(commodity))
					return plan.getCommodity(i).getFactors();
			}
			
			return null;
		}
		
		if ("IT".equals(name))
			return it;
		if ("DS".equals(name))
		{
			if (ds == null)
				ds = commodity.getProduct().getDataHub().run(this);
			
			return ds;
		}
		
		/*
		 * 固定值或输入值，包括保额、保费、份数
		 */
		r = this.getCommodity().getValue(name);
		if (r != null)
			return r;
		
//		/*
//		 * 如果不是输入的保额、保费、份数，那么直接从公式计算。
//		 */
//		if ("AMOUNT".equals(name))
//			name = "AMOUNT_DTL";
//		else if ("PREMIUM".equals(name))
//			name = "PREMIUM_DTL";
//		else if ("QUANTITY".equals(name))
//			name = "QUANTITY_DTL";

		/*
		 * 缓冲
		 */
		r = cache.get(name);
		if (r != null)
			return r;

		r = this.getCommodity().getAdditional(name);
		if (r != null)
		{
			if (r instanceof Variable) //如果有这个值表示是一个函数，函数有入参，结果根据不同的参数会变化，所以不能缓存结果。
			{
				r = ((Variable)r).run(this); //使用this，这样PARENT.XXX(A,B)，使用的是PARENT的环境
			}
			else if (r instanceof Formula) //<init>标签内的公式是放在Additional里面的，这时需要运算
			{
				r = ((Formula)r).run(this); //使用this，这样PARENT.XXX(A,B)，使用的是PARENT的环境
				cache.put(name, r);
			}
			
			return r;
		}

		r = vars.get(name);
		if (r != null)
		{
			if (r instanceof Variable) //如果有这个值表示是一个函数，函数有入参，结果根据不同的参数会变化，所以不能缓存结果。
			{
				r = ((Variable)r).run(this); //使用this，这样PARENT.XXX(A,B)，使用的是PARENT的环境
			}
			else if (r instanceof Formula) //<init>标签内的公式是放在Additional里面的，这时需要运算
			{
				r = ((Formula)r).run(this); //使用this，这样PARENT.XXX(A,B)，使用的是PARENT的环境
				cache.put(name, r);
			}

			return r;
		}

		if ("INSURANT".equals(name))
			return insurantFactors;
		if ("APPLICANT".equals(name))
			return applicantFactors;

		if (commodity.getProduct().hasOptionType(name))
		{
			Input input = new Input(commodity.getCompany().getOption(name, (String)this.get(name.toUpperCase())), commodity);
			cache.put(name, input);

			return input;
		}

		/*
		 * 计算器只需直接返回计算器对象，由公式引擎调用它计算，不需要自己计算。
		 * 虽然返回的是函数体，但是关联险种不存在直接使用他们的可能性。
		 * 而由其他函数调用的时候在其他函数那里factors已经转换为对应险种的factors，所以不会有问题
		 *
		 * 直接调用时有问题：PARENT.HasProduct('XXXX')
		 */
		r = FunctionMgr.getCommodityCalculater(commodity, name);
		if (r != null)
			return r;

		r = FunctionMgr.getCalculater(name);
		if (r != null)
			return r;
		
		if ("RIDER".equals(name))
			return new RiderFunction(commodity); 
		
		if ("CHILD".equals(name))
			return new ChildFunction(commodity);

		if ("CHILDREN".equals(name))
		{
			List cr = new ArrayList();
			UnstableList sl = commodity.getChildren();
			for (int i=0;i<sl.size();i++)
				cr.add(sl.get(i).getFactors());

			cache.put(name, cr);
			return cr;
		}

//		if ("INPUT".equals(name))
//			return new InputFunction(commodity);

		return commodity.getPlan().getFactor(name);
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
		insurantFactors.clearCache();
		applicantFactors.clearCache();

		if (duty != null)
			duty.clear();

		cache.clear();
		
		if (it != null)
			it.clear();
		
		ds = null;
	}

	public Commodity getCommodity()
	{
		return commodity;
	}
	
	public String toString()
	{
		return commodity.getProduct().getName() + ", " + commodity.getValue() + ", " + vars.toString() + ", " + cache.toString();
	}
	
	public static String getCurrencyWord(int currency)
	{
		if (currency == Insurance.CURRENCY_CNY)
			return "cny";
		else if (currency == Insurance.CURRENCY_TWD)
			return "twd";
		else if (currency == Insurance.CURRENCY_USD)
			return "usd";
		else if (currency == Insurance.CURRENCY_EUR)
			return "eur";
		else if (currency == Insurance.CURRENCY_GBP)
			return "gbp";
		else if (currency == Insurance.CURRENCY_HKD)
			return "hdk";
		else if (currency == Insurance.CURRENCY_JPY)
			return "jpy";
		else
			return "unknown";
	}
	
	public static class RiderFunction implements Function
	{
		Commodity commodity;
		
		public RiderFunction(Commodity commodity)
		{
			this.commodity = commodity;
		}
		
		public Object run(Object[] v, Factors p)
		{
			Commodity rider = commodity.getRider((String)v[0]);
			return rider != null ? rider.getFactors() : null;
		}
	};
	
	public static class ChildFunction implements Function
	{
		Commodity commodity;
		
		public ChildFunction(Commodity commodity)
		{
			this.commodity = commodity;
		}
		
		public Object run(Object[] v, Factors p)
		{
			Commodity child = commodity.getChild((String) v[0]);
			return child != null ? child.getFactors() : null;
		}
	};

//	public static class InputFunction implements Function
//	{
//		Commodity commodity;
//
//		public InputFunction(Commodity commodity)
//		{
//			this.commodity = commodity;
//		}
//
//		public Object run(Object[] v, Factors p)
//		{
//			return new Input(commodity.getCompany().getOption((String)v[0], (String)v[1]), commodity);
//		}
//	};
}
