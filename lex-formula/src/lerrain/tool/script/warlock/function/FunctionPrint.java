package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;

import java.io.PrintStream;

public class FunctionPrint implements OptimizedFunction
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

	@Override
	public boolean isFixed(int mode, Code p)
	{
		return false;
	}
}
