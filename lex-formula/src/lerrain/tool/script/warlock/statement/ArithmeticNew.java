package lerrain.tool.script.warlock.statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class ArithmeticNew implements Code
{
	Code v, a;
	
	String cluss;
	int type;
	
	List array;
	
	public ArithmeticNew(Words ws, int i)
	{
		if (i == ws.size() - 1 || ws.getType(i + 1) != Words.CLASS)
			throw new SyntaxException("没有找到new的类型");
		
		cluss = ws.getWord(i + 1);
		
		if (ws.size() > i + 2 && ws.getType(i + 2) == Words.BRACKET) //数组
		{
			type = 1; 
			
			array = new ArrayList();
			
			int l = i + 2;
			int r = Syntax.findRightBrace(ws, l + 1);
			array.add(Expression.expressionOf(ws.cut(l + 1, r)));
			
			if (ws.size() > r + 1 && ws.getType(r + 1) == Words.BRACKET)
			{
				l = r + 1;
				r = Syntax.findRightBrace(ws, l + 1);
				array.add(Expression.expressionOf(ws.cut(l + 1, r)));
			}
		}
		else if (ws.size() > i + 2 && ws.getType(i + 2) == Words.PRT) //对象
		{
			type = 2;
		}
	}

	public Object run(Factors factors)
	{
		if (type == 1 && array != null)
		{
			if (array.size() == 1)
			{
				int s = Value.intOf((Code)array.get(0), factors);
				
				if ("double".equals(cluss))
					return new double[s];
				if ("int".equals(cluss))
					return new int[s];
				if ("object".equals(cluss))
					return new Object[s];
			}
			else if (array.size() == 2)
			{
				int s1 = Value.intOf((Code)array.get(0), factors);
				int s2 = Value.intOf((Code)array.get(1), factors);
				
				Object r = null;
				if ("double".equals(cluss))
					return new double[s1][s2];
				if ("int".equals(cluss))
					return new int[s1][s2];
				if ("object".equals(cluss))
					return new Object[s1][s2];
				
//				Object[] r = new Object[s1];
//				
//				if ("double".equals(cluss))
//				{
//					for (int i=0;i<s1;i++)
//						r[i] = new double[s2];
//				}
//				else if ("int".equals(cluss))
//				{
//					for (int i=0;i<s1;i++)
//						r[i] = new int[s2];
//				}
//				else
//				{
//					r = null;
//				}
				
				return r;
			}
		}
		else if (type == 2)
		{
			if ("map".equals(cluss))
				return new HashMap();
			if ("list".equals(cluss))
				return new ArrayList();
			if ("synchMap".equals(cluss))
				return new ConcurrentHashMap();
			if ("synchList".equals(cluss))
				return Collections.synchronizedList(new ArrayList());
		}
		
		return null;
	}

	public String toText(String space)
	{
		return "";
	}
}
