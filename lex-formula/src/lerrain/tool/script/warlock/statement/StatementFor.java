package lerrain.tool.script.warlock.statement;

import java.util.*;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Interrupt;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementFor extends Code
{
	int type = 1;
	
	Code f1, f2, f3, fc;
	
	public StatementFor(Words ws)
	{
		super(ws);

		int left = 1;
		int right = Syntax.findRightBrace(ws, left + 1);
		Script c = new Script(ws.cut(left + 1, right));
		
		if (c.size() == 3)
		{
			f1 = c.getSentence(0);
			f2 = c.getSentence(1);
			f3 = c.getSentence(2);
		}
		else if (c.size() == 1)
		{
			f1 = c.getSentence(0);
			type = 2;
		}
		
		left = right + 1;
		int type1 = ws.getType(left);
		if (type1 == Words.BRACE)
		{
			right = Syntax.findRightBrace(ws, left + 1);
			fc = new Script(ws.cut(left + 1, right));
		}
		else
		{
			fc = new Script(ws.cut(left, ws.size()));
		}
	}

	public Code markBreakPoint(int pos)
	{
		if (fc.isPointOn(pos))
			return fc.markBreakPoint(pos);

		if (f1 != null && f1.isPointOn(pos))
			return f1.markBreakPoint(pos);
		if (f2 != null && f2.isPointOn(pos))
			return f2.markBreakPoint(pos);
		if (f3 != null && f3.isPointOn(pos))
			return f3.markBreakPoint(pos);

		return super.markBreakPoint(pos);
	}

	@Override
	public boolean isFixed()
	{
		return (fc == null || fc.isFixed())
				&& (f1 == null || f1.isFixed())
				&& (f2 == null || f2.isFixed())
				&& (f3 == null || f3.isFixed());
	}

	public void clearBreakPoints()
	{
		fc.clearBreakPoints();

		if (f1 != null)
			f1.clearBreakPoints();
		if (f2 != null)
			f2.clearBreakPoints();
		if (f3 != null)
			f3.clearBreakPoints();

		super.clearBreakPoints();
	}

	private void traversal(LinkedList ss, Reference nef, Reference ref, Object k, Object v, Stack stack)
	{
		if (v instanceof Map)
		{
			for (Map.Entry<Object, Object> e : ((Map<Object, Object>)v).entrySet())
			{
				if (ss != null)
					ss.add(e.getKey());
				traversal(ss, nef, ref, e.getKey(), e.getValue(), stack);

				if (ss != null)
					ss.removeLast();
			}
		}
		else if (v instanceof Collection)
		{
			int count = 0;

			for (Object o : (Collection)v)
			{
				if (ss != null)
					ss.add(count);
				traversal(ss, nef, ref, count, o, stack);

				if (ss != null)
					ss.removeLast();
				count++;
			}
		}
		else if (v instanceof Object[])
		{
			int count = 0;

			for (Object o : (Object[])v)
			{
				if (ss != null)
					ss.add(count);
				traversal(ss, nef, ref, count, o, stack);

				if (ss != null)
					ss.removeLast();
				count++;
			}
		}
		else
		{
			if (nef != null)
				nef.let(stack, k);

			ref.let(stack, v);

			try
			{
				fc.run(stack);
			}
			catch (Interrupt.Continue e)
			{
			}
		}
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		Stack stack = new Stack(factors);

		int count = 0;

		if (type == 2)
		{
			ArithmeticCode cc = (ArithmeticCode)f1.run(stack);
			Object value = cc.v[1].run(stack);

			Reference nef, ref, mef = null;
			if (cc.v[0] instanceof ArithmeticComma)
			{
				ArithmeticComma comma = (ArithmeticComma)cc.v[0];
				nef = (Reference)comma.getCodes().get(0);
				ref = (Reference)comma.getCodes().get(1);

				if (comma.getCodes().size() > 2)
					mef = (Reference)comma.getCodes().get(2);
			}
			else
			{
				nef = null;
				ref = (Reference)cc.v[0];
			}

			if ("~:".equals(cc.getSymbol()))
			{
				LinkedList ll = null;
				if (mef != null)
				{
					ll = new LinkedList();
					mef.let(stack, ll);
				}

				try
				{
					traversal(ll, nef, ref, null, value, stack);
				}
				catch (Interrupt.Break e)
				{
				}
			}
			else if (value instanceof Object[])
			{
				for (Object v : (Object[])value)
				{
					if (nef != null)
						nef.let(stack, count);

					ref.let(stack, v);

					try
					{
						fc.run(stack);
					}
					catch (Interrupt.Break e)
					{
						break;
					}
					catch (Interrupt.Continue e)
					{
					}

					count++;

					if (Stack.runtimeListener != null && Stack.LOOP_ALERT_TIMES > 0)
					{
						if (count % Stack.LOOP_ALERT_TIMES == 0)
							Stack.runtimeListener.onEvent(Stack.EVENT_LOOP_ALERT, count);
					}
				}
			}
			else if (value instanceof Collection)
			{
				for (Object v : (Collection<?>)value)
				{
					if (nef != null)
						nef.let(stack, count);

					ref.let(stack, v);

					try
					{
						fc.run(stack);
					}
					catch (Interrupt.Break e)
					{
						break;
					}
					catch (Interrupt.Continue e)
					{
					}

					count++;

					if (Stack.runtimeListener != null && Stack.LOOP_ALERT_TIMES > 0)
					{
						if (count % Stack.LOOP_ALERT_TIMES == 0)
							Stack.runtimeListener.onEvent(Stack.EVENT_LOOP_ALERT, count);
					}
				}
			}
			else if (value instanceof Map)
			{
				if (nef == null)
				{
					for (Object v : ((Map<?, ?>) value).keySet())
					{
						ref.let(stack, v);

						try
						{
							fc.run(stack);
						}
						catch (Interrupt.Break e)
						{
							if (e.popOut() > 0)
								throw e;

							break;
						}
						catch (Interrupt.Continue e)
						{
						}

						count++;

						if (Stack.runtimeListener != null && Stack.LOOP_ALERT_TIMES > 0)
						{
							if (count % Stack.LOOP_ALERT_TIMES == 0)
								Stack.runtimeListener.onEvent(Stack.EVENT_LOOP_ALERT, count);
						}
					}
				}
				else
				{
					for (Map.Entry entry : ((Map<?, ?>) value).entrySet())
					{
						nef.let(stack, entry.getKey());
						ref.let(stack, entry.getValue());

						try
						{
							fc.run(stack);
						}
						catch (Interrupt.Break e)
						{
							if (e.popOut() > 0)
								throw e;

							break;
						}
						catch (Interrupt.Continue e)
						{
						}

						count++;

						if (Stack.runtimeListener != null && Stack.LOOP_ALERT_TIMES > 0)
						{
							if (count % Stack.LOOP_ALERT_TIMES == 0)
								Stack.runtimeListener.onEvent(Stack.EVENT_LOOP_ALERT, count);
						}
					}
				}
			}
			else if (value instanceof Iterator)
			{
				Iterator iter = (Iterator)value;
				while (iter.hasNext())
				{
					if (nef != null)
						nef.let(stack, count);

					ref.let(stack, iter.next());

					try
					{
						fc.run(stack);
					}
					catch (Interrupt.Break e)
					{
						if (e.popOut() > 0)
							throw e;

						break;
					}
					catch (Interrupt.Continue e)
					{
					}

					count++;

					if (Stack.runtimeListener != null && Stack.LOOP_ALERT_TIMES > 0)
					{
						if (count % Stack.LOOP_ALERT_TIMES == 0)
							Stack.runtimeListener.onEvent(Stack.EVENT_LOOP_ALERT, count);
					}
				}
			}
		}
		else
		{
			if (f1 != null)
				f1.run(stack);
			
			while (Value.booleanOf(f2, stack))
			{
				try
				{
					fc.run(stack);
				}
				catch (Interrupt.Break e)
				{
					if (e.popOut() > 0)
						throw e;

					break;
				}
				catch (Interrupt.Continue e)
				{
				}

				count++;

				if (Stack.runtimeListener != null && Stack.LOOP_ALERT_TIMES > 0)
				{
					if (count % Stack.LOOP_ALERT_TIMES == 0)
						Stack.runtimeListener.onEvent(Stack.EVENT_LOOP_ALERT, count);
				}

				f3.run(stack);
			}
		}
		
		return null;
	}

	public String toText(String space, boolean line)
	{
		StringBuffer buf = new StringBuffer("FOR (");
		if (type == 2)
			buf.append(f1.toText("", line));
		else
			buf.append((f1 == null ? "" : f1.toText("", line)) + "; " + (f2 == null ? "" : f2.toText("", line)) + "; " + (f3 == null ? "" : f3.toText("", line)));
		buf.append(")\n");
		buf.append(space + "{\n");
		buf.append(fc.toText(space + "    ", line) + "\n");
		buf.append(space + "}");
		
		return buf.toString();
	}
}
