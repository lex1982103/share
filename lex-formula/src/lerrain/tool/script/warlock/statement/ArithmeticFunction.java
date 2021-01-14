package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Function;
import lerrain.tool.script.*;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Optimized;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;
import lerrain.tool.script.warlock.function.FunctionTry;

import java.util.Arrays;
import java.util.List;

public class ArithmeticFunction extends Code
{
	protected Code body, prt;
	protected Function function;

	public ArithmeticFunction(Words ws, int i)
	{
		super(ws, i);

		int r = Syntax.findRightBrace(ws, i + 1);
		body = Expression.expressionOf(ws.cut(0, i));
		prt = Expression.expressionOf(ws.cut(i + 1, r));

//		if (Script.compileListener != null) //left!=null才是函数
//			Script.compileListener.compile(CompileListener.FUNCTION, this);
	}

	/**
	 * 把code绑定到函数体，不再从环境中获取
	 * @param body
	 * @param prt
	 */
	public void bind(Code body, Code prt)
	{
		this.body = body;
		this.prt = prt;
	}

	public void bind(Code body)
	{
		this.body = body;
	}

	public void bind(Function function, Code prt)
	{
		this.function = function;
		this.prt = prt;
	}

	public void bind(Function function)
	{
		this.function = function;
	}

	public Code getBody()
	{
		return body;
	}

	public Code getParam()
	{
		return prt;
	}

	public Object run(Factors factors)
	{
		try
		{
			Function val = function;

			if (val == null)
			{
				Object res = body.run(factors);

				if (!(res instanceof Function))
				{
					if (res instanceof Formula)
						return ((Formula) res).run(factors);
					else
						return res;
				}

				val = (Function) res;
			}

			if (val instanceof FunctionTry) //这种方式的try已经作废，虽然还支持，但以后不要使用
			{
				Object ex = null;

				List<Code> codes = ((ArithmeticComma) prt).codes;
				for (int i = 0; i < codes.size() - 1; i++)
				{
					try
					{
						return codes.get(i).run(factors);
					}
					catch (Exception e)
					{
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

			if (val instanceof ParamFunction)
			{
				params = ((ParamFunction)val).param(prt);
			}
			else if (prt != null)
			{
				Object r = prt.run(factors);
				if (r instanceof Wrap)
					params = ((Wrap) r).toArray();
				else
					params = new Object[]{r};
			}

			Object prepare = null;
			if (val instanceof FunctionInstable && Script.playbackListener != null)
			{
				String recordName = ((FunctionInstable)val).getRecordName();

				if (Script.playbackListener.isDebugging())
					return Script.playbackListener.popRecordHistory(recordName);

				prepare = Script.playbackListener.prepare(recordName);
			}

			if (factors instanceof Stack)
				((Stack)factors).setCode(this);

			try
			{
				Object r = val.run(params, factors);

				if (prepare != null)
					Script.playbackListener.success(prepare, r);

				return r;
			}
			catch (Exception e)
			{
				if (prepare != null)
					Script.playbackListener.fail(prepare, e);

				throw e;
			}
		}
		catch (Exception e)
		{
			throw new ScriptRuntimeException(this, factors, e);
		}
	}

	@Override
	public boolean isFixed()
	{
		if (function instanceof Optimized)
			return ((Optimized)function).isFixed(prt);

		return body.isFixed(prt); //函数时是否固定，无法预知，需要自己设
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {body, prt};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			body = code;
		else if (i == 1)
			prt = code;
	}

	public String toText(String space, boolean line)
	{
		return (body == null ? "" : body.toText("", line)) + "(" + (prt == null ? "" : prt.toText("", line)) + ")";
	}
}
