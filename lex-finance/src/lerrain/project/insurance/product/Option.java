package lerrain.project.insurance.product;

/**
 * 各类可选择项。 
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.formula.Formula;

public final class Option implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final String PAY			= "pay";
	public static final String PAY_FREQ		= "pay_freq";
	public static final String INSURE		= "insure";
	public static final String RANK			= "rank";
	public static final String DRAW			= "draw";
	public static final String DRAW_AGE		= "draw_age";
	public static final String DRAW_MODE	= "draw_mode";

	private String code;
	
	private Formula mode;
	private Formula value;

	private String show;
	private Formula desc;
	
	private Map other;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getShow()
	{
		return show;
	}

	public void setShow(String show)
	{
		this.show = show;
	}

	public Formula getMode()
	{
		return mode;
	}

	public void setMode(Formula mode)
	{
		this.mode = mode;
	}

	public Formula getValue()
	{
		return value;
	}

	public void setValue(Formula value)
	{
		this.value = value;
	}

	public Formula getDesc()
	{
		return desc;
	}

	public void setDesc(Formula desc)
	{
		this.desc = desc;
	}
	
	public void set(String name, Object value)
	{
		if (other == null)
			other = new HashMap();
		
		other.put(name, value);
	}
	
	public Object get(String name)
	{
		if (other == null)
			return null;
		
		return other.get(name);
	}
}
