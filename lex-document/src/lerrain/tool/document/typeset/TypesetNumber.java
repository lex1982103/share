package lerrain.tool.document.typeset;

import java.io.Serializable;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class TypesetNumber implements Serializable
{
	private static final long serialVersionUID = 1L;

	int value;
	
	Formula formula;
	
	public TypesetNumber()
	{
	}
	
	public TypesetNumber(int value)
	{
		this.value = value;
	}
	
	public TypesetNumber(Formula formula)
	{
		this.formula = formula;
	}
	
	public static TypesetNumber numberOf(String str)
	{
		if (str == null)
			return null;
		
		TypesetNumber r = null;
		
		try
		{
			int value = Integer.parseInt(str);
			r = new TypesetNumber(value);
		}
		catch (Exception e)
		{
			try
			{
				Formula formula = TypesetUtil.formulaOf(str);
				r = new TypesetNumber(formula);
			} 
			catch (Exception e1)
			{
			}
		}
		
		return r;
	}
	
	private boolean isFormula()
	{
		return formula != null;
	}

	public int value(Factors varSet)
	{
		if (isFormula())
			return Value.intOf(formula, varSet);
		else
			return value;
	}

	public String toString()
	{
		return formula == null ? value + "" : formula.toString();
	}
}
