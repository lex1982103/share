package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticArrayDefine extends Arithmetic
{
	int type;

	Code a;

	public ArithmeticArrayDefine(Words ws, int i, Code a, int type)
	{
		super(ws, i);

		this.type = type;
		this.a = a;
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[]{a};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		a = code;
	}

	public Object run(Factors factors)
	{
		if (type == 0) // [a, b, c, ...]
			return Wrap.arrayOf(a, factors);
		else if (type == 1) // double[a, b, c, ...]
			return Wrap.doubleArrayOf(a, factors);
		else if (type == 2) // @[a, b, c, ...]
			return Wrap.wrapOf(a, factors).toList();
		else
			throw new ScriptRuntimeException(this, factors, "无法识别的数组定义类型");
	}

	@Override
	public boolean isFixed(int mode)
	{
		return a == null || a.isFixed(mode);
	}

	public String toText(String space, boolean line)
	{
		return "[" + (a == null ? "" : a.toText("", line)) + "]";
	}
}
