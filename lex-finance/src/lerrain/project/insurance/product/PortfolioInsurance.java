package lerrain.project.insurance.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 产品组合
 * 1. 购买一个产品时，自动附赠一些其他产品，这种方式以自动追加产品的形式实现。
 * 2. 几个同类产品简单合并，组合有自己的名字，可以自动设定每个产品的参数，这些产品新增和删除时绑或不绑在一起，可以或不可以局部调节每个产品的属性。
 * 3. 组合可以以产品的形态出现在列表中，可以查看组合内部的详情（也可以选择隐藏组合，显示内部）。不可为内部的险种增加附加险。
 *    根据组合本身的参数和内部险种的参数是否可调，分位3种形式，可调+不可调、不可调+可调、都不可调。
 * 4. 跨界组合，由于内部产品的多种形式，无法支持通过组合统一设定参数的模式。
 *    跨界组合内部包含多种同类型组合。
 * 5. 组合产品（太平一诺千金）、卡单、套餐
 *    
 * 2 部分实现
 * 3、4 不实现。
 * 
 * 万能自动追加附加险（无名称，绑定），一诺千金（有名称，绑定）、卡单（有名称，绑定，不能有其他产品，可填份数）、产品套餐（有名称，绑定，不可更改）
 * 卡单作为一个产品处理，暂时不提供查看内部产品的方法。
 * 
 * 这个类是种类5的实现
 * 
 * 1 自动追加
 * 2 松散组合（可以理解为快捷选择，选定后组合概念消失）
 * 3 绑定组合（独占计划，可更改）
 * 4 绑定组合（独占计划，不可更改）
 * 
 * 松散组合
 * 
 * @author lerrain
 * @deprecated
 *
 */
public class PortfolioInsurance implements Serializable
{
	private static final long serialVersionUID		= 1L;
	
	String id;
	
	String name;
	
	int type;
	int currency						= Insurance.CURRENCY_CNY;
	int sequence						= 1000;
	
	Company company;
	
	Date saleBeginDate					= null;						//起始销售时间
	Date saleEndDate					= null;						//结束销售时间
	
	Map map = new HashMap();
	
	List group = new ArrayList();
	
	public PortfolioInsurance()
	{
		this(Insurance.PACKAGE);
	}
	
	public PortfolioInsurance(int type)
	{
		this.type = type;
	}
	
	public void addProduct(Insurance parent, Insurance product, InitValue value)
	{
		if (parent != null)
			map.put(product.getId(), parent);
		
		map.put(product.getId() + "@init", value);
			
		group.add(product);
	}
	
	public void addProduct(Insurance product, InitValue value)
	{
		addProduct(null, product, value);
	}
	
	public int size()
	{
		return group.size();
	}
	
	public Insurance getParent(Insurance product)
	{
		return (Insurance)map.get(product.getId());
	}
	
	public InitValue getInitValue(Insurance product)
	{
		return (InitValue)map.get(product.getId() + "@init");
	}
	
	public Iterator iterator()
	{
		return group.iterator();
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

	public Company getCompany()
	{
		return company;
	}

	public void setCompany(Company insurance)
	{
		this.company = insurance;
	}

	public int getCategory()
	{
		return Insurance.LIFEINS;
	}

	public String getCode()
	{
		return "group";
	}

	public int getType()
	{
		return type;
	}

	public int getSequence()
	{
		return sequence;
	}

	public void setSequence(int sequence)
	{
		this.sequence = sequence;
	}

	public void setName(String name)
	{
		this.name = name;
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

	public int getCurrency()
	{
		return currency;
	}

	public void setCurrency(int currency)
	{
		this.currency = currency;
	}
}
