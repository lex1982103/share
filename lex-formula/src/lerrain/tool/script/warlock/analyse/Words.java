package lerrain.tool.script.warlock.analyse;

import java.util.ArrayList;
import java.util.List;

/**
 * 词法分析
 * @author lerrain
 */
public class Words
{
	public static final int SYMBOL				= 10000; 
	public static final int WORD				= 11000;
	
	public static final int BRACE				= 10; //大括号 
	public static final int BRACE_R				= 11; //大括号 
	public static final int BRACKET				= 20; //中括号 
	public static final int BRACKET_R			= 21; //中括号 
	public static final int PRT					= 30; //小括号 
	public static final int PRT_R				= 31; //小括号
	
	public static final int NUMBER				= 50; 
	public static final int STRING				= 60;
	public static final int TRUE				= 61;
	public static final int FALSE				= 62;
	
	public static final int NEW					= 70; 
	public static final int FUNCTION_DIM		= 71; 
	public static final int SEMICOLON			= 80; //;
	public static final int POINT_KEY			= 90; 
	public static final int POINT_METHOD		= 91;
	public static final int POINT_KEY2			= 92;
	public static final int POINT_METHOD2		= 93;
	public static final int COMMA				= 100; //,
	public static final int QUESTMARK			= 101; //?
	public static final int COLON				= 102; //:
	public static final int COLON2				= 103; //:
	public static final int LET					= 110;
	public static final int AND					= 120; 
	public static final int OR					= 130; 
	public static final int REVISE				= 140; 
	public static final int EQUAL				= 150; 
	public static final int NOTEQUAL			= 160; 
	public static final int GREATER				= 170; 
	public static final int LESS				= 180; 
	public static final int GREATEREQUAL		= 190; 
	public static final int LESSEQUAL			= 200; 
	public static final int ADD					= 210; 
	public static final int SUB					= 220; 
	public static final int ADDLET				= 230; //+=
	public static final int SUBLET				= 240; //-=
	public static final int ADDADD				= 250; //++
	public static final int SUBSUB				= 260; //--
	public static final int POSITIVE			= 270; 
	public static final int NEGATIVE			= 280; 
	public static final int MULTIPLY			= 281;  
	public static final int DIVIDE				= 282; 
	public static final int MOD					= 283; 

	public static final int NULLPT				= 290; 
	public static final int KEYWORD				= 300;
	public static final int CLASS				= 310;

	public static final int VARIABLE			= 380; //变量
	public static final int FUNCTION			= 390; //函数
	public static final int METHOD				= 400; //方法
	public static final int KEY					= 410; //相对于前方值的KEY
	
	List c = new ArrayList();
	List d = new ArrayList();
	List e = new ArrayList();

	String scriptStr;

	public Words(String scriptStr)
	{
		this.scriptStr = scriptStr;
	}

	public String getScript()
	{
		return scriptStr;
	}
	
	public int size()
	{
		return c.size();
	}
	
	public boolean isEmpty()
	{
		return c.isEmpty();
	}
	
	public void setType(int index, int type)
	{
		d.set(index, new Integer(type));
	}
	
	public String getWord(int index)
	{
		return (String)c.get(index);
	}

	public int getLocation(int index)
	{
		return (Integer)e.get(index);
	}

	public void add(char word, int loc)
	{
		add(word + "", getSymbolType(word), loc);
	}
	
	public void add(String word, int type, int loc)
	{
		c.add(word);
		d.add(new Integer(type));
		e.add(new Integer(loc));
	}
	
	public void add(Words ws, int from, int to)
	{
		for (int i = from; i < to; i++)
			add(ws.getWord(i), ws.getType(i), ws.getLocation(i));
	}
	
	public Words cut(int from, int to)
	{
		Words ws = new Words(scriptStr);
		ws.add(this, from, to);
		
		return ws;
	}
	
	public Words cut(int from)
	{
		return cut(from, size());
	}
	
	public int getType(int index)
	{
		return (Integer)d.get(index);
	}
	
	public String toString()
	{
		return c.toString();
	}
	
	public static Words wordsOf(String text)
	{
		text = Text.cutComment(text);
		
		Words ws = new Words(text);
		
		int len = text.length();
		for (int i = 0; i < len; i++)
		{
			char c = text.charAt(i);
			
			if (c == '\"' || c == '\'') //如果是字符串，或单引号圈起来的
			{
				int j = Text.findInString(text, i + 1, c);
				String str = text.substring(i, j + 1);
				str = str.replaceAll("[\\\\][\"]", "\"");
				str = str.replaceAll("[\\\\][\']", "\'");
				str = str.replaceAll("[\\\\][\\\\]", "\\");
				str = str.replaceAll("[\\\\][n]", "\n");
				ws.add(str, STRING, i);
				i = j;
			}
			else if (isSpecialSymbol(c))
			{
				ws.add(c, i);
			}
			else if (isSymbol(c))
			{
				int x = i;
				for (; i < len - 1; i++)
				{
					c = text.charAt(i + 1);
					if (!isSymbol(c))
						break;
				}
				
				String w = text.substring(x, i + 1);
				ws.add(w, getSymbolType(w), x);
			}
			else if (isLetter(c)) //如果是单词
			{
				int x = i;
				for (; i < len - 1; i++)
				{
					c = text.charAt(i + 1);
					if (!isLetter(c) && !isNumber(c))
						break;
				}

				String w = text.substring(x, i + 1);
				ws.add(w, getWordType(w), x);
			}
			else if (isNumber(c)) //如果是数字(包括小数)，不接受C语言钟类似1.2f，3L这种形式的数字
			{
				int x = i;
				for (; i < len - 1; i++)
				{
					c = text.charAt(i + 1);
					if (!isNumber(c) && c != '.')
						break;
				}
				String number = text.substring(x, i + 1);
				//此处加入对数字的校验
				ws.add(number, NUMBER, x);
			}
		}
		
		ws.analyse();
		
		return ws;
	}
	
	/**
	 * 后续分析
	 * 1. WORD的前方如果不是POINT，并且后方不是PRT，那么这个WORD是个VARIABLE
	 * 2. WORD的前方如果不是POINT，并且后方是PRT，那么这个WORD是个FUNCTION
	 * 3. WORD的前方如果是POINT，并且后方不是PRT，那么这个WORD是个KEY（KEY的作用要视前方的值属性而定，可以是：Factors对象的name、Map的key、对象的成员变量等）
	 * 4. WORD的前方如果是POINT，并且后方是PRT，那么这个WORD是个METHOD
	 * 5. 正负号与加减号的判定，两者优先级不同
	 * 6. POINT的多种种类
	 */
	protected void analyse()
	{
		int size = size();
		for (int i = 0; i < size; i++)
		{
			int t = getType(i);
			if (t == WORD)
			{
				boolean isNew = i != 0 && getType(i - 1) == NEW;
				boolean isPoint = i != 0 && (getType(i - 1) == POINT_KEY || getType(i - 1) == POINT_KEY2);
				boolean isPrt = i != size - 1 && getType(i + 1) == PRT;
				
				if (isNew)
				{
					setType(i, CLASS);
				}
				else if (isPoint && isPrt)
				{
					setType(i, METHOD);
					if (getType(i - 1) == POINT_KEY2)
						setType(i - 1, POINT_METHOD2);
					else
						setType(i - 1, POINT_METHOD);
				}
				else if (isPoint && !isPrt)
				{
					setType(i, KEY);
				}
				else if (!isPoint && isPrt)
				{
					setType(i, FUNCTION);
				}
				else
				{
					setType(i, VARIABLE);
				}
			}
		}
	}
	
	private static int getSymbolType(char word)
	{
		if (word == ';')
			return SEMICOLON;
		else if (word == ',')
			return COMMA;
		else if (word == '(')
			return PRT;
		else if (word == ')')
			return PRT_R;
		else if (word == '[')
			return BRACKET;
		else if (word == ']')
			return BRACKET_R;
		else if (word == '{')
			return BRACE;
		else if (word == '}')
			return BRACE_R;
		
		return SYMBOL;
	}
	
	private static int getSymbolType(String symbol)
	{
		if ("=".equals(symbol))
			return LET;
		if ("&&".equals(symbol))
			return AND;
		if ("||".equals(symbol))
			return OR;
		if ("!".equals(symbol))
			return REVISE;
		if ("==".equals(symbol))
			return EQUAL;
		if ("!=".equals(symbol))
			return NOTEQUAL;
		if (">".equals(symbol))
			return GREATER;
		if ("<".equals(symbol))
			return LESS;
		if (">=".equals(symbol))
			return GREATEREQUAL;
		if ("<=".equals(symbol))
			return LESSEQUAL;
		if ("+".equals(symbol))
			return ADD;
		if ("-".equals(symbol))
			return SUB;
		if ("+=".equals(symbol))
			return ADDLET;
		if ("-=".equals(symbol))
			return SUBLET;
		if ("++".equals(symbol))
			return ADDADD;
		if ("--".equals(symbol))
			return SUBSUB;
		if ("*".equals(symbol))
			return MULTIPLY;
		if ("/".equals(symbol))
			return DIVIDE;
		if ("%".equals(symbol))
			return MOD;
		if ("?".equals(symbol))
			return QUESTMARK;
		if (":".equals(symbol))
			return COLON;
		if ("::".equals(symbol))
			return COLON2;
		if (".".equals(symbol))
			return POINT_KEY;
		if ("..".equals(symbol))
			return POINT_KEY2;

		return SYMBOL;
	}
	
	private static int getWordType(String word)
	{
		if ("lt".equals(word))
			return LESS;
		if ("le".equals(word))
			return LESSEQUAL;
		if ("gt".equals(word))
			return GREATER;
		if ("ge".equals(word))
			return GREATEREQUAL;
		if ("and".equals(word))
			return AND;
		if ("or".equals(word))
			return OR;
		if ("not".equals(word))
			return REVISE;
		
		if ("new".equals(word))
			return NEW;
		if ("function".equals(word))
			return FUNCTION_DIM;

		if ("null".equals(word))
			return NULLPT;
		if ("true".equals(word))
			return TRUE;
		if ("false".equals(word))
			return FALSE;
		
		if (",for,while,if,else,return,continue,break,var,throw,thread,synch,".indexOf("," + word + ",") >= 0)
			return KEYWORD;
		
		return WORD;
	}
	
	private static boolean isSpecialSymbol(char c)
	{
		return c == '(' || c == '[' || c == '{' || c == '}' || c == ']' || c == ')' || c == ',' || c == ';' || c == '@';
	}
	
	private static boolean isSymbol(char c)
	{
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '=' || c == '|' || c == '&' || c == '.' || c == '>' || c == '<' || c == '~' || c == '?' || c == ':' || c == '^' || c == '%' || c == '!';
	}
	
	private static boolean isLetter(char c)
	{
		return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' || c == '#');
	}
	
	private static boolean isNumber(char c)
	{
		return c >= '0' && c <= '9';
	}
}
