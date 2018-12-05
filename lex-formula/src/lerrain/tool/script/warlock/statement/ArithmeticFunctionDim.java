package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticFunctionDim extends Code implements Function
{
	String[] param;

	transient Script content;

	Words copy; //序列化或复制使用，content很大，恢复的时候用copy恢复，但内部的content最初的已经不是同一个对象，里面的格式和引用都不同了
	
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
		content = new Script(words); //这个要用来debug定位，不能用最小化的words副本

		//这个用来序列化stack的时候恢复
		copy = words.less();
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
		//复制或序列化出来的content为null
		if (content == null) synchronized(copy)
		{
			//锁加在外面影响效率，加在里面有很小的可能重复执行，这里在判断一下，这个被执行到的情况很少
			if (content == null)
			{
				content = new Script(copy);
			}
		}

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
}
