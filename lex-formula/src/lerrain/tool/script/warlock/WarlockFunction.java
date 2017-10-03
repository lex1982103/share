package lerrain.tool.script.warlock;

import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.script.warlock.statement.ArithmeticComma;

/**
 * @deprecated 
 */
public class WarlockFunction implements Formula
{
	String[] vars;
	
	public WarlockFunction(String[] vars)
	{
		this.vars = vars;
	}

	public Object run(Factors factors)
	{
		return null;
	}
	
	public static class Parameters
	{
		Factors factors;
		
		Object[] values;
		
		List parameters;
		
		public Parameters(Formula formula, Factors factors)
		{
			this.factors = factors;
			
			parameters = findParameters(formula, new ArrayList(), false);
			
			values = new Object[size()];
		}
		
		private List findParameters(Formula formula, List list, boolean left)
		{
			if (formula instanceof ArithmeticComma)
			{
				ArithmeticComma ac = (ArithmeticComma)formula;
				
				findParameters(ac.left(), list, true);
				findParameters(ac.right(), list, false);
			}
			else
			{
				if (left)
					list.add(0, formula);
				else
					list.add(formula);
			}
			
			return list;
		}
		
		public int size()
		{
			return parameters.size();
		}
		
		public Object getValue(int index)
		{
			if (values[index] == null)
			{
				values[index] = ((Formula)parameters.get(index)).run(factors);
			}
			
			return values[index];
		}
		
		public boolean getBoolean(int index)
		{
			return ((Boolean)getValue(index)).booleanValue();
		}
	}
}
