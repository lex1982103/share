package lerrain.project.insurance.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lerrain.tool.formula.Formula;


public class InitValue implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String insurantId; //被保险人不是默认的被保险人时，设定这个
	
	Formula premium;
	Formula amount;
	Formula quantity;
	
	Map options = new HashMap();
	Map value = new HashMap();
	
	public InitValue()
	{
	}

	public Map getValue()
	{
		return value;
	}
	
	public Map getOptions()
	{
		return options;
	}
	
	public void setOption(String key, Formula option)
	{
		options.put(key, option);
	}
	
	public Formula getOption(String key)
	{
		return (Formula)options.get(key);
	}
	
	public void set(String name, Object v)
	{
		value.put(name, v);
	}
	
	public Object get(String name)
	{
		return value.get(name);
	}

	public Formula getPremium()
	{
		return premium;
	}

	public void setPremium(Formula premium)
	{
		this.premium = premium;
	}

	public Formula getAmount()
	{
		return amount;
	}

	public void setAmount(Formula amount)
	{
		this.amount = amount;
	}

	public Formula getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Formula quantity)
	{
		this.quantity = quantity;
	}

	public String getInsurantId()
	{
		return insurantId;
	}

	public void setInsurantId(String insurantId)
	{
		this.insurantId = insurantId;
	}
}
