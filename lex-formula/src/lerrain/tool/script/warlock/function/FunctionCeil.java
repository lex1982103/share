package lerrain.tool.script.warlock.function;

import java.math.BigDecimal;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;

public class FunctionCeil implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
			return Integer.valueOf((int)Math.ceil(Value.valueOf(v[0]).doubleValue()));
		
		if (v.length == 2)
		{
			int scale = Value.valueOf(v[1]).intValue();
			
			BigDecimal d = Value.valueOf(v[0]).toDecimal();
			return d.setScale(scale, BigDecimal.ROUND_CEILING);
		}
		
		throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException("错误的ceil运算");
	}
}
