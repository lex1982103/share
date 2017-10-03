package lerrain.project.insurance.plan;

/**
 * Commodity输入项（选定的选择项） 
 * 
 * 2015-01-24 李新豪
 * 去除了这里的缓冲功能。
 * 这里缓冲没有太大意义，却增加了产品的关联关系，提高了复杂度
 */

import java.io.Serializable;

import lerrain.project.insurance.product.Option;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class Input implements Serializable, Factors
{
	private static final long serialVersionUID = 1L;
	
	public static final int MODE_YEAR	= 0;
	public static final int MODE_TO		= 1;
	public static final int MODE_MONTH	= 2;

	Option option;
	
	Factors factors;
	
	public Input(Option option, Commodity product)
	{
		this.option = option;
		this.factors = product.getFactors();
	}
	
	private int getAge()
	{
		return ((Integer)factors.get("AGE")).intValue();
	}
	
	public String getCode()
	{
		return option.getCode();
	}
	
	public int getValue()
	{
		return Value.intOf(option.getValue(), factors);
	}
	
	public int getMode()
	{
		if (option.getMode() == null)
			return 0;
		
		return Value.intOf(option.getMode(), factors);
	}
	
	public String getDesc()
	{
		return option.getDesc() == null ? option.getShow() : Value.stringOf(option.getDesc(), factors);
	}
	
	public Option getOption()
	{
		return option;
	}
	
	public Object get(String key)
	{
		if ("CODE".equals(key))
			return option.getCode();
		if ("VALUE".equals(key))
			return new Integer(getValue());
		if ("DESC".equals(key))
			return getDesc();
		if ("MODE".equals(key))
			return new Integer(getMode());
		
		if ("PERIOD".equals(key))
			return new Integer(getPeriodYear());
		if ("MONTH".equals(key))
			return new Integer(getPeriodMonth());
		
		return null;
	}
	
	public int getPeriodYear()
	{
		return periodOf(this);
	}
	
	public int getPeriodMonth()
	{
		return monthOf(this);
	}
	
	public static int periodOf(Input input)
	{
		int value = input.getValue();
		int mode = input.getMode();
		if (mode == MODE_TO) //to
		{
			return value - input.getAge();
		}
		else if (mode == MODE_MONTH) //month
		{
			return value / 12; //不满一年的按0算
		}
		
		return value;
	}
	
	public static int monthOf(Input input)
	{
		int value = input.getValue();
		int mode = input.getMode();
		if (mode == MODE_TO)
		{
			return (value - input.getAge()) * 12;
		}
		else if (mode == MODE_MONTH)
		{
			return value;
		}
		
		return value * 12;
	}
}
