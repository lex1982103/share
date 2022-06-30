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
import lerrain.tool.script.warlock.function.*;

import java.util.List;

public class ArithmeticFunction extends Arithmetic
{
	static
	{
		Script.FUNCTIONS.put("try", new FunctionTry());
		Script.FUNCTIONS.put("case", new FunctionCase());
		Script.FUNCTIONS.put("round", new FunctionRound());
		Script.FUNCTIONS.put("ceil", new FunctionCeil());
		Script.FUNCTIONS.put("floor", new FunctionFloor());
		Script.FUNCTIONS.put("format", new FunctionFormat());
		Script.FUNCTIONS.put("array", new FunctionArray());
		Script.FUNCTIONS.put("min", new FunctionMin());
		Script.FUNCTIONS.put("max", new FunctionMax());
		Script.FUNCTIONS.put("abs", new FunctionAbs());
		Script.FUNCTIONS.put("pow", new FunctionPow());
		Script.FUNCTIONS.put("size", new FunctionSize());
		Script.FUNCTIONS.put("call", new FunctionCall());
		Script.FUNCTIONS.put("print", new FunctionPrint());
		Script.FUNCTIONS.put("fill", new FunctionFill());
		Script.FUNCTIONS.put("sum", new FunctionSum());
		Script.FUNCTIONS.put("val", new FunctionVal());
		Script.FUNCTIONS.put("long", new FunctionLong());
		Script.FUNCTIONS.put("int", new FunctionInteger());
		Script.FUNCTIONS.put("find", new FunctionFind());
		Script.FUNCTIONS.put("index", new FunctionIndex());
		Script.FUNCTIONS.put("str", new FunctionStr());
		Script.FUNCTIONS.put("str_begin", new FunctionStrBegin());
		Script.FUNCTIONS.put("str_end", new FunctionStrEnd());
		Script.FUNCTIONS.put("str_index", new FunctionStrIndex());
		Script.FUNCTIONS.put("str_split", new FunctionStrSplit());
		Script.FUNCTIONS.put("str_len", new FunctionStrLen());
		Script.FUNCTIONS.put("str_upper", new FunctionStrUpper());
		Script.FUNCTIONS.put("str_lower", new FunctionStrLower());
		Script.FUNCTIONS.put("str_right", new FunctionStrRight());
		Script.FUNCTIONS.put("str_trim", new FunctionStrTrim());
		Script.FUNCTIONS.put("str_replace", new FunctionStrReplace());
		Script.FUNCTIONS.put("random", new FunctionRandom());
		Script.FUNCTIONS.put("post", new FunctionPost());
		Script.FUNCTIONS.put("time", new FunctionTime());
		Script.FUNCTIONS.put("timestr", new FunctionTimeStr());
		Script.FUNCTIONS.put("datestr", new FunctionDateStr());
		Script.FUNCTIONS.put("num", new FunctionNum());
		Script.FUNCTIONS.put("sleep", new FunctionSleep());
		Script.FUNCTIONS.put("reflex", new FunctionReflex());
		Script.FUNCTIONS.put("type", new FunctionType());
		Script.FUNCTIONS.put("trim", new FunctionTrim());
		Script.FUNCTIONS.put("match", new FunctionMatch());
		Script.FUNCTIONS.put("copy", new FunctionCopy());
		Script.FUNCTIONS.put("contains", new FunctionContains());
		Script.FUNCTIONS.put("nvl", new FunctionNvl());
		Script.FUNCTIONS.put("list", new FunctionList());
		Script.FUNCTIONS.put("notnull", new FunctionNotNull());
		Script.FUNCTIONS.put("has", new FunctionHas());
	}

	protected Code body, prt;
	protected Function function, innerFunction;

	int fixed = 0;

	public ArithmeticFunction(Words ws, int i)
	{
		super(ws, i);

		int r = Syntax.findRightBrace(ws, i + 1);
		body = Expression.expressionOf(ws.cut(0, i));
		prt = Expression.expressionOf(ws.cut(i + 1, r));

		if (body instanceof Variable) //内置函数，参数也直接运算
			innerFunction = (Function)Script.FUNCTIONS.get(((Variable) body).getVarName());

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
		this.function = null;
		this.prt = prt;
	}

	public void bind(Code body)
	{
		this.bind(body, null);
	}

	public void bind(Function function, Code prt)
	{
		this.function = function;
		this.body = null;
		this.prt = prt;
	}

	public void bind(Function function)
	{
		this.bind(function, null);
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
		super.debug(factors);

		try
		{
			Function val = function;

			if (val == null)
			{
				Object res = body.run(factors);

				if (res == null)
					res = innerFunction;

				if (res instanceof Function)
					val = (Function) res;
				else if (res instanceof Formula)  //考虑去除
					return ((Formula) res).run(factors);
				else
					throw new ScriptRuntimeException(body + "不是函数体");
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
					Stack st = Stack.newStack(factors);
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
	public boolean isFixed(int mode)
	{
		if ((fixed & mode) != 0)
			return prt == null || prt.isFixed(mode);

		if (function != null)
		{
			if (function instanceof OptimizedFunction)
				return ((OptimizedFunction) function).isFixed(mode, prt);
			if (function instanceof Optimized)
				return ((Optimized) function).isFixed(mode) && (prt == null || prt.isFixed(mode));

			return prt == null || prt.isFixed(mode); //函数体默认可以优化
		}

		return body.isFixed(mode) && (prt == null || prt.isFixed(mode)); //函数时是否固定，无法预知，需要自己设
	}

	public void setFixed(int fixed)
	{
		this.fixed = fixed;
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
