package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

import java.io.PrintStream;

public class FunctionPrint implements Function
{
	public static PrintStream OUTPUT = System.out;

	public Object run(Object[] v, Factors factors)
	{
		if (OUTPUT != null)
		{
			for (int i = 0; i < v.length; i++)
			{
				if (i != 0)
					OUTPUT.print(", ");

				OUTPUT.print(v[i]);
			}
			OUTPUT.println();
		}
		
		return null;
	}
}
