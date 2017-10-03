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

/**
 * @deprecated
 */
public class Company20170813 implements Serializable
{
	private static final long serialVersionUID = 1L;

	String id;
	String name;

	Company20170813 parent;

	VariableDefine planVars;
	VariableDefine productVars;

	AgeCalculator ageCalculator;

	Map ruleMap; //通则
	Map inputMap;
	Map riskMap;

	List productList; //产品定义列表
	List packageList;

	Map dataParserMap; //数据源集合
	Map filterMap;
	Map additional;

	public Company20170813(String id)
	{
		this(id, null);
	}

	public Company20170813(String id, Company20170813 d)
	{
		this.id = id;

		dataParserMap = new HashMap();
		riskMap = new HashMap();
		ruleMap = new HashMap();
		inputMap = new HashMap();
		additional = new HashMap();

		productList = new ArrayList();
		packageList = new ArrayList();
		
		planVars = new VariableDefine();
		productVars = new VariableDefine();

		if (d == null)
		{
			filterMap = new HashMap();
			filterMap.put("coverage", new CoverageFilter());
			filterMap.put("liability", new LiabilityFilter());
			filterMap.put("table", new TableFilter());
			filterMap.put("combo", new ComboFilter());
			filterMap.put("combo_chart", new ComboChartFilter());
			filterMap.put("chart", new ChartFilter());
			filterMap.put("chart@axa", new AxaChartFilter());
			filterMap.put("tgraph", new TGraphFilter());
			filterMap.put("document", new DocumentFilter());
		}
		else
		{
			planVars.addAll(d.planVars);
			productVars.addAll(d.productVars);
		}

		this.parent = d;

		//数据源不需要处理，数据源是所有统一额外管理的（数据源不可以重名覆盖），不通过公司对象。
	}
	
	public void addDataParser(String id, DataParser parser)
	{
		dataParserMap.put(id, parser);
	}
	
	public DataParser getDataParser(String id)
	{
		DataParser d = (DataParser)dataParserMap.get(id);
		if (d != null)
			return d;

		return parent != null ? parent.getDataParser(id) : null;
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
		Map m = (Map)riskMap.get(productType);
		if (m != null)
			return m;

		return parent != null ? parent.getAccumulation(productType) : null;
	}
	
	public String getName()
	{
		return name;
	}
	
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
		List list = (List)ruleMap.get(new Integer(type));
		if (list != null)
			return list;

		return parent != null ? parent.getRuleList(type) : null;
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
			return parent != null ? parent.getOption(type, code) : null;
		
		Option option = (Option)map.get(code);
		return option != null ? option : parent != null ? parent.getOption(type, code) : null;
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
		Object val = additional.get(name);
		return val != null ? val : parent != null ? parent.getAdditional(name) : null;
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
		return parent == null ? null : parent.getCommodityFilter(name);
	}
	
	public FilterPlan getPlanFilter(String name) throws FilterNotFoundException
	{
		Object filter = filterMap.get(name);
		
		if (filter instanceof FilterPlan)
			return (FilterPlan)filter;
			
		//throw new FilterNotFoundException("未找到的过滤器：" + name);
		return parent == null ? null : parent.getPlanFilter(name);
	}

	public AgeCalculator getAgeCalculator()
	{
		return ageCalculator != null ? ageCalculator : parent != null ? parent.getAgeCalculator() : null;
	}

	public void setAgeCalculator(AgeCalculator ageCalculator)
	{
		this.ageCalculator = ageCalculator;
	}
}
