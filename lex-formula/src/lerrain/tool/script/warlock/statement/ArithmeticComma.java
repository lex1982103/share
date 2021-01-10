package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.ArrayList;
import java.util.List;

public class ArithmeticComma extends Code
{
	List<Code> codes = new ArrayList<>();
	
	public ArithmeticComma(Words ws, int i)
	{
		super(ws);

		Code lc = Expression.expressionOf(ws.cut(0, i));
		Code rc = Expression.expressionOf(ws.cut(i + 1));

		if (lc instanceof ArithmeticComma)
			codes.addAll(((ArithmeticComma) lc).codes);
		else
			codes.add(lc);

		if (rc instanceof ArithmeticComma)
			codes.addAll(((ArithmeticComma) rc).codes);
		else
			codes.add(rc);
	}

	@Override
	public boolean isFixed()
	{
		for (Code c : codes)
			if (!c.isFixed())
				return false;

		return true;
	}

	public Object run(Factors factors)
	{
		Wrap wrap = new Wrap();

		for (Code code : codes)
		{
			if (code != null)
				wrap.add(code.run(factors));
		}

		return wrap;

//		if (lc == null && rc == null)
//			return new Wrap();
//		if (rc == null)
//			return new Wrap(lc.run(factors));
//		if (lc == null)
//			return new Wrap(rc.run(factors));
//
//		return new Wrap(lc.run(factors), rc.run(factors));
		
//		/*
//		 * 由逗号分割开的多个表达式，通常是用作函数或数组的参数，并不是每个都需要计算的
//		 * 所以直接打包返回，处理的部分根据需要计算全部或者部分
//		 * 如：x[i] = case(i>0,x[i-1]+y,y); 
//		 * 如果每个都计算，那么这个函数是没办法运行的。
//		 */
//		return new Wrap(l, r);
	}
	
//	public Code left()
//	{
//		return lc;
//	}
//
//	public Code right()
//	{
//		return rc;
//	}

	@Override
	public List<Code> getChildren()
	{
		return codes;
	}

	public String toText(String space, boolean line)
	{
		String str = null;

		for (Code code : codes)
			str = (str == null ? "" : str + "," + (line ? "\n" : " " )) + (code == null ? "" : code.toText(space, line));

		return str;
	}
}
