package lerrain.tool.script.warlock;

import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.script.warlock.statement.ArithmeticComma;

@Deprecated
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

				//getChildren会生成新对象，影响效率，但这个无用了就不改了
				findParameters(ac.getChildren()[0], list, true);
				findParameters(ac.getChildren()[1], list, false);
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
