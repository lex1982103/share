package lerrain.tool.script.warlock.analyse;

import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Chinese;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.statement.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 语法分析
 * @author lerrain
 */
public class Syntax
{
	static Map<String, Statement> keywords = new HashMap<>();
	static Statement ERROR = words -> { throw new SyntaxException(words, 0, "关键字位置错误"); };

	static
	{
		keywords.put("for", words -> new StatementFor(words));
		keywords.put("while", words -> new StatementWhile(words));
		keywords.put("if", words -> new StatementIf(words));
		keywords.put("else", ERROR);
		keywords.put("return", words -> new StatementReturn(words));
		keywords.put("continue", words -> new StatementContinue(words));
		keywords.put("break", words -> new StatementBreak(words));
		keywords.put("var", words -> new StatementVar(words));
		keywords.put("throw", words -> new StatementThrow(words));
		keywords.put("thread", words -> new StatementThread(words));
		keywords.put("synch", words -> new StatementSynch(words));
		keywords.put("function", words -> new StatementFunction(words));
		keywords.put("catch", ERROR);
		keywords.put("goto", words -> new StatementGoto(words));
	}

	public interface Statement
	{
		Code newInstance(Words words);
	}

	/**
	 * 新增加自定义关键字，需要注意检查之前有无使用这个单词当作变量
	 * @param keyword
	 * @param s
	 */
	public static void register(String keyword, Statement s)
	{
		keywords.put(keyword, s);
	}

	public static Code sentenceOf(Words ws)
	{
		int i = ws.size();
		for (; i > 0; --i)
			if (ws.getType(i - 1) != Words.SEMICOLON)
				break;

		if (i == 0)
			return null;
		if (ws.size() > i)
			ws = ws.cut(0, i);

//		System.out.println(ws);

		int type = ws.getType(0);
		String word = ws.getWord(0);

		if (type == Words.KEYWORD)
			return keywords.get(word).newInstance(ws);
		else if ("try".equals(word) && ws.getType(1) == Words.BRACE) //try也是个函数，就不好做关键字了，用try后面紧跟一个大括号来识别
			return new StatementTry(ws);
		else if (type == Words.BRACE && ws.size() - 1 != i + 1 && ws.getType(i + 2) != Words.COLON_SPLIT)
			return new StatementParagraph(ws);

		if (ws.size() == 1 && type == Words.CHINESE)
			return new Chinese(ws);
		
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
	 * @param p
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
			throw new SyntaxException(ws, p, "无法找到对应的右侧括号");

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
			int j = 0;

			if (t == Words.BRACE || t == Words.BRACKET || t == Words.PRT)
			{
				j = i;
				i = findRightBrace(ws, i + 1);
			}

			if (isSplit(ws, i, j - 1))
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
	
	private static boolean isSplit(Words ws, int i, int j)
	{
		int size = ws.size();
		int t = ws.getType(i);

		//分号和右大括号并不能完全代表行结尾，如果后面跟else、catch等，需要继续追加
		if (t == Words.SEMICOLON || t == Words.BRACE_R)
		{
			String s1 = null;
			int type = 0;

			if (i + 1 < size)
			{
				type = ws.getType(i + 1);
				s1 = ws.getWord(i + 1);
			}

			if (j >= 0) //这里是特别语法处理，j>=0时t一定是BRACE_R，throw如果跟在try{}后面，那就不单独作为语句，而是作为try语句的一部分
			{
				if ("throw".equals(s1) && "try".equals(ws.getWord(j)))
					return false;
			}

			//右侧大括号后面跟着2元、3元运算符号的话，是不应该切开的，ARITHMETIC_KEYWORD一般都是自定义2元计算
			if (type == Words.ARITHMETIC_KEYWORD || type == Words.SEMICOLON || (type >= 2000 && type < 4000))
				return false;

			return (!"else".equals(s1) && !"catch".equals(s1));
		}
		
		return false;
	}
}
