package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.WriteVariable;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticLet extends Arithmetic2Elements implements WriteVariable
{
	public ArithmeticLet(Words ws, int i)
	{
		super(ws, i);

		if (!(l instanceof Reference))
			throw new SyntaxException("被赋值一方必须是一个引用 - " + ws);
	}

	public Object run(Factors factors)
	{
		Object v = r.run(factors);
		((Reference)l).let(factors, v);
		
		return v;
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
