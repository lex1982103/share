package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.FormulaAutoRun;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
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
		((Reference)l).let(factors, new AutoRun(r, factors));
		
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

	static class AutoRun implements FormulaAutoRun
	{
		boolean computed = false;

		Code r;
		Factors f;

		Object v;

		AutoRun(Code r, Factors f)
		{
			this.r = r;
			this.f = f;
		}

		@Override
		public synchronized Object run(Factors factors)
		{
			if (computed)
				return v;

			v = r.run(factors);
			computed = true;

			return v;
		}
	}
}
