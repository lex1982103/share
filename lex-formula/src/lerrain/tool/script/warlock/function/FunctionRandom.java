package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.FunctionInstable;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Optimized;

import java.util.Random;

public class FunctionRandom implements OptimizedFunction, FunctionInstable
{
	static Random random = new Random();
	
	public Object run(Object[] v, Factors factors)
	{
		if (v == null || v.length == 0)
			return random.nextInt();
		
		if (v.length == 1)
			return random.nextInt(Value.intOf(v[0]));
		
		throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException("错误的random运算");
	}

	@Override
	public boolean isFixed(int mode, Code p)
	{
		if ((mode & Optimized.RANDOM) != 0)
			return true;

		return false;
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
