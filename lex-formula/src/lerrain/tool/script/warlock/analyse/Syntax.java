package lerrain.tool.script.warlock.analyse;

import java.util.ArrayList;
import java.util.List;

import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.statement.*;

/**
 * 语法分析
 * @author lerrain
 */
public class Syntax
{
	public static Code sentenceOf(Words ws)
	{
		if (ws.getType(ws.size() - 1) == Words.SEMICOLON)
			ws = ws.cut(0, ws.size() - 1);
		
		if (ws.isEmpty())
			return null;
		
//		System.out.println(ws);

		int type = ws.getType(0);
		String word = ws.getWord(0);

		if (type == Words.KEYWORD)
		{
			if ("if".equals(word))
			{
				return new StatementIf(ws);
			}
			else if ("return".equals(word))
			{
				return new StatementReturn(ws);
			}
			else if ("for".equals(word))
			{
				return new StatementFor(ws);
			}
			else if ("while".equals(word))
			{
				return new StatementWhile(ws);
			}
			else if ("break".equals(word))
			{
				return new StatementBreak(ws);
			}
			else if ("var".equals(word))
			{
				return new StatementVar(ws);
			}
			else if ("throw".equals(word))
			{
				return new StatementThrow(ws);
			}
			else if ("continue".equals(word))
			{
				return new StatementContinue(ws);
			}
			else if ("thread".equals(word))
			{
				return new StatementThread(ws);
			}
			else if ("synch".equals(word))
			{
				return new StatementSynch(ws);
			}
//			else if ("try".equals(word))
//			{
//				return new StatementTry(ws);
//			}
			
			throw new SyntaxException("无法识别的关键字：" + word);
		}
		else if (type == Words.BRACE)
		{
			return new StatementParagraph(ws);
		}
		
		return Expression.expressionOf(ws);
	}
	
	/**
	 * 把一段程序，按语句切割
	 * @param ws
	 * @return List of Function
	 */
	public static List split(Words ws)
	{
		List r = new ArrayList();
		
		int size = ws.size();
		
		for (int i = 0; i < size; i++)
		{
			int left = i;
			i = findSplit(ws, i);
			i = i >= 0 ? i : size - 1;
			
			r.add(ws.cut(left, i + 1));
		}
		
		return r;
	}
	
	/**
	 * 找到第一个右侧的括号，中间被括号包括的内容会被跳过
	 * @param ws
	 * @param i
	 * @return
	 */
	public static int findRightBrace(Words ws, int p)
	{
		int size = ws.size();
		
		int deep = 1;
		int i = p;
		for (; i < size; i++)
		{
			int t = ws.getType(i);
			
			if (t == Words.BRACE || t == Words.BRACKET || t == Words.PRT)
			{
				deep++;
			}
			else if (t == Words.BRACE_R || t == Words.BRACKET_R || t == Words.PRT_R)
			{
				deep--;
				if (deep == 0)
					break;
			}
		}
		
		if (i >= size)
			throw new SyntaxException("无法找到对应的右侧括号：" + ws + " at " + p);

		return	i;
	}
	
	public static int findLeftBrace(Words ws, int i)
	{
		int deep = 1;
		for (; i >= 0; i--)
		{
			int t = ws.getType(i);
			
			if (t == Words.BRACE_R || t == Words.BRACKET_R || t == Words.PRT_R)
			{
				deep++;
			}
			else if (t == Words.BRACE || t == Words.BRACKET || t == Words.PRT)
			{
				deep--;
				if (deep == 0)
					break;
			}
		}
		
		return i;
	}
	
	public static int findSplit(Words ws, int i)
	{
		int size = ws.size();
		
		for (; i < size; i++)
		{
			int t = ws.getType(i);
			
			if (t == Words.BRACE || t == Words.BRACKET || t == Words.PRT)
				i = findRightBrace(ws, i + 1);
			
			if (isSplit(ws, i))
				return i;
		}
		
		return -1;
	}
	
	public static boolean isLeftBrace(Words ws, int i)
	{
		int t = ws.getType(i);
		return t == Words.BRACE || t == Words.BRACKET || t == Words.PRT;
	}
	
	public static int findSplitWithoutSyntax(Words ws, int i)
	{
		int size = ws.size();
		
		for (; i < size; i++)
		{
			int t = ws.getType(i);
			
			if (t == Words.BRACE || t == Words.BRACKET || t == Words.PRT)
				i = findRightBrace(ws, i + 1);
			
			if (t == Words.SEMICOLON || t == Words.BRACE_R)
				return i;
		}
		
		return -1;
	}
	
	private static boolean isSplit(Words ws, int i)
	{
		int size = ws.size();
		int t = ws.getType(i);
		
		//分号和右大括号并不能完全代表行结尾，如果后面跟else、catch等，需要继续追加
		if (t == Words.SEMICOLON || t == Words.BRACE_R)
		{
			String s1 = i + 1 < size ? ws.getWord(i + 1) : null;
			return (!"else".equals(s1) && !"catch".equals(s1));
		}
		
		return false;
	}
}
