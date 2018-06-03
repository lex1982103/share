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
	Code v, a;

	public ArithmeticBrace(Words ws, int i)
	{
		if (ws.getType(i) != Words.BRACE || ws.getType(ws.size() - 1) != Words.BRACE_R)
			throw new SyntaxException("找不到数组的右括号");

		if (ws.size() - 1 == i + 1 || ws.getType(i + 2) == Words.COLON)
			a = Expression.expressionOf(ws.cut(i + 1, ws.size() - 1));
		else
			a = new StatementParagraph(ws.cut(i, ws.size()));
	}

	public Object run(Factors factors)
	{
		if (a == null)
			return new LinkedHashMap();;

		Object r = a.run(factors);

		if (r instanceof Wrap)
		{
			Map res = new LinkedHashMap();
			for (Object val : ((Wrap)r).toList())
			{
				Code[] v = (Code[])val;
				res.put(v[0].toString(), v[1].run(factors));
			}

			return res;
		}
		else if (r instanceof Code[])
		{
			Map res = new LinkedHashMap();
			Code[] v = (Code[])r;
			res.put(v[0].toString(), v[1].run(factors));

			return res;
		}

		return r;
	}

	public String toText(String space)
	{
		return (v == null ? "OBJECT" : v.toText("")) + "{" + a.toText("") + "}";
	}
}
