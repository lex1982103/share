package lerrain.tool.formula.aries.arithmetic;

import java.io.Serializable;

import lerrain.tool.formula.Function;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;

public class FunctionAppend implements Formula, Serializable
{
	private static final long serialVersionUID = 1L;
	
	String name;
	
	Formula[] params;
	
	public FunctionAppend(String name, Formula[] params)
	{
		this.name = name;
		this.params = params;
	}

	public Object run(Factors p)
	{
		Function ff = (Function)p.get(name);
		if (ff == null)
			return null;
		
		Object[] v = null;
		if (params != null)
		{
			v = new Object[params.length];
			for (int i = 0; i < params.length; i++)
			{
				v[i] = params[i].run(p);
			}
		}
		
		return ff.run(v, p);
	}
}
