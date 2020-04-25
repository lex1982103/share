package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Function;
import lerrain.tool.script.FunctionInstable;
import lerrain.tool.script.CompileListener;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;
import lerrain.tool.script.warlock.function.FunctionTry;

import java.util.List;

public class ArithmeticParentheses extends Code
{
	Code left, prt;
	
	public ArithmeticParentheses(Words ws, int i)
	{
		super(ws, i);

		int l = i;
		int r = Syntax.findRightBrace(ws, l + 1);
		
//		if (l != 0 || r != ws.size() - 1)
//			throw new SyntaxException("小括号两侧有无法处理的代码");
//
//		if (l + 1 == r)
//			throw new SyntaxException("小括号运算内部不能为空");

		left = Expression.expressionOf(ws.cut(0, l));
		prt = Expression.expressionOf(ws.cut(l + 1, r));

		if (left != null && Script.compileListener != null)
			Script.compileListener.compile(CompileListener.FUNCTION, left);
	}

	public Object run(Factors factors)
	{
		try
		{
			if (left != null)
			{
				Object res = left.run(factors);

				if (!(res instanceof Function))
				{
					if (res instanceof Formula)
						return ((Formula) res).run(factors);
					else
						return res;
				}

				Function val = (Function) res;

				if (val instanceof FunctionTry) //这种方式的try已经作废，虽然还支持，但以后不要使用
				{
					Object ex = null;

					List<Code> codes = ((ArithmeticComma)prt).codes;
					for (int i = 0; i < codes.size() - 1; i++)
					{
						try
						{
							return codes.get(i).run(factors);
						}
						catch (Exception e)
						{
//							if (e instanceof ScriptRuntimeException)
//							{
//								if ("interrupt".equals(((ScriptRuntimeException)e).getExceptionCode())) //线程强制中断，不拦截
//									throw e;
//							}

							if (Thread.currentThread().isInterrupted()) //线程强制中断，不拦截
								throw new ScriptRuntimeException(this, factors, "interrupted");

							ex = e;

							//这里没必要处理Interrupt，break/continue直接被循环拦截，return不存在抛到try的可能
						}
					}

					Object x = codes.get(codes.size() - 1).run(factors);
					if (x instanceof Function)
					{
						Stack st = new Stack(factors);
						return ((Function)x).run(new Object[]{ex}, st);
					}

					return x;
				}

				Object[] params = null;

				if (prt != null)
				{
					Object r = prt.run(factors);
					if (r instanceof Wrap)
						params = ((Wrap) r).toArray();
					else
						params = new Object[]{r};
				}

				if (val instanceof FunctionInstable && Script.playbackListener != null && Script.playbackListener.isDebugging())
					return Script.playbackListener.popRecordHistory(((FunctionInstable)val).getRecordName());

				if (factors instanceof Stack)
					((Stack)factors).setCode(this);

				return val.run(params, factors);
			}
			else
			{
				return prt.run(factors);
			}
		}
		catch (Exception e)
		{
			throw new ScriptRuntimeException(this, factors, e);
		}
	}

	public String toText(String space, boolean line)
	{
		return (left == null ? "" : left.toText("", line)) + "(" + (prt == null ? "" : prt.toText("", line)) + ")";
	}
}
