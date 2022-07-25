package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.SyntaxException;
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
@Deprecated
public class ArithmeticFunctionBody extends Arithmetic
{
	String name;
	
	Function fs;

	public ArithmeticFunctionBody(Words ws, int i)
	{
		super(ws, i);

		if (i > 0)
			throw new SyntaxException(ws, i, "不是一个有效的函数语法 - " + ws);
		
		name = ws.getWord(i);
		
		//内置函数，参数也直接运算
		fs = (Function)Script.FUNCTIONS.get(name);

//		if (Script.compileListener != null) //left!=null才是函数
//			Script.compileListener.compile(CompileListener.FUNCTION_INSTANT, this);
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
	public boolean isFixed(int mode)
	{
		//如果值被改不是预设的fs而是factors里面取出的f，那么前面一定有可变的导致fixed是false，这里无论返回的是否，都不会对整体的code段产生判断错误
//		if (fs instanceof Optimized)
//			return ((Optimized) fs).isFixed(mode);

		//其实prt==null的时候，函数结果很可能是不确定的，比如random() time()，反而是结果确定的几乎没有，因为毫无意义，但这个规则一般函数里面自己直接设定了，这里还是按照基本的参数确定就确定
		return false; //注意time random等函数要额外处理
	}

	public String toText(String space, boolean line)
	{
		return name;
	}
}
