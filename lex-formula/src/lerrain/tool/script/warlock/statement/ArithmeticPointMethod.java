package lerrain.tool.script.warlock.statement;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

/**
 * <p>直接取对象的某个方法</p>
 * 
 * <p>注意：使用这个功能一般会降低脚本的跨语言性。</p>
 * <p>一般脚本语言和主语言配合时，涉及不少对象的联合使用。这些对象无论是语言原生的还是自己开发的，在使用另一套语言时
 * 都面临方法名可能不同的问题，通常情况下，原生对象（如List、Map）的本身就不同，自己开发的有时也受语言限制（比如OC不
 * 允许方法同名）造成一些不同，这时必定造成脚本无法运行（方法名变更）</p>
 * <p>由于有时不可避免，通常采用映射的方法，把涉及具体对象方法的地方写在配置里，参见lex-finance的variable.xml</p>
 *
 * @author lerrain
 *
 */
public class ArithmeticPointMethod implements Code
{
	Code obj, param;
	
	String name;
	
	public ArithmeticPointMethod(Words ws, int i)
	{
		obj = Expression.expressionOf(ws.cut(0, i));
		
		if (ws.getType(i + 1) != Words.METHOD)
			throw new SyntaxException("POINT-METHOD运算右侧没有找到方法名");
		
		name = ws.getWord(i + 1);
		
		int l = i + 2;
		int r = Syntax.findRightBrace(ws, l + 1);
		
		param = l + 1 == r ? null : Expression.expressionOf(ws.cut(l + 1, r));
	}

	public Object run(Factors factors)
	{
		Object v = obj.run(factors);
		Object[] wrap = Wrap.arrayOf(param, factors);
		
		if (v instanceof Factors)
		{
			Object val = ((Factors)v).get(name);
			if (val instanceof Function)
				return ((Function)val).run(wrap, factors);
		}
		
		if (v instanceof Map)
		{
			Object val = ((Map)v).get(name);
			if (val instanceof Function)
				return ((Function)val).run(wrap, factors);
		}
		
		Object value;
		
		Class[] classArray = new Class[wrap.length];
		for (int i=0;i<classArray.length;i++)
		{
			if (wrap[i] != null)
				classArray[i] = wrap[i].getClass();
		}
		
		Method method = null;
		try
		{
			method = v.getClass().getDeclaredMethod(name, classArray);
		}
		catch (NoSuchMethodException e)
		{
			//上面的方法，如果参数某个参数是传入参数的父类，则会找不到该方法。这里对这种情况做一下处理。
			//OC--
			Method[] m = v.getClass().getMethods();
			for (int i=0;i<m.length;i++)
			{
				if (name.equals(m[i].getName()))
				{
					Class[] c = m[i].getParameterTypes();
					if (c.length == classArray.length)
					{
						boolean pass = true;
						for(int j=0;j<c.length;j++)
						{
							//传入参数是null，除了int(double boolean等先不考虑了)以外的其他都适合，只找第一个。
							if (wrap[j] == null && !c[j].equals(Integer.TYPE))
							{
							}
							//int
							else if (c[j].equals(Integer.TYPE) && wrap[j] instanceof Integer)
							{
							}
							else if (c[j].equals(Integer.TYPE) && wrap[j] instanceof BigDecimal)
							{
								wrap[j] = Integer.valueOf(((BigDecimal)wrap[j]).intValue());
							}
							//boolean
							else if (c[j].equals(Boolean.TYPE) && wrap[j] instanceof Boolean)
							{
							}
							//double
							else if (c[j].equals(Double.TYPE) && wrap[j] instanceof Double)
							{
							}
							else if (c[j].equals(Double.TYPE) && wrap[j] instanceof Integer)
							{
								wrap[j] = Double.valueOf(((Integer)wrap[j]).doubleValue());
							}
							else if (c[j].equals(Double.TYPE) && wrap[j] instanceof Float)
							{
								wrap[j] = Double.valueOf(((Float)wrap[j]).doubleValue());
							}
							else if (c[j].equals(Double.TYPE) && wrap[j] instanceof BigDecimal)
							{
								wrap[j] = Double.valueOf(((BigDecimal)wrap[j]).doubleValue());
							}
							//float
							else if (c[j].equals(Float.TYPE) && wrap[j] instanceof Integer)
							{
								wrap[j] = Float.valueOf(((Integer)wrap[j]).floatValue());
							}
							else if (c[j].equals(Float.TYPE) && wrap[j] instanceof Float)
							{
							}
							else if (c[j].equals(Float.TYPE) && wrap[j] instanceof Double)
							{
								wrap[j] = Float.valueOf(((Float)wrap[j]).floatValue());
							}
							else if (c[j].equals(Float.TYPE) && wrap[j] instanceof BigDecimal)
							{
								wrap[j] = Float.valueOf(((BigDecimal)wrap[j]).floatValue());
							}
							else if (!c[j].isInstance(wrap[j]))
							{
								pass = false;
								break;
							}
						}
						
						if (pass)
						{
							method = m[i];
							break;
						}
					}
					
				}
			}
			//--OC
		}
		
		try
		{
			if (method == null)
				throw new NoSuchMethodException();
			
			value = method.invoke(v, wrap);
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException("point运算取值失败，没有该方法 -- 对象：" + v + "，方法名: " + name + "<" + wrap.length + ">", e);
		}
		catch (Exception e)
		{
			throw new RuntimeException("point运算取值失败，方法内部错误 -- 对象：" + v + "，方法名: " + name, e);
		}
		
		return value;
	}

	public String toText(String space)
	{
		return obj.toText("") + "." + name + "(" + (param == null ? "" : param.toText("")) + ")";
	}
}
