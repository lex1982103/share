package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticParentheses implements Code
{
	Code c;
	
	public ArithmeticParentheses(Words ws, int i)
	{
		int l = i;
		int r = Syntax.findRightBrace(ws, l + 1);
		
		if (l != 0 || r != ws.size() - 1)
			throw new SyntaxException("小括号两侧有无法处理的代码");
		
		if (l + 1 == r)
			throw new SyntaxException("小括号运算内部不能为空");
		
		c = Expression.expressionOf(ws.cut(l + 1, r));
	}

	public Object run(Factors factors)
	{
		Object r = c.run(factors);
		if (r instanceof Wrap)
			return ((Wrap)r).toArray();
					
		return r;
	}

	public String toText(String space)
	{
		return "(" + c.toText("") + ")";
	}
}
