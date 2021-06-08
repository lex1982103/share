package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;

import java.util.ArrayList;
import java.util.Arrays;

public class FunctionList implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		return Arrays.asList(v);
	}
}
