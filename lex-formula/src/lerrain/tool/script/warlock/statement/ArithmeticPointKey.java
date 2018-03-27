package lerrain.tool.script.warlock.statement;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.Stack;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.CodeImpl;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticPointKey extends CodeImpl implements Reference
{
	Code l;
	String key;

	boolean tk = true;
	
	public ArithmeticPointKey(Words ws, int i)
	{
		super(ws, i);

		l = Expression.expressionOf(ws.cut(0, i));
		
		if (ws.getType(i + 1) != Words.KEY)
			throw new SyntaxException("POINT-KEY运算右侧没有找到KEY");

		if (ws.getType(i) == Words.POINT_KEY2)
			tk = false;

		key = ws.getWord(i + 1);
	}

	/**
	 * 如果结果是Formula，这里不能直接二次计算
	 * 
	 * 对于y.xxx[a]，y.xxx如果是Formula，本应是一个函数指针。
	 * 执行了Formula返回后数组部分会识别错误，抛出异常。
	 */
	public Object run(Factors factors)
	{
		Object v = l.run(factors);
		
		if (v == null)
		{
			if (tk)
				throw new ScriptRuntimeException(this, factors, "空指针 - " + toText(""));
			else
				return null;
		}
		
		if (v instanceof Factors)
			return ((Factors)v).get(key);
		if (v instanceof Map)
			return ((Map)v).get(key);

//		if (v instanceof List)
//		{
//			List l = ((List)v);
//			int index = Integer.parseInt(key);
//
//			return index < l.size() ? l.get(index) : null;
//		}
//
//		if (v instanceof Object[])
//		{
//			Object[] l = ((Object[])v);
//			int index = Integer.parseInt(key);
//
//			return index < l.length ? l[index] : null;
//		}

		try
		{
			Field f = v.getClass().getDeclaredField(key);
			if (f != null)
			{
				f.setAccessible(true);
				return f.get(v);
			}
		}
		catch (Exception e)
		{
			throw new ScriptRuntimeException(this, factors, String.format("POINT-KEY失败：%s<%s> / %s，%s", v.toString(), v.getClass().toString(), key, e.toString()));
		}

		throw new ScriptRuntimeException(this, factors, String.format("POINT-KEY失败：%s<%s> / %s，缺少key", v.toString(), v.getClass().toString(), key));
	}

	public void let(Factors factors, Object value)
	{
		Object v = l.run(factors);
		if (v instanceof Stack)
			((Stack)v).set(key, value);
		else if (v instanceof Map)
			((Map)v).put(key, value);
		else
			throw new ScriptRuntimeException(this, factors, "赋值时，被赋值一方的POINT运算的左侧不是有效类型");
	}

	public String toText(String space)
	{
		return l.toText("") + "." + key;
	}
}
