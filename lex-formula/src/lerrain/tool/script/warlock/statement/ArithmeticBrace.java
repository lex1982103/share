package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Stack;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArithmeticBrace extends Arithmetic
{
	Code lead;
	Code content;

	public ArithmeticBrace(Words ws, int i)
	{
		super(ws, i);

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
				lead = Expression.expressionOf(ws.cut(0, i));
		}
	}

	@Override
	public boolean isFixed(int mode)
	{
		return (lead == null || lead.isFixed(mode)) && content.isFixed(mode);
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {lead, content};
	}

	@Override
	public void replaceChild(int i, Code code)
	{
		if (i == 0)
			lead = code;
		else if (i == 1)
			content = code;
	}

	public Object treat(Object lead, Code content, Factors factors)
	{
		int i = 0;
		Object val = null;

		if (lead instanceof Iterable)
		{
			int count = 0;
			double num = 0;

			Factors f;
			Object p;

			for (Object v : (Iterable)lead)
			{
				if (v instanceof Factors)
				{
					f = (Factors)v;
				}
				else
				{
					Stack ns = Stack.newStack(factors);
					ns.declare("self", v);

					if (v instanceof Map)
						ns.getHeap().putAll((Map)v);

					f = ns;
				}

				try
				{
					try
					{
						p = content.run(f);
					}
					catch (Interrupt.Return e)
					{
						p = e.getValue();
					}

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
		if (lead != null)
		{
			Object list = lead.run(factors);
			return treat(list, content, factors);
		}

		if (content == null)
			return new LinkedHashMap();

		Object r = content.run(factors);

		if (r instanceof Wrap)
		{
			Map res = new LinkedHashMap();
			for (Object val : ((Wrap)r).toList())
			{
				ArithmeticEntry ae = (ArithmeticEntry)val;
				res.put(ae.getKey(), ae.v.run(factors));
			}

			return res;
		}
		else if (r instanceof ArithmeticCode)
		{
			Map res = new LinkedHashMap();
			ArithmeticEntry ae = (ArithmeticEntry)r;
			res.put(ae.getKey(), ae.v.run(factors));

			return res;
		}

		return r;
	}

	public String toText(String space, boolean line)
	{
		return lead.toText(space, false) + "{\n" + content.toText(space + "  ", true) + "\n" + space + "}\n";
	}
}
