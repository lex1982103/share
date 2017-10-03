package lerrain.project.insurance.product;


import lerrain.tool.formula.Formula;

public class InsuranceRecom
{
	Insurance parent;
	Insurance insurance;

	InitValue initValue;
	Formula c;

	String desc;

	public InsuranceRecom(Insurance parent, Insurance insurance, InitValue initValue, Formula c, String desc)
	{
		this.parent = parent;
		this.initValue = initValue;
		this.insurance = insurance;
		this.c = c;
		this.desc = desc;
	}

	public Formula getCondition()
	{
		return c;
	}

	public Insurance getParent()
	{
		return parent;
	}
	
	public Insurance getInsurance()
	{
		return insurance;
	}

	public InitValue getInitValue()
	{
		return initValue;
	}
	
	public String getDesc()
	{
		return desc;
	}
}
