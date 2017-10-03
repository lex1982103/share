package lerrain.tool.formula.aries.arithmetic;

import java.math.BigDecimal;
import java.util.List;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class FunctionRound extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public FunctionRound()
	{
		super.addSign("round");
		super.setPriority(1000);
	}
	
	public FunctionRound(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		BigDecimal r = null;

		int size = paramList.size();
		if (size == 1)
		{
			Value key = Value.valueOf((Formula)paramList.get(0), getter);
			r = key.toDecimal().setScale(0, BigDecimal.ROUND_HALF_UP);
		}
		else if (size == 2)
		{
			Value key = Value.valueOf((Formula)paramList.get(0), getter);
			int scale = Value.valueOf((Formula)paramList.get(1), getter).intValue();
			r = key.toDecimal().setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		
		return r;
	}
}
