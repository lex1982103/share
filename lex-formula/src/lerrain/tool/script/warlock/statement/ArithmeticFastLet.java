package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Script;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.AutoCodeConstant;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.WriteVariable;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticFastLet extends Arithmetic2Elements implements WriteVariable
{
	public ArithmeticFastLet(Words ws, int i)
	{
		super(ws, i);

		if (!(l instanceof Reference))
			throw new SyntaxException("被赋值一方必须是一个引用 - " + ws);
	}

	public Object run(Factors factors)
	{
		AutoCodeConstant acc = new AutoCodeConstant(r, factors);
		((Reference)l).let(factors, acc);

		if (Script.EXECUTOR != null)
			Script.EXECUTOR.submit(acc);

		return null;
	}

	@Override
	public boolean isFixed(int mode)
	{
		return false;
	}

	public String toText(String space, boolean line)
	{
		return l.toText("", line) + " = " + r.toText("", line);
	}

	@Override
	public Reference getReference()
	{
		return (Reference)l;
	}
}
