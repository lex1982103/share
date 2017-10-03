package lerrain.tool.formula.aries.arithmetic;

import java.io.Serializable;
import java.math.BigDecimal;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;

public class Constant implements Formula, Serializable
{
	private static final long serialVersionUID = 1L;
	
	Object value;
	
	public Constant(Object value)
	{
		this.value = value;
	}

	public static Constant constantOf(String vStr)
	{
		Object v = null;
		
		if (vStr == null)
			v = null;
		else if (vStr.startsWith("\'"))
			v = vStr.substring(1, vStr.length() - 1);
		else if (vStr.startsWith("0x"))
			v = new BigDecimal(Long.valueOf(vStr.substring(2), 16).longValue());
		else if (vStr.indexOf(".") >= 0)
			v = new Double(vStr);
		else
			v = new Integer(vStr);
		
		return new Constant(v);
	}

	public Object run(Factors getter)
	{
		return value;
	}
	
	public String toString()
	{
		return value == null ? null : value.toString();
	}
}