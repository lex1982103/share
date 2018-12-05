package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.FunctionCloneable;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;

public class ArithmeticFunctionDim extends Code implements Function, FunctionCloneable
{
	String[] param;

	Script content;

	String functionId;

	public ArithmeticFunctionDim(Words ws, int i)
	{
		super(ws, i);

		int l = i + 1;
		int r = Syntax.findRightBrace(ws, l + 1);

		int num = (r - l) / 2;
		param = new String[num];

		Words w = ws.cut(l + 1, r);
		for (int m = 0; m < num; m++)
		{
			param[m] = w.getWord(m * 2);
		}

		l = r + 1;
		r = Syntax.findRightBrace(ws, l + 1);

		Words words = ws.cut(l + 1, r);
		content = new Script(words);

		if (Script.SERIALIZABLE)
		{
			functionId = words.hash();
			Script.FUNCTIONS.put(functionId, this);
		}
	}

	public Object run(Factors factors)
	{
		return this;
	}

	public String toText(String space, boolean line)
	{
		return toString();
	}

	public String toString()
	{
		String str = null;
		if (param != null) for (String p : param)
			str = str == null ? p : str + "," + p;

		return "function(" + str + ")";
	}

	public Object run(Object[] v, Factors p)
	{
		Stack stack = new Stack(p);

		for (int i = 0; i < param.length && i < v.length; i++)
		{
			stack.declare(param[i]);
			stack.set(param[i], v[i]);
		}
		
		Object result = content.run(stack);
		
		if (Interrupt.isMatch(result, Interrupt.RETURN))
			result = Interrupt.getValue(result);
		
		return result;
	}

	private Object writeReplace()
	{
		return new SerializedFunction(functionId);
	}

	@Override
	public String clone()
	{
		return functionId;
	}

	public static class SerializedFunction implements Serializable
	{
		String functionId;

		public SerializedFunction(String functionId)
		{
			this.functionId = functionId;
		}

		private Object readResolve()
		{
			return Script.FUNCTIONS.get(functionId);
		}
	}
}
