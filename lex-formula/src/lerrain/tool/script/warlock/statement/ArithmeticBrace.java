package lerrain.tool.script.warlock.statement;

import java.util.LinkedHashMap;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticBrace implements Code
{
	String type = null;
	
	Code v, a;
	
//	ArithmeticArray pv;
	
	public ArithmeticBrace(Words ws, int i)
	{
		if (ws.getType(i) != Words.BRACE || ws.getType(ws.size() - 1) != Words.BRACE_R)
			throw new SyntaxException("找不到数组的右括号");
		
		if (i > 0) //否则为Object数组定义 [a, b, c, ...]
		{
			if (i == 1 && "double".equals(ws.getWord(0))) // double[a, b, c, ...]
				type = "double";
			else
				v = Expression.expressionOf(ws.cut(0, i));
		}
		
		a = Expression.expressionOf(ws.cut(i + 1, ws.size() - 1));
	}

	public Object run(Factors factors)
	{
		Map res = new LinkedHashMap();

		if (a == null)
			return res;

		Object r = a.run(factors);

		if (r instanceof Wrap)
		{
			for (Object val : ((Wrap)r).toList())
			{
				Code[] v = (Code[])val;
				res.put(v[0].toString(), v[1].run(factors));
			}
		}
		else if (r instanceof Code[])
		{
			Code[] v = (Code[])r;
			res.put(v[0].toString(), v[1].run(factors));
		}
		
		return res;
	}

	public String toText(String space)
	{
		return (v == null ? "OBJECT" : v.toText("")) + "{" + a.toText("") + "}";
	}
}
