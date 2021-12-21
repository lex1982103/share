package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.CompileListener;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

//for循环用，虽然操作都放在for那边做了，但这些代码还涉及赋值之类的操作，fixed默认为false
public class ArithmeticColon extends Code
{
	Code[] v;

	String symbol;
	
	public ArithmeticColon(Words ws, int i)
	{
		super(ws, i);

		v = new Code[2];
		v[0] = Expression.expressionOf(ws.cut(0, i));
		v[1] = Expression.expressionOf(ws.cut(i + 1));

		symbol = ws.getWord(i);
	}

	public String getSymbol()
	{
		return symbol;
	}

	public Object run(Factors factors)
	{
		return v;
	}
	
	public String toText(String space, boolean line)
	{
		return (line ? space : "") + v[0].toText(space, line) + " " + symbol + " " + v[1].toText(space, line);
	}

	@Override
	public Code[] getChildren()
	{
		return v;
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		v[i] = code;
	}
}
