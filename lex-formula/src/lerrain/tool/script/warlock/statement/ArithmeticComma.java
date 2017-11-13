package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticComma implements Code
{
	Code lc, rc;
	
	public ArithmeticComma(Words ws, int i)
	{
		lc = Expression.expressionOf(ws.cut(0, i));
		rc = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		return new Wrap(lc.run(factors), rc.run(factors));
		
//		/*
//		 * 由逗号分割开的多个表达式，通常是用作函数或数组的参数，并不是每个都需要计算的
//		 * 所以直接打包返回，处理的部分根据需要计算全部或者部分
//		 * 如：x[i] = case(i>0,x[i-1]+y,y); 
//		 * 如果每个都计算，那么这个函数是没办法运行的。
//		 */
//		return new Wrap(l, r);
	}
	
	public Code left()
	{
		return lc;
	}

	public Code right()
	{
		return rc;
	}

	public String toText(String space)
	{
		return lc.toText("") + ", " + rc.toText("");
	}
}
