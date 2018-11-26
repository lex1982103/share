package lerrain.tool.script.warlock.statement;

import java.util.Collection;
import java.util.Map;

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

	public void markBreakPoint(int pos)
	{
		if (fc.isPointOn(pos))
			fc.markBreakPoint(pos);
		else
			super.markBreakPoint(pos);
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		Stack stack = new Stack(factors);
		
		if (type == 2)
		{
			Code[] cc = (Code[])f1.run(stack);
			Object value = cc[1].run(stack);
			Reference ref = (Reference)cc[0];
			
			if (value instanceof Object[])
			{
				for (Object v : (Object[])value)
				{
					ref.let(stack, v);
					
					Object result = fc.run(stack);
					
					if (Interrupt.isMatch(result, Interrupt.BREAK))
						break;
					if (Interrupt.isMatch(result, Interrupt.RETURN))// || Interrupt.isMatch(result, Interrupt.THROW))
						return result;
				}
			}
			else if (value instanceof Collection)
			{
				for (Object v : (Collection<?>)value)
				{
					ref.let(stack, v);
					
					Object result = fc.run(stack);
					
					if (Interrupt.isMatch(result, Interrupt.BREAK))
						break;
					if (Interrupt.isMatch(result, Interrupt.RETURN))// || Interrupt.isMatch(result, Interrupt.THROW))
						return result;
				}
			}
			else if (value instanceof Map)
			{
//				Collection val = null;
//
//				if (f1 instanceof ArithmeticColon)
//				{
//					ArithmeticColon ac = (ArithmeticColon) f1;
//					if ("::".equals(ac.getSymbol()))
//						val = ((Map<?, ?>)value).values();
//				}
//
//				if (val == null)
//					val = ((Map<?, ?>)value).keySet();

				for (Object v : ((Map<?, ?>)value).keySet())
				{
					ref.let(stack, v);

					Object result = fc.run(stack);

					if (Interrupt.isMatch(result, Interrupt.BREAK))
						break;
					if (Interrupt.isMatch(result, Interrupt.RETURN))// || Interrupt.isMatch(result, Interrupt.THROW))
						return result;
				}
			}
		}
		else
		{
			if (f1 != null)
				f1.run(stack);
			
			while (Value.booleanOf(f2, stack))
			{
				Object result = fc.run(stack);
				
				if (Interrupt.isMatch(result, Interrupt.BREAK))
					break;
				if (Interrupt.isMatch(result, Interrupt.RETURN))// || Interrupt.isMatch(result, Interrupt.THROW))
					return result;
				
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
