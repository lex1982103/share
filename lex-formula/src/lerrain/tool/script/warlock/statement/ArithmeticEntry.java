package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticEntry extends ArithmeticCode
{
	String key;
	Code v;

	public ArithmeticEntry(Words ws, int i)
	{
		super(ws, i);

		key = ws.getWord(i - 1);
		if (ws.getType(i - 1) == Words.STRING)
			key = key.substring(1, key.length() - 1);

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
	public boolean isFixed(int mode)
	{
		return v.isFixed(mode);
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
