package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementIf implements Code
{
	Code c, yes, no;
	
	public StatementIf(Words ws)
	{
		int left = 1; //条件左括号的位置
		int right = Syntax.findRightBrace(ws, left + 1);
		c = Expression.expressionOf(ws.cut(left + 1, right));
		
		left = right + 1; 
		int type1 = ws.getType(left);
		if (type1 == Words.BRACE)
		{
			right = Syntax.findRightBrace(ws, left + 1);
			yes = new Script(ws.cut(left + 1, right));
			right++; //else的位置为右括号+1
		}
		else
		{
			right = findElse(ws, left);
			if (right < 0) 
				yes = new Script(ws.cut(left));
			else
				yes = new Script(ws.cut(left, right));
		}
		
		if (right > 0 && right < ws.size() && "else".equals(ws.getWord(right)))
		{
			left = right + 1; //else后面左括号的位置
			
			int type2 = ws.getType(left);
			if (type2 == Words.BRACE)
			{
				right = Syntax.findRightBrace(ws, left + 1);
				no = new Script(ws.cut(left + 1, right));
			}
			else
			{
				no = new Script(ws.cut(left));
			}
		}
	}
	
	private int findElse(Words ws, int i)
	{
		int size = ws.size();
		int deep = 1;
		for (; i < size; i++)
		{
			String s = ws.getWord(i);
			int t = ws.getType(i);
			
			if (t == Words.BRACE || t == Words.BRACKET || t == Words.PRT)
				i = Syntax.findRightBrace(ws, i + 1);
			
			if ("if".equals(s))
			{
				deep++;
			}
			else if ("else".equals(s))
			{
				deep--;
				if (deep == 0) return i;
			}
		}
		
		return -1;
	}

	public Object run(Factors factors)
	{
		boolean r;
		
		Value v = Value.valueOf(c, factors);
		if (v.isType(Value.TYPE_BOOLEAN))
			r = v.booleanValue();
		else if (v.isType(Value.TYPE_DECIMAL))
			r = v.intValue() != 0;
		else
			r = !v.isNull(); //非boolean型的值，不等于null即为true
		
		if (r)
		{
			return yes.run(new Stack(factors));
		}
		else
		{
			if (no != null)
				return no.run(new Stack(factors));
		}
		
		return null;
	}

	public String toText(String space)
	{
		StringBuffer buf = new StringBuffer("IF (");
		buf.append(c.toText(""));
		buf.append(")\n");
		buf.append(space + "{\n");
		buf.append(yes.toText(space + "    ") + "\n");
		buf.append(space + "}");
		
		if (no != null)
		{
			buf.append("\n");
			buf.append(space + "ELSE\n");
			buf.append(space + "{\n");
			buf.append(no.toText(space + "    ") + "\n");
			buf.append(space + "}");
		}
		
		return buf.toString();
	}
}
