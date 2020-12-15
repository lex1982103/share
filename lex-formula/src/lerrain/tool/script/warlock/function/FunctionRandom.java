package lerrain.tool.script.warlock.function;

import java.util.Random;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.FunctionInstable;

public class FunctionRandom implements Function, FunctionInstable
{
	static Random random = new Random();
	
	public Object run(Object[] v, Factors factors)
	{
		if (v == null || v.length == 0)
			return random.nextInt();
		
		if (v.length == 1)
			return random.nextInt(Value.intOf(v[0]));
		
		throw new RuntimeException("错误的random运算");
	}

	public String toString()
	{
		return "function/random";
	}

	@Override
	public String getRecordName()
	{
		return "function/random";
	}
}
