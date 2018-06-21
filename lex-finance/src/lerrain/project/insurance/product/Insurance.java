package lerrain.project.insurance.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lerrain.project.insurance.product.rule.Rule;
import lerrain.tool.data.DataHub;
import lerrain.tool.formula.Formula;

/**
 * 组合产品
 * 组合产品根据输入部分、计算及输出部分分类
 * 
 * 1. 保险产品(Insurance)
 *    卡单等。
 * 2. 产品固定组合
 *    多险种，绑定，可调整其中产品的参数，可为其中的产品追加附加险，如太平的一诺千金。
 * 3. 产品推介组合
 *    多险种，不绑定，选择后组合的概念就不存在了，更像是多产品的快捷选定。如保险代理人自己设计的套餐等各种非固定套餐。
 * 4. 套餐组合
 *    多险种，绑定，限定计划，一个套餐组合就是一个计划，不可以加入其他险种。
 *    
 * 注意
 * 1. 产品固定组合存在打折销售、独立规则、屏蔽原规则、是否可添加其他险种、是否可添加附加险、是否可修改参数等问题。
 * 2. 有些产品固定组合，内部一切不可更改，子产品既没有单独特征也不能添加附加险，甚至有整体的份数、保额、保费概念，这类产品更像是单独
 *    产品，所以通常按照一个保险产品处理。如新华的卡单。
 * 3. 产品固定组合是一种介于单一产品与计划组合之间的形态。有一定的产品特性（固定的名称、强调整体、独立的投保规则），也有一定的组合特
 *    性（整体没有份数、保额、保费的概念，而子产品有，并且子产品参数可以分别修改）。
 * 4. 判断一个组合是作为单独产品处理，还是作为一个组合处理，主要是看他有无自己的参数特征（保障期限、缴费期限、保额、保费、份数）。 
 * 
 * 版本历史
 * 1. 初始版本。
 * 2. IT部分的参数表由产品参数表替换为IT表，带来的结果是IT内部计算变量可以不用写前缀IT，但是与外部重名时会覆盖掉外部变量，内部命名时
 *    务必注意不要和外部的主要参数重名。
 * 
 * @author lerrain
 *
 */
public class Insurance implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final int LIFEINS			= 1;
	public static final int FUND			= 2;
	
	public static final int CLAUSE			= 1; //普通产品
	public static final int PACKAGE			= 3; //自定义组合
	
	public static final int CURRENCY_CNY	= 1; //人民币
	public static final int CURRENCY_TWD	= 2; //新台币
	public static final int CURRENCY_USD	= 3; //美元
	public static final int CURRENCY_EUR	= 4; //欧元
	public static final int CURRENCY_GBP	= 5; //英镑
	public static final int CURRENCY_HKD 	= 6; //港元
	public static final int CURRENCY_JPY	= 7; //日元
	
	int version							= 2;						//定义文件版本，根据版本的不同，少数地方算法会有差异
	
	Company company;												//销售公司
	String vendor;													//供应商
	
	/*
	 * 基本定义
	 */
	String id;														//产品id，考虑程序接口简单，采用单键确定险种，公司间不可重复。
	String code;													//产品code
	String name;													//产品名称
	String abbrName;												//产品简称
	
	Formula fullCode;												//长代号，全球人寿使用加缴费年期的代号XAR20，是个动态值，不设置的话默认等于code

	double unit							= 1;						//单份额度

	int currency						= Insurance.CURRENCY_CNY;
	int type							= Insurance.CLAUSE;			//对象类型，产品、组合
	int category						= Insurance.LIFEINS;		//大类型，寿险、财险
	String productType					= null;						//小类型
	
	String[] channel					= null;						//销售渠道，为空表示所有渠道通用
	
	boolean hidden						= false;					//是否为不可选择的隐藏险种
	
	Formula inSale						= null;						//是否在售
	Date saleBeginDate					= null;						//起始销售时间
	Date saleEndDate					= null;						//结束销售时间
	
	Map accumulativeAmount;											//累计额度
	Map accumulativePremium;
	
	InitValue initValue					= null;						//初始化时默认的值
	
	InsuranceDepend depend				= null;

	/*
	 * 基本参数
	 */
	Map	options							= new HashMap(); 			//可选的各类列表
	
	/*
	 * 购买方式
	 */
	Purchase purchase					= new Purchase();
	
	/*
	 * 数据计算
	 */
	VariableDefine interestVars			= null;						//
	Map scriptMap						= null;

	List dutyList						= null;

	/*
	 * 规则相关
	 */
	boolean main						= true;						//是否可为主产品
	boolean rider						= false;					//是否可为附加产品

	List input							= null;						//输入项配置，通常不是必须的

	List bindList						= null;						//与该产品捆绑销售的产品列表，已考虑废除不要使用
	Map bindMap							= null;

	List riderList						= null;
	
	List ruleSkippedIdList				= null;						//跳过的购买规则总则id
	List ruleList						= null;						//购买规则列表
	
	Formula delay						= null;						//从购买日开始计算，该产品生效所需天数
	
	/*
	 * 界面
	 */
	int sequence						= 1000;						//在产品列表中的排序位置
	
	/*
	 * 组合，如果不是组合产品那么这个值为空
	 */
	Portfolio portfolio					= null;
	
	/*
	 * 附加
	 */
	Map additional						= null;
	
	/*
	 * 数据
	 */
	DataHub dataHub						= new DataHub();
	
	public int getInputMode()
	{
		return purchase.getInputMode();
	}
	
	public int getPurchaseMode()
	{
		return purchase.getPurchaseMode();
	}

	public void addBind(String id)
	{
		if (bindList == null)
			bindList = new ArrayList();
		
		bindList.add(id);
	}

	public List getDutyList()
	{
		return dutyList;
	}

	public void setDutyList(List dutyList)
	{
		this.dutyList = dutyList;
	}

	public void addAccumulativeAmount(String type, Formula formula)
	{
		if (accumulativeAmount == null)
			accumulativeAmount = new HashMap();
		
		accumulativeAmount.put(type, formula);
	}
	
	public Formula getAccumulativeAmount(String type)
	{
		if (accumulativeAmount == null) 
			return null;
		
		return (Formula)accumulativeAmount.get(type);
	}
	
	public void addAccumulativePremium(String type, Formula formula)
	{
		if (accumulativePremium == null)
			accumulativePremium = new HashMap();
		
		accumulativePremium.put(type, formula);
	}
	
	public Formula getAccumulativePremium(String type)
	{
		if (accumulativePremium == null) 
			return null;
		
		return (Formula)accumulativePremium.get(type);
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

	public void setAdditional(String name, Object value)
	{
		if (additional == null)
			additional = new HashMap();
		
		additional.put(name, value);
	}
	
	public Object getAdditional(String name)
	{
		if (additional == null)
			return null;
		
		return additional.get(name);
	}
	
	public Map getAdditional()
	{
		return additional;
	}
	
	public void addRider(String riderId)
	{
		if (riderList == null)
			riderList = new ArrayList();
		
		riderList.add(riderId);
	}

	public Date getSaleBeginDate()
	{
		return saleBeginDate;
	}

	public void setSaleBeginDate(Date saleBeginDate)
	{
		this.saleBeginDate = saleBeginDate;
	}

	public Date getSaleEndDate()
	{
		return saleEndDate;
	}

	public void setSaleEndDate(Date saleEndDate)
	{
		this.saleEndDate = saleEndDate;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAbbrName()
	{
		return abbrName;
	}

	public void setAbbrName(String abbrName)
	{
		this.abbrName = abbrName;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public double getUnit()
	{
		return unit;
	}

	public void setUnit(double unit)
	{
		this.unit = unit;
	}

	public int getSequence()
	{
		return sequence;
	}

	public void setSequence(int sequence)
	{
		this.sequence = sequence;
	}

	public boolean isMain()
	{
		return main;
	}

	public void setMain(boolean main)
	{
		this.main = main;
	}

	public boolean isRider()
	{
		return rider;
	}

	public void setRider(boolean rider)
	{
		this.rider = rider;
	}

	public String getProductType()
	{
		return productType;
	}

	public void setProductType(String productType)
	{
		this.productType = productType;
	}
	
//	public void setProductType(String productType)
//	{
//		this.productType = new ArrayList();
//		this.productType.add(productType);
//	}

	public boolean isHidden()
	{
		return hidden;
	}

	public void setHidden(boolean hidden)
	{
		this.hidden = hidden;
	}

	public List getRuleList()
	{
		return ruleList;
	}

	public void addRule(Rule rule)
	{
		if (ruleList == null)
			ruleList = new ArrayList();
		
		ruleList.add(rule);
	}

	public List getRuleSkippedIdList()
	{
		return ruleSkippedIdList;
	}

	public void addRuleSkippedId(String skippedId)
	{
		if (ruleSkippedIdList == null)
			ruleSkippedIdList = new ArrayList();
		
		ruleSkippedIdList.add(skippedId);
	}

	public boolean hasOptionType(String type)
	{
		return options.containsKey(type);
	}

	public List getOptionType()
	{
		return new ArrayList(options.keySet());
	}
	
	public List getOptionList(String type)
	{
		return (List)options.get(type);
	}
	
	public Option getOption(String type, String code)
	{
		List list = getOptionList(type);
		if (list == null)
			return null;
		
		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Option option = (Option)iter.next();
			if (option.getCode().equals(code))
				return option;
		}
		
		return null;
	}
	
	public void addOption(String type, Option item)
	{
		List list = (List)options.get(type);
		if (list == null)
		{
			list = new ArrayList();
			options.put(type, list);
		}
		
		list.add(item);
	}

	public VariableDefine getInterestVars()
	{
		return interestVars;
	}

	public void setInterestVars(VariableDefine interestVars)
	{
		this.interestVars = interestVars;
	}

	public DataHub getDataHub()
	{
		return dataHub;
	}

	public Company getCompany()
	{
		return company;
	}

	public void setCompany(Company insurance)
	{
		this.company = insurance;
	}

	public Purchase getPurchase()
	{
		return purchase;
	}

	public void setPurchase(Purchase purchase)
	{
		this.purchase = purchase;
	}

	public List getRiderList()	
	{
		return riderList;
	}

	public void setRiderIdList(List riderList)
	{
		this.riderList = riderList;
	}

	public int getCategory()
	{
		return category;
	}

	public void setCategory(int category)
	{
		this.category = category;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}

	public List getBindList() 
	{
		return bindList;
	}

	public void setBindList(List bindList) 
	{
		this.bindList = bindList;
	}

	public int getType()
	{
		return type;
	}

	public Portfolio getPortfolio()
	{
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio)
	{
		this.portfolio = portfolio;
	}
	
	public void addScript(String name, Formula f)
	{
		if (scriptMap == null)
			scriptMap = new HashMap();
		
		scriptMap.put(name, f);
	}

	public InsuranceDepend getDepend()
	{
		return depend;
	}

	public void setDepend(InsuranceDepend depend)
	{
		this.depend = depend;
	}

	public InitValue getInitValue()
	{
		return initValue;
	}

	public void setInitValue(InitValue initValue)
	{
		this.initValue = initValue;
	}

	public String[] getChannel()
	{
		return channel;
	}
	
	public boolean hasChannel(String channelName)
	{
		if (channel == null)
			return true;
		
		for (int i = 0; i < channel.length; i++)
			if (channelName.equals(channel[i]))
				return true;
		
		return false;
	}
	
	public void addBind(String productId, Formula c)
	{
		if (bindMap == null)
			bindMap = new LinkedHashMap();
		
		bindMap.put(productId, c);
	}

	public void setChannel(String[] channel)
	{
		this.channel = channel;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public int getCurrency()
	{
		return currency;
	}

	public void setCurrency(int currency)
	{
		this.currency = currency;
	}

	public Formula getFullCode()
	{
		return fullCode;
	}

	public void setFullCode(Formula fullCode)
	{
		this.fullCode = fullCode;
	}

	public String getVendor()
	{
		return vendor;
	}

	public void setVendor(String vendor)
	{
		this.vendor = vendor;
	}

	public Map getBindMap()
	{
		return bindMap;
	}

	public List getInput()
	{
		return input;
	}

	public void setInput(List input)
	{
		this.input = input;
	}
}
