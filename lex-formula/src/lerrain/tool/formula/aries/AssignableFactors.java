package lerrain.tool.formula.aries;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.formula.Factors;

public class AssignableFactors implements Factors, Serializable
{
	private static final long serialVersionUID = 1L;

	Factors factors;
	
	Map map = new HashMap();
	
	public AssignableFactors(Factors factors)
	{
		this.factors = factors;
	}
	
	public AssignableFactors(Factors factors, Object thisObj)
	{
		this.factors = factors;
		this.set("this", thisObj);
	}
	
	public void set(String name, Object value)
	{
		map.put(name, value);
	}

	public Object get(String name)
	{
		if (map.containsKey(name))
			return map.get(name);
		
		return factors.get(name);
	}
}
