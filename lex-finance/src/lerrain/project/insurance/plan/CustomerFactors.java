package lerrain.project.insurance.plan;

import java.io.Serializable;
import java.util.Date;

public class CustomerFactors implements FactorsSupport, Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final int INT_UNINIT		= -9;
	private static final int INT_EXCEPTION	= -1;
	
	public static final int APPLICANT		= -1;
	public static final int INSURANT		= -2;
	public static final int EMPTY			= -99;
	
	int role	= EMPTY;

	InsuranceCustomer c;
	
	Plan plan;
	Commodity commodity;
	
	Date insureDate;
	
//	String productType;
	
	int age, month, day, insage;
	
	public CustomerFactors(Plan plan, int role)
	{
		this.plan = plan;
		this.role = role;
		
		clearCache();
	}
	
	public CustomerFactors(Commodity commodity, int role)
	{
		this.commodity = commodity;
		this.plan = commodity.getPlan();
		this.role = role;
		
		clearCache();
	}
	
////	public CustomerFactors(InsuranceCustomer c)
////	{
////		this.c = (InsuranceCustomer)c;
////		this.plan = null;
////		this.productType = null;
////		
////		clearCache();
////	}
//	
//	public CustomerFactors(InsuranceCustomer c, Plan plan)
//	{
//		this.c = c;
//		this.plan = plan;
////		this.productType = null;
//		
//		clearCache();
//	}
//	
//	public CustomerFactors(InsuranceCustomer c, Commodity commodity)
//	{
//		this.c = c;
//		this.commodity = commodity;
//		
//		//产品的productType是不会变的，这里可以直接取过来
////		List type = commodity.getProduct().getProductType();
////		if (type != null && !type.isEmpty())
////			this.productType = (String)commodity.getProduct().getProductType().get(0);
//		
//		clearCache();
//	}
	
	public boolean equals(Object value)
	{
		if (value instanceof CustomerFactors)
		{
			CustomerFactors cf = (CustomerFactors)value;
			if (c != null && ((Object)c).equals(cf.c))
				return true;
		}
		
		return false;
	}
	
	private int getAge()
	{
		if (age == INT_UNINIT)
		{
			if (c.getBirthday() == null)
				age = INT_EXCEPTION;
			else
				age = Time.getAge(c.getBirthday(), insureDate);
		}
		
		return age;
	}
	
	private int getAgeMonth()
	{
		if (month == INT_UNINIT)
		{
			if (c.getBirthday() == null)
				month = INT_EXCEPTION;
			else
				month = Time.getAgeMonth(c.getBirthday(), insureDate);
		}
		
		return month;
	}
	
	private int getAgeDay()
	{
		if (day < 0)
		{
			if (c.getBirthday() == null)
				day = INT_EXCEPTION;
			else
				day = (int)((insureDate.getTime() - c.getBirthday().getTime()) / 1000 / 3600 / 24);
		}

		return day;
	}
	
	private int getInsAge()
	{
		if (insage == INT_UNINIT)
		{
			if (c.getBirthday() == null)
				insage = INT_EXCEPTION;
			else if (!plan.isEmpty() && plan.primaryCommodity().getCompany().getAgeCalculator() != null)
				insage = plan.primaryCommodity().getCompany().getAgeCalculator().getAge(c.getBirthday(), insureDate);
		}
		
		return insage;
	}
	
	public Object get(String name)
	{
		if ("ID".equals(name))
			return c.getId();
		if ("GENDER".equals(name))
			return new Integer(c.getGenderCode());
		if ("BIRTHDAY".equals(name))
			return c.getBirthday();
		if ("AGE".equals(name))
			return new Integer(getAge());
		if ("DAY".equals(name))
			return new Integer(getAgeDay());
		if ("MONTH".equals(name))
			return new Integer(getAgeMonth());
		if ("INS_AGE".equals(name)) //保险年龄，部分公司设定保险年龄，其算法不同于一般年龄
			return new Integer(getInsAge());
		if ("RELATION".equals(name))
			return plan.getInsurantRelation(c.getId());
		if ("instance".equals(name))
			return c;
		if ("OCCUPATION_CATEGORY".equals(name))
		{
			if (commodity != null)
				return new Integer(c.getOccupationCategory(commodity.getProduct().getProductType()));
			else
				return new Integer(c.getOccupationCategory(null));
		}
		
		return c.get(name);
	}

	public void clearCache()
	{
		age = INT_UNINIT;
		insage = INT_UNINIT;
		month = INT_UNINIT;
		day = INT_UNINIT;
		
		if (commodity != null)
			insureDate = commodity.getInsureTime();
		else
			insureDate = plan.getInsureTime();

		if (insureDate == null)
			insureDate = new Date();
		
		if (role == CustomerFactors.APPLICANT)
		{
			this.c = plan.getApplicant();
		}
		else if (role == CustomerFactors.INSURANT)
		{
			if (commodity == null || commodity.getInsurantId() == null)
				this.c = plan.getInsurant();
			else
				this.c = plan.getInsurant(commodity.getInsurantId());
		}
		
		//error
	}
	
	public InsuranceCustomer getCustomer()
	{
		return c;
	}
}
