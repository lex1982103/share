package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.analyse.Words;

//for循环用，虽然操作都放在for那边做了，但这些代码还涉及赋值之类的操作，fixed默认为false
public class ArithmeticColon extends ArithmeticCode
{
	public ArithmeticColon(Words ws, int i)
	{
		super(ws, i);
	}

	public Object run(Factors factors)
	{
		return v;
	}
}
