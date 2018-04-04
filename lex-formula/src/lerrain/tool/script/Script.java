/**
 * 语法
 * 
 * 整体语法类似java和javascript
 * 
 * 定义变量 var x; 或 var x = 1; 
 * 变量也可以不定义直接使用。
 * 
 * for、while、if、continue、break、return语法等同C语言。
 * 
 * point运算
 * xxx.yyy() 访问xxx对象的yyy方法。
 * xxx.yyy 访问xxx对象，以yyy作为key值，然后根据对象的类型操作
 * 如：xxx为map那么是xxx.get("yyy")，xxx为factors那么是xxx.get("yyy")，xxx为一般对象那么是xxx的yyy成员变量
 * 
 * yyy() 访问自定义的或系统自带的yyy函数
 * 
 * 函数不可以同名
 * 即使参数数量或类型不一样也不可以，因为函数在这里就是变量，其值实际是函数指针，也就是函数能赋值给任意变量
 * x = func(abc) { return abc*abc; }
 * y = x;
 * z = y(100);  
 * 
 * 赋值也是一种运算，也有返回值，返回的值就是赋值的值，比如：x = y = 5
 * 
 * 函数可以返回多个值
 * x = func(abc) { return abc*abc, abc+abc; }
 * y, z = x(100);
 * 如果只写一个，那么只取第一个值
 * y = x(100); //y的值为10000
 * 
 * 如果需要return的地方没有写return，那么相当于在最后一行被执行到的代码前面加上return
 * 
 * 关于数组、函数、方法的区分
 * xxx(a,b) 函数
 * y.xxx(a,b) 可能是y对象的xxx方法，也可能是y.xxx表示一个函数指针。先以y的值类型来定，如果y是map或者factors那么看y.xxx的值类型
 * xxx[a][b] 数组
 * xxx[a,b] 函数
 * xxx[a] 数组或函数，以xxx的值类型来定
 * y.xxx[a][b] 数组
 * y.xxx[a,b] 函数
 * y.xxx[a] 数组或函数，以y.xxx的值类型来定
 *   
 * @author lerrain
 *
 */
package lerrain.tool.script;

import java.util.*;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.CodeImpl;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

/**
 * <p>脚本对象</p>
 * <p>一段程序文本会被翻译为该对象</p>
 * <b>主要的关键字：for,while,if,else,return,continue,break,var,throw,new,function,null</b>
 * <p>var i = 0;</p>
 * <p>var testFunction = function(x, y) { return x + y; }</p>
 * <p>没有列出的与java的相似</p>
 * <p></p>
 * <b>运算符：expN代表一个表达式(变量和常量都属于表达式)，varN代表一个变量，conN代表一个常量</b>
 * <p>exp1 + exp2</p>
 * <p>exp1 - exp2</p>
 * <p>exp1 * exp2</p>
 * <p>exp1 / exp2</p>
 * <p>exp1 % exp2 或 exp1 mod exp2</p>
 * <p>!exp1 或 not exp1</p>
 * <p>var1++</p>
 * <p>var1--</p>
 * <p>var1 = exp1</p>
 * <p>var1 += exp1</p>
 * <p>var1 -= exp1</p>
 * <p>var1 *= exp1</p>
 * <p>var1 /= exp1</p>
 * <p>exp1 == exp2</p>
 * <p>exp1 != exp2</p>
 * <p>exp1 > exp2 或 exp1 gt exp2</p>
 * <p>exp1 >= exp2 或 exp1 ge exp2</p>
 * <p>exp1 < exp2 或 exp1 lt exp2</p>
 * <p>exp1 <= exp2 或 exp1 le exp2</p>
 * <p>exp1 and exp2</p>
 * <p>exp1 or exp2</p>
 * <p>[exp1, exp2, ..., expN]</p>
 * <p>exp1 ? exp2 : exp3</p>
 * <p></p>
 * <b>内置的函数：</b>
 * <p>try(5/0,5/2); //如果第一个表达式执行出现异常了，那么返回第二个表达式的值。</p>
 * <p>var strs = array('abc','def'); //array，strs的值是数组</p>
 * <p>case(xxx>0, 1, -1);</p>
 * <p>case(x, 1, 100, 2, 200, 300); //偶数个参数，最后一个参数为都不符合的情况下的值。</p>
 * <p>ceil(1.05);</p>
 * <p>var x = ceil(1.05, 1); //x=1.1f</p></p>
 * <p>floor(1.05);</p>
 * <p>var x = floor(1.05, 1); //x=1.0f</p></p>
 * <p>var x = max(1, 2, 3);</p>
 * <p>var x = min(1, 2, 3);</p>
 * <p>print(x);</p>
 * <p>round(1.05);</p>
 * <p>var x = round(1.05, 1); //x=1.1f;</p>
 * <p>var x = size(strs); //参照上面array的例子，x=2</p>
 * <p>str_begin('xxxxx', 'abc'); //java的startsWith</p>
 * <p>str_end('xxxxx', 'abc'); //java的endsWith</p>
 * <p>str_index('xxxxx', 'abc'); //java的indexOf</p>
 * <p></p>
 * @author lerrain
 */
public class Script extends CodeImpl
{
	/**
	 * <p>这是一种用原生计算代替高精度计算的方式，某些情形下，可以显著提升计算速度，在Script中设置即可。</p>
	 * <b>实际中，高精度计算的速度足以满足各种需求，别处的一个简单处理可能就要比这里慢上百倍，所以不要随便使用NATIVE模式</b>
	 * <p>
	 * <b>注意：</b>
	 * <p>1。在程序运行时，所有的	数字必须转化为对象，这时候频繁的转化可能会拖累速度（有可能比高精度计算还要慢）</p>
	 * <p>2。精度低，大数字运算时，小数的舍进可能会比较严重</p>
	 * <p>3。为了追求速度，省略了一些类型校验，类型不对时可能抛出对象转换错误等原生异常</p>
	 * </p>
	 * <p>仅适合复杂表达式的又不要求很高的小数精度的情况，表达式之间运算时基本不需要数字与对象的转化（注意所有带有赋值的运算都需要）</p>
	 * <p>由于表达式最终结果需要转化为对象用作返回结果，所以如果是脚本中表达式数量很多却不复杂，那么也不要使用这种方式。<p>
	 */
	public static final int NATIVE			= 1;

	//自定义函数，没加同步锁，初始化的时候加入，不要在执行的时候add
	public static final Map FUNCTIONS		= new HashMap();

	/**
	 * 高精度模式，java中实际使用BigDecimal来完成
	 */
	public static final int PRECISE			= 2;
	
	public static final int PRECISE_SCALE	= 10;

	public static boolean STACK_MESSAGE		= true;

	private static int mode = NATIVE;
	
	/**
	 * 如果是最外层，那么遇到return不能直接把Result(return)对象返回，需要把他的真实值返回。
	 * 1. 非最外层，直接上抛
	 * 2. 最外层的return，那么返回值
	 * 3. 最外层的continue break等，直接返回null
	 */
	boolean main = true;
	
	List codeList = new ArrayList();
	Code code;
	
	public Script(Words ws)
	{
		this(ws, false);
	}
	
	public Script(Words ws, boolean main)
	{
		super(ws);

		this.main = main;
		
		List line = Syntax.split(ws);
		if (line.size() == 1)
		{
			code = Syntax.sentenceOf((Words)line.get(0));
			codeList.add(code);
		}
		else
		{
			Iterator iter = line.iterator();
			while (iter.hasNext())
			{
				Code code = Syntax.sentenceOf((Words)iter.next());
				if (code != null) //单纯一个分号的语句会直接返回null
					codeList.add(code);
			}
		}
	}
	
	public int size()
	{
		return codeList.size();
	}
	
	public Code getSentence(int index)
	{
		return (Code)codeList.get(index);
	}

	/**
	 * 单行视为公式，多行视为脚本
	 * 脚本在运行的时候要起stack，中间可能存在变量定义等。
	 */
	public Object run(Factors factors)
	{
		Object r = null;
		
		if (code != null)
		{
			r = code.run(factors);
			return r instanceof Interrupt && main ? Interrupt.getValue(r) : r;
		}
		
		Stack stack = factors instanceof Stack ? (Stack)factors : new Stack(factors);
		
		Iterator iter = codeList.iterator();
		while (iter.hasNext())
		{
			Code f = (Code)iter.next();
			r = f.run(stack);
//			System.out.println("RUN: " + f.toText("") + "   ---   " + r);
			
			if (r instanceof Interrupt)
			{
				if (!main)
					return r;

				return ((Interrupt) r).getValue();

//				Interrupt interrupt = (Interrupt)r;
//				Object with = interrupt.getValue();
//
//				if (interrupt.getType() == Interrupt.THROW)
//					throw new RuntimeException(with == null ? null : with.toString());
//
//				return with;
			}
		}
		
		return r;
	}
	
	public String toText(String space)
	{
		StringBuffer buf = new StringBuffer();
		
		Iterator iter = codeList.iterator();
		while (iter.hasNext())
		{
			buf.append(space + ((Code)iter.next()).toText(space) + (iter.hasNext() ? "\n" : ""));
		}
		
		return buf.toString();
	}
	
	public String toString()
	{
		return toText("");
	}
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	public static Script scriptOf(String text)
	{
		if (text == null || "".equals(text.trim()))
			return null;

		return new Script(Words.wordsOf(null, text), true);
	}

	public static Script scriptOf(String name, String text)
	{
		if (text == null || "".equals(text.trim()))
			return null;

		return new Script(Words.wordsOf(name, text), true);
	}

	public static void addFunction(String name, Function f)
	{
		FUNCTIONS.put(name, f);
	}
	
	public static int getCalculateMode()
	{
		return mode;
	}
}
