package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.FunctionCloneable;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.Optimized;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

import java.io.Serializable;

public class ArithmeticFunctionDim extends Arithmetic
{
	String[] param;

	Script content;

	//根据整段脚本的md5值，加上函数体所在的字符开始与结束位置，生成的唯一码。只要本机编译过这段脚本，就可以被恢复。但外部传入本机脚本的自定义函数，恢复行为如果在其他机器，那会恢复不出来
	//2022-03-07修改为整段函数的脚本作为key
	String functionId;

	ScriptFunction instant;

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

		if (Script.SERIALIZABLE) //序列化自定义函数的时候的特殊处理
		{
//			functionId = words.getWordsKey();
			functionId = words.getCurrentScript();
			Script.FUNCTIONS.put(functionId, this);
		}

		instant = new ScriptFunction();
	}

	public Code markBreakPoint(int pos)
	{
		if (content.isPointOn(pos))
			return content.markBreakPoint(pos);

		return super.markBreakPoint(pos);
	}

	public void clearBreakPoints()
	{
		content.clearBreakPoints();
		super.clearBreakPoints();
	}

	@Override
	public boolean isFixed(int mode)
	{
		return true;
	}

	public Object run(Factors factors)
	{
		return instant;
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

	private Object writeReplace()
	{
		return new SerializedFunction(functionId);
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
			Object func = Script.FUNCTIONS.get(functionId);
			if (func == null)
			{
				Words ws = Words.wordsOf(functionId);
				func = new ArithmeticFunctionDim(ws, 0);
				Script.FUNCTIONS.put(functionId, func);
			}

//			if (func == null && Stack.runtimeListener != null)
//			{
//				Stack.runtimeListener.onEvent(Stack.EVENT_DYNAMIC_FUNCTION_NOT_FOUND, functionId);
//				func = Script.FUNCTIONS.get(functionId); //处理后重新获取
//			}
//
//			if (func == null)
//				throw new RuntimeException(functionId + " not found!");

			return func;
		}
	}

	class ScriptFunction implements Function, FunctionCloneable, Optimized
	{
		public Object run(Object[] v, Factors p)
		{
			Stack stack = Stack.newStack(p);

			for (int i = 0; i < param.length && i < v.length; i++)
			{
				stack.declare(param[i]);
				stack.set(param[i], v[i]);
			}

			try
			{
				return content.run(stack);
			}
			catch (Interrupt.Return e)
			{
				return e.getValue();
			}
			catch (Interrupt e)
			{
				throw new ScriptRuntimeException(ArithmeticFunctionDim.this, p, "can't break/continue a function, use return");
			}
		}

		@Override
		public boolean isFixed(int mode)
		{
			return content.isFixed(mode);
		}

		@Override
		public String clone()
		{
			return functionId;
		}

	}
}
