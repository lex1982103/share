package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticNot extends Arithmetic
{
	Code r;
	
	public ArithmeticNot(Words ws, int i)
	{
		super(ws, i);

		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		Object v = r.run(factors);

		if (v == null)
			return Boolean.TRUE;
		if (v instanceof Boolean)
			return Boolean.valueOf(!((Boolean)v).booleanValue());
		if (v instanceof Number)
			return Boolean.valueOf(((Number)v).intValue() != 0);

		return Boolean.FALSE;

//		throw new ScriptRuntimeException(this, factors, "NOT逻辑运算，要求值为boolean类型");
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {r};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			r = code;
	}

	@Override
	public boolean isFixed(int mode)
	{
		return r.isFixed(mode);
	}

	public String toText(String space, boolean line)
	{
		return "NOT " + r.toText("", line);
	}
}
