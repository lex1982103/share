package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Stack;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ArithmeticBrace extends Code
{
	Code left;
	Code content;

	public ArithmeticBrace(Words ws, int i)
	{
		super(ws);

		if (ws.getType(i) != Words.BRACE || ws.getType(ws.size() - 1) != Words.BRACE_R)
			throw new SyntaxException("找不到数组的右括号");

		if (ws.size() - 1 == i + 1 || ws.getType(i + 2) == Words.COLON_SPLIT)
		{
			content = Expression.expressionOf(ws.cut(i + 1, ws.size() - 1));
		}
		else
		{
			content = new StatementParagraph(ws.cut(i, ws.size()));

			if (i > 0)
				left = Expression.expressionOf(ws.cut(0, i));
		}
	}

	public Object treat(Code left, Code content, Factors factors)
	{
		int i = 0;
		Object val = null;

		Object list = left.run(factors);
		if (list instanceof Collection)
		{
			int count = 0;
			double num = 0;

			for (Object v : (Collection)list)
			{
				Factors f;
				if (v instanceof Factors)
				{
					f = (Factors)v;
				}
				else
				{
					Stack ns = new Stack(factors);
					ns.declare("self", v);

					if (v instanceof Map)
						ns.getHeap().putAll((Map)v);

					f = ns;
				}

				try
				{
					Object p = content.run(f);
					if (p instanceof Boolean)
					{
						if ((Boolean)p)
							count++;
					}
					else if (p instanceof Number)
					{
						num += ((Number) p).doubleValue();
					}
				}
				catch (Interrupt.Break e)
				{
					break;
				}
				catch (Interrupt.Continue e)
				{
				}

				i++;

				if (Stack.runtimeListener != null && Stack.LOOP_ALERT_TIMES > 0)
				{
					if (i % Stack.LOOP_ALERT_TIMES == 0)
						Stack.runtimeListener.onEvent(Stack.EVENT_LOOP_ALERT, i);
				}
			}

			if (num != 0)
				val = num + count;
			else
				val = count;
		}

		return val;
	}

	public Object run(Factors factors)
	{
		if (left != null)
			return treat(left, content, factors);

		if (content == null)
			return new LinkedHashMap();

		Object r = content.run(factors);

		if (r instanceof Wrap)
		{
			Map res = new LinkedHashMap();
			for (Object val : ((Wrap)r).toList())
			{
				Code[] v = ((ArithmeticCode)val).v;
				res.put(v[0].toString(), v[1].run(factors));
			}

			return res;
		}
		else if (r instanceof ArithmeticCode)
		{
			Map res = new LinkedHashMap();
			ArithmeticCode cc = (ArithmeticCode)r;
			res.put(cc.v[0].toString(), cc.v[1].run(factors));

			return res;
		}

		return r;
	}

	public String toText(String space, boolean line)
	{
		return "{\n" + content.toText(space + "  ", true) + "\n" + space + "}\n";
	}
}
