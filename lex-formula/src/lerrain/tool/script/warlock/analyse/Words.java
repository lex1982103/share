package lerrain.tool.script.warlock.analyse;

import lerrain.tool.script.SyntaxException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 词法分析
 * @author lerrain
 */
public class Words implements Serializable
{
	private static final long serialVersionUID = 718238024541280392L;

	public static final int SYMBOL				= 10000; 
	public static final int WORD				= 11000;
	public static final int CHINESE				= 12000;

	public static final int BRACE				= 10; //大括号 
	public static final int BRACE_R				= 11; //大括号 
	public static final int BRACKET				= 20; //中括号 
	public static final int BRACKET_R			= 21; //中括号 
	public static final int PRT					= 30; //小括号 
	public static final int PRT_R				= 31; //小括号
	public static final int AT					= 40; //@

	public static final int NUMBER				= 50; 
	public static final int STRING				= 60;
	public static final int TRUE				= 61;
	public static final int FALSE				= 62;
	
	public static final int NEW					= 70; 
	public static final int FUNCTION_DIM		= 71; 
	public static final int SEMICOLON			= 80; //;
	public static final int POINT_KEY			= 2090;
	public static final int POINT_METHOD		= 2091;
	public static final int POINT_KEY2			= 2092;
	public static final int POINT_METHOD2		= 2093;
	public static final int ARROW_RIGHT			= 2095;
	public static final int ARROW_LEFT			= 2096;
	public static final int COMMA				= 2100; //,
	public static final int QUESTMARK			= 2101; //?
	public static final int COLON				= 2102; //:
	public static final int COLON_SPLIT			= 2104; //json或map串中key后面的:  优先级低于=，似乎不那么合理
	public static final int COLON2				= 2103; //::  忘记做啥用的了，貌似也没地方用，考虑下掉
	public static final int COLON_FLAG			= 2105; //~:  忘记做啥用的了，貌似也没地方用，考虑下掉
	public static final int LET					= 2110;
	public static final int WAVE				= 2111; //~ 二元运算
    public static final int FASTLET				= 2112; //:=
	public static final int AND					= 2120;
	public static final int INTERSECTION        = 2121; //&
	public static final int OR					= 2130;
	public static final int UNION		        = 2131; //|
	public static final int REVISE				= 140; //!
	public static final int EQUAL				= 2150;
	public static final int APPROX				= 2155; //~= 约等
	public static final int NOTEQUAL			= 2160;
	public static final int GREATER				= 2170;
	public static final int GREATER2			= 2171;
	public static final int LESS				= 2180;
	public static final int LESS2				= 2181;
	public static final int GREATEREQUAL		= 2190;
	public static final int LESSEQUAL			= 2200;
	public static final int ADD					= 2210;
	public static final int SUB					= 2220;
	public static final int ADDLET				= 2230; //+=
	public static final int SUBLET				= 2240; //-=
	public static final int ADDADD				= 250; //++
	public static final int SUBSUB				= 260; //--
	public static final int POSITIVE			= 270; 
	public static final int NEGATIVE			= 280; 
	public static final int MULTIPLY			= 2281;
	public static final int DIVIDE				= 2282;
	public static final int MOD					= 2283;

	public static final int NULL				= 290;
	public static final int KEYWORD				= 300;
	public static final int ARITHMETIC_KEYWORD  = 301;
	public static final int CLASS				= 310;

	public static final int VARIABLE			= 380; //变量
	public static final int FUNCTION_BODY		= 390; //函数
	public static final int FUNCTION			= 391; //函数（包括参数部分）
	public static final int METHOD				= 400; //方法
	public static final int KEY					= 2410; //相对于前方值的KEY

//	List c = new ArrayList();
//	IntList d = new IntList();
//	IntList e = new IntList();

	List<Word> words = new ArrayList<>();

	int from = 0, num = 0;
	int end;

	Object scriptTag;
	String scriptStr;

	private static class Word implements Serializable
	{
		String c;
		int d, e;

		public Word(String c, int d, int e)
		{
			this.c = c;
			this.d = d;
			this.e = e;
		}
	}

	public Words(Object scriptTag, String scriptStr)
	{
		this.scriptTag = scriptTag;
		this.scriptStr = scriptStr;

		this.end = scriptStr.length();
	}

	public Object getScriptTag()
	{
		return scriptTag;
	}

	public void setScriptTag(Object scriptTag)
	{
		this.scriptTag = scriptTag;
	}

	public String getScript()
	{
		return scriptStr;
	}

	public String getCurrentScript()
	{
		int f = 0;
		if (from > 0)
			f = words.get(from).e;
		return scriptStr.substring(f, end);
	}

	public int size()
	{
		return num;
	}
	
	public boolean isEmpty()
	{
		return num == 0;
	}

	public int getFrom()
	{
		return from;
	}
	
	public void setType(int index, int type)
	{
		if (index >= num)
			throw new SyntaxException("out of range");

		words.get(from + index).d = type;
	}

	public void setWord(int index, String word)
	{
		if (index >= num)
			throw new SyntaxException("out of range");

		words.get(from + index).c = word;
	}

	public String getWord(int index)
	{
		if (index >= num)
			throw new SyntaxException("out of range");

		return words.get(from + index).c;
	}

	public int getLocation(int index)
	{
		if (index >= num)
			throw new SyntaxException("out of range");

		return words.get(from + index).e;
	}

	public void add(char word, int loc)
	{
		add(word + "", getSymbolType(word), loc);
	}
	
	public void add(String word, int type, int loc)
	{
		if (words.size() != num)
			throw new SyntaxException("out of range");

		words.add(new Word(word, type, loc));

		num++;
	}
	
//	public void add(Words ws, int from, int to)
//	{
//		for (int i = from; i < to; i++)
//			add(ws.getWord(i), ws.getType(i), ws.getLocation(i));
//	}

	public boolean isInWords(int pos)
	{
		return pos >= words.get(from).e && pos < end;
	}

	public int[] range()
	{
		return new int[] {words.get(from).e, end};
	}

	public int find(int pos)
	{
		if (!isInWords(pos))
			return -1;

		int i = 0;
		for (Word w : words)
		{
			if (w.e <= pos)
				return i;

			i++;
		}

		throw new SyntaxException("error in formula");
	}
	
	public Words cut(int from, int to)
	{
		Words ws = new Words(scriptTag, scriptStr);
		ws.words = this.words;
		ws.from = this.from + from;
		ws.num = to - from;

		ws.end = this.from + to < words.size() ? words.get(this.from + to).e : this.end;

		return ws;
	}

	/**
	 * 最小化内存占用的副本
	 * @return
	 */
	public Words less()
	{
		String str = scriptStr.substring(from == 0 ? 0 : getLocation( - 1), end);

		Words ws = new Words(null, str);
		for (int i = 0; i < num; i++)
			ws.words.add(words.get(from + i));
		ws.num = num;
		ws.end = str.length();

		return ws;
	}
	
	public Words cut(int from)
	{
		return cut(from, size());
	}
	
	public int getType(int index)
	{
		return words.get(from + index).d;
	}

	public static Words wordsOf(String text)
	{
		return wordsOf(null, text);
	}

	public static Words wordsOf(Object tag, String text)
	{
		text = Text.cutComment(text);
		
		Words ws = new Words(tag, text);
		
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
				str = str.replaceAll("[\\\\][\\\\]", "\\\\");
				str = str.replaceAll("[\\\\][n]", "\n");
				str = str.replaceAll("[\\\\][t]", "\t");
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
				int type = getSymbolType(w);
				//如果冒号前面是一个string/word/keyword，在前面是一个逗号，那么他就是json/map串中key后面的冒号，不是常规的?:
				//for(var x1,x2:m) 也符合上诉标准
				if (type == Words.COLON)
				{
					int t1 = ws.getType(ws.size() - 1);
					int t2 = ws.getType(ws.size() - 2);
					if (ws.size() >= 2 && (t1 == Words.STRING || t1 == Words.KEYWORD || t1 == Words.WORD) && (t2 == Words.BRACE || t2 == Words.COMMA))
						type = Words.COLON_SPLIT;
				}
				ws.add(w, type, x);
			}
			else if (isLetter(c)) //如果是单词
			{
				boolean chn = false;
				int x = i;
				for (; i < len - 1; i++)
				{
					c = text.charAt(i + 1);
					if (isChinese(c))
					{
						chn = true;
						continue;
					}
					if (!isLetter(c) && !isNumber(c))
						break;
				}

				String w = text.substring(x, i + 1);
				ws.add(w, chn ? CHINESE : getWordType(w), x);
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
			else if (isChinese(c)) //如果是中文
			{
				int x = i;
				for (; i < len - 1; i++)
				{
					c = text.charAt(i + 1);
					if (!isLetter(c) && !isNumber(c) && !isChinese(c))
						break;
				}

				String w = text.substring(x, i + 1);
				ws.add(w, CHINESE, x);
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
				//这里有问题，A.B()的形式，应该是A.B执行PoingKey运算取得一个Function，在run；而不是直接标记成PointMethod一起处理
//				else if (isPoint && isPrt)
//				{
//					setType(i, METHOD);
//					if (getType(i - 1) == POINT_KEY2)
//						setType(i - 1, POINT_METHOD2);
//					else
//						setType(i - 1, POINT_METHOD);
//				}
				else if (isPoint) //(isPoint && !isPrt)
				{
					setType(i, KEY);
				}
				else if (!isPoint && isPrt)
				{
					setType(i, FUNCTION_BODY);
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
        if (":=".equals(symbol))
            return FASTLET;
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
		if ("~=".equals(symbol))
			return APPROX;
		if (">".equals(symbol))
			return GREATER;
		if ("<".equals(symbol))
			return LESS;
		if (">=".equals(symbol) || "=>".equals(symbol))
			return GREATEREQUAL;
		if ("<=".equals(symbol))
			return LESSEQUAL;
		if (">>".equals(symbol))
			return GREATER2;
		if ("<<".equals(symbol))
			return LESS2;
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
		if ("~:".equals(symbol))
			return COLON_FLAG;
		if ("::".equals(symbol))
			return COLON2;
		if (".".equals(symbol))
			return POINT_KEY;
		if ("..".equals(symbol))
			return POINT_KEY2;
		if ("&".equals(symbol))
			return INTERSECTION;
		if ("|".equals(symbol))
			return UNION;
		if ("@".equals(symbol))
			return AT;
		if ("~".equals(symbol))
			return WAVE;
		if ("->".equals(symbol))
			return ARROW_RIGHT;
		if ("<-".equals(symbol))
			return ARROW_LEFT;

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
			return NULL;
		if ("true".equals(word))
			return TRUE;
		if ("false".equals(word))
			return FALSE;
		
		if (Syntax.keywords.containsKey(word)) //源生关键字，自定义关键字
			return KEYWORD;
		if (Expression.arithmeticStr.containsKey(word)) //自定义的运算符
			return ARITHMETIC_KEYWORD;
		
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

	private static boolean isChinese(char c)
	{
		return c >= 0x4e00 && c <= 0x9fa5;
	}
	
	private static boolean isNumber(char c)
	{
		return c >= '0' && c <= '9';
	}

//	public String getWordsKey()
//	{
//		MessageDigest messageDigest = null;
//		try
//		{
//			messageDigest = MessageDigest.getInstance("MD5");
//			messageDigest.reset();
//			messageDigest.update(scriptStr.getBytes("UTF-8"));
//		}
//		catch (Exception e)
//		{
//			throw new RuntimeException("md5 exception", e);
//		}
//
//		byte[] byteArray = messageDigest.digest();
//
//		StringBuffer md5StrBuff = new StringBuffer();
//		for (int i = 0; i < byteArray.length; i++)
//		{
//			String s = Integer.toHexString(0xFF & byteArray[i]);
//			if (s.length() == 1)
//				md5StrBuff.append("0");
//			md5StrBuff.append(s);
//		}
//
//		md5StrBuff.append('&');
//		md5StrBuff.append(from);
//		md5StrBuff.append('&');
//		md5StrBuff.append(num);
//
//		return md5StrBuff.toString();
//	}
}
