package lerrain.tool.script.warlock.statement;

import java.util.List;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
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
public class ArithmeticPointMethod extends Code
{
	Code obj;
	
	String name;

	boolean tk = true;
	
	public ArithmeticPointMethod(Words ws, int i)
	{
		super(ws, i);

		obj = Expression.expressionOf(ws.cut(0, i));
		
		if (ws.getType(i + 1) != Words.METHOD)
			throw new SyntaxException("POINT-METHOD运算右侧没有找到方法名");

		if (ws.getType(i) == Words.POINT_KEY2)
			tk = false;

		name = ws.getWord(i + 1);
	}

	public Object run(Factors factors)
	{
		Object v = obj.run(factors);

		if (v == null)
		{
			if (tk)
				throw new ScriptRuntimeException(this, factors, "空指针 - " + toText("", true));
			else
				return null;
		}

		if (v instanceof Factors)
			return ((Factors)v).get(name);
		if (v instanceof Map)
			return ((Map)v).get(name);
		if (v instanceof List)
			return ((List)v).get(Integer.parseInt(name));

		throw new ScriptRuntimeException(this, factors, "point左侧只能是map或者factors，目前为" + v.toString());
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {obj};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			obj = code;
	}

	public String getMethodName()
	{
		return name;
	}

	public void setMethodName(String name)
	{
		this.name = name;
	}

	@Override
	public boolean isFixed(int mode)
	{
		return obj.isFixed(mode);
	}

	public String toText(String space, boolean line)
	{
		return obj.toText("", line) + "." + name;
	}
}
