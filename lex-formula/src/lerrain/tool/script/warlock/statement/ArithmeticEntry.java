package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticEntry extends Code
{
	String key;
	Code v;

	public ArithmeticEntry(Words ws, int i)
	{
		super(ws, i);

		key = ws.getWord(i - 1);
		v = Expression.expressionOf(ws.cut(i + 1));
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {v};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			v = code;
	}

	@Override
	public boolean isFixed()
	{
		return v.isFixed();
	}

	public String getKey()
	{
		return key;
	}

	public Object run(Factors factors)
	{
		return this;
	}
	
	public String toText(String space, boolean line)
	{
		return (line ? space : "") + key + " : " + v.toText(space, line);
	}
}
