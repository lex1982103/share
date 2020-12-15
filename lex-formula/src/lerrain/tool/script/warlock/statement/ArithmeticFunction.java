package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.CompileListener;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Fixed;
import lerrain.tool.script.warlock.analyse.Words;
import lerrain.tool.script.warlock.function.*;

/**
 * 
 * @author lerrain
 * 
 * 2014-08-11
 * 2014-08-10提到的这种形式，用?:来处理
 *
 * 2014-08-10
 * 由逗号分割开的多个表达式，通常是用作函数或数组的参数，并不是每个都需要计算的
 * 所以直接打包返回，处理的部分根据需要计算全部或者部分
 * 如：x[i] = case(i>0,x[i-1]+y,y); 
 * 如果每个都计算，那么这个函数是没办法运行的。
 * 
 */
public class ArithmeticFunction extends Code
{
	String name;
	
	Function fs;

	Boolean fixed;

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
	}
	
	public ArithmeticFunction(Words ws, int i)
	{
		super(ws, i);

		if (i > 0)
			throw new SyntaxException("不是一个有效的函数语法 - " + ws);
		
		name = ws.getWord(i);
		
		//内置函数，参数也直接运算
		fs = (Function)Script.FUNCTIONS.get(name);

		if (Script.compileListener != null) //left!=null才是函数
			Script.compileListener.compile(CompileListener.FUNCTION_INSTANT, this);
	}

	public String getName()
	{
		return name;
	}

	public String toString()
	{
		return name + "(...) {...}";
	}

	public Object run(Factors factors)
	{
		Function f = fs;
		
		Object v = factors.get(name);
		if (v instanceof Function)
			f = (Function)v;
		
		if (f == null)
		{
			if (v == null)
				throw new ScriptRuntimeException(this, factors, "未找到函数 - " + name);
			else
				throw new ScriptRuntimeException(this, factors, "该变量对应的值不是函数体 - " + name + " is " + v.getClass() + ": " + v.toString());
		}

		return f;
	}

	@Override
	public boolean isFixed()
	{
		if (fixed != null)
			return fixed;

		//如果值被改不是预设的fs而是factors里面取出的f，那么前面一定有可变的导致fixed是false，这里无论返回的是否，都不会对整体的code段产生判断错误
		if (fs instanceof Fixed)
			return ((Fixed) fs).isFixed();

		return false;
	}

	public void setFixed(boolean fixed)
	{
		this.fixed = fixed;
	}

	public String toText(String space, boolean line)
	{
		return name;
	}
}
