package lerrain.project.insurance.product;

import lerrain.project.insurance.plan.filter.*;
import lerrain.project.insurance.plan.filter.axachart.AxaChartFilter;
import lerrain.project.insurance.plan.filter.chart.ChartFilter;
import lerrain.project.insurance.plan.filter.liability.LiabilityFilter;
import lerrain.project.insurance.plan.filter.table.ComboChartFilter;
import lerrain.project.insurance.plan.filter.table.ComboFilter;
import lerrain.project.insurance.plan.filter.table.TableFilter;
import lerrain.project.insurance.plan.filter.tgraph.TGraphFilter;
import lerrain.project.insurance.product.rule.Rule;
import lerrain.tool.data.DataParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Company implements Serializable
{
	private static final long serialVersionUID = 1L;

	String id;
	String name;

	VariableDefine planVars;
	VariableDefine productVars;

	AgeCalculator ageCalculator;

//	VariableSet variableSet;

//	Map formulaMap;

	//通则
	Map ruleMap;

	Map inputMap;

	Map riskMap;

	//产品定义列表
	List productList;

	List packageList;

	//数据源集合
	Map dataParserMap;

	Map filterMap;

	Map additional;

	public Company(String id)
	{
		this(id, null);
	}

	public Company(String id, Company d)
	{
		this.id = id;

//		ageCalculator = new AgeCalculator() 
//		{
//			public int getAge(Date birthday, Date now)
//			{
//				return Time.getAge(birthday, now);
//			}
//
//			public int getAgeDay(Date birthday, Date now)
//			{
//				return (int)((now.getTime() - birthday.getTime()) / 1000 / 3600 / 24);
//			}
//
//			public int getAgeMonth(Date birthday, Date now)
//			{
//				return Time.getAgeMonth(birthday, now);
//			}
//		};

		dataParserMap = new HashMap();
		riskMap = new HashMap();
//		varDefMap = new HashMap();
		ruleMap = new HashMap();
		inputMap = new HashMap();
		additional = new HashMap();
//		formulaMap = new HashMap();

		productList = new ArrayList();
		packageList = new ArrayList();

		planVars = new VariableDefine();
		productVars = new VariableDefine();
//		variableSet = new VariableSet();

		if (d == null)
		{
			filterMap = Config.getFilterMap();
		}
		else
		{
			filterMap = d.filterMap;

//			if (d.variableSet != null)
//				variableSet.putAll(d.variableSet);

			if (d.planVars != null)
				planVars.addAll(d.planVars);

			if (d.productVars != null)
				productVars.addAll(d.productVars);

			if (d.riskMap != null)
				riskMap.putAll(d.riskMap);

			if (d.inputMap != null)
				inputMap.putAll(d.inputMap);

			//数据源不需要处理，数据源是所有统一额外管理的（数据源不可以重名覆盖），不通过公司对象。
		}
	}

	public void addDataParser(String id, DataParser parser)
	{
		dataParserMap.put(id, parser);
	}

	public DataParser getDataParser(String id)
	{
		if (dataParserMap == null)
			return null;

		return (DataParser)dataParserMap.get(id);
	}

	public void addAccumulation(String productType, Map ac)
	{
		riskMap.put(productType, ac);
	}

	/**
	 * 每种类型的产品，都会累加多个类型的风险保额。
	 * 比如普通寿险，通常会累加意外险风险保额、寿险风险保额等。
	 * @param productType 产品类型
	 * @return
	 */
	public Map getAccumulation(String productType)
	{
		if (riskMap == null)
			return null;

		return (Map)riskMap.get(productType);
	}

	public String getName()
	{
		return name;
	}

//	public Function getVariable(int type, String name)
//	{
//		return variableSet.getVariable(type, name);
//	}
//	
//	public Map getVariableMap(int type)
//	{
//		return variableSet.getVariableMap(type);
//	}
//	
//	public void addVariable(int type, String name, Function function)
//	{
//		variableSet.addVariable(type, name, function);
//	}

	public VariableDefine getProductVars()
	{
		return productVars;
	}

	public VariableDefine getPlanVars()
	{
		return planVars;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List getRuleList(int type)
	{
		if (ruleMap == null)
			return null;

		return (List)ruleMap.get(new Integer(type));
	}

	public void addRule(Rule rule)
	{
		int type = rule.getType();
		List ruleList = (List)ruleMap.get(new Integer(type));
		if (ruleList == null)
		{
			ruleList = new ArrayList();
			ruleMap.put(new Integer(type), ruleList);
		}
		ruleList.add(rule);
	}

	public void addOptionType(String type, String variableName)
	{
		inputMap.put(type + "/name", variableName);
	}

	public String getOptionVariable(String type)
	{
		return (String)inputMap.get(type + "/name");
	}

	public void addOption(String type, Option item)
	{
		Map map = (Map)inputMap.get(type);
		if (map == null)
		{
			map = new HashMap();
			inputMap.put(type, map);
		}
		map.put(item.getCode(), item);
	}

	public Option getOption(String type, String code)
	{
		Map map = (Map)inputMap.get(type);
		if (map == null)
			return null;

		return (Option)map.get(code);
	}

	public List getPackageList()
	{
		return packageList;
	}

	public List getProductList()
	{
		return productList;
	}

	public Insurance getProduct(String productId)
	{
		if (productList == null)
			return null;

		for (int i=0;i<productList.size();i++)
		{
			Insurance def = (Insurance)productList.get(i);
			if (productId.equals(def.getId()))
				return def;
		}

		return null;
	}

	public void setAdditional(String name, Object value)
	{
		additional.put(name, value);
	}

	public Object getAdditional(String name)
	{
		if (additional == null)
			return null;

		return additional.get(name);
	}

	public void setAttachment(String name, String filterName, Object value)
	{
		setAdditional("attachment_" + name, value);
		setAdditional("attachment_filter_" + name, filterName);
	}

	public Object getAttachment(String name)
	{
		return getAdditional("attachment_" + name);
	}

	public String getAttachmentFilterName(String name)
	{
		return (String)getAdditional("attachment_filter_" + name);
	}

	public void addProduct(Insurance product)
	{
		if (productList.indexOf(product) < 0)
			productList.add(product);
	}

	public void addPackage(PackageIns product)
	{
		if (packageList.indexOf(product) < 0)
			packageList.add(product);
	}

	public FilterCommodity getCommodityFilter(String name) throws FilterNotFoundException
	{
		Object filter = filterMap.get(name);

		if (filter instanceof FilterCommodity)
			return (FilterCommodity)filter;

		//throw new FilterNotFoundException("未找到的过滤器：" + name);
		return null;
	}

	public FilterPlan getPlanFilter(String name) throws FilterNotFoundException
	{
		Object filter = filterMap.get(name);

		if (filter instanceof FilterPlan)
			return (FilterPlan)filter;

		//throw new FilterNotFoundException("未找到的过滤器：" + name);
		return null;
	}

	public AgeCalculator getAgeCalculator()
	{
		return ageCalculator;
	}

	public void setAgeCalculator(AgeCalculator ageCalculator)
	{
		this.ageCalculator = ageCalculator;
	}
}
