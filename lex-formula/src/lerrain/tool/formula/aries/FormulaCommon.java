package lerrain.tool.formula.aries;

import lerrain.tool.formula.FormulaException;

public class FormulaCommon
{
	public final static int CHAR_QUOTE		= 10;	//引号
	public final static int CHAR_NUMBER		= 2;	//数字
	public final static int CHAR_SYMBOL		= 1;	//符号

	private static final String[] keyWords = {"and", "or"};

	/**
	 * 判断一个单词是否为关键字
	 */
	public static boolean isKeyWord(String word)
	{
		for (int i=0;i<keyWords.length;i++)
		{
			if (keyWords[i].equals(word))
				return true;
		}
		
		return false;
	}
	
	/**
	 * 下一个引号的位置
	 * @param formula
	 * @param start
	 * @return
	 */
	public static int nextQuote(String formula, int start)
	{
		int s = -1;
		
		while (true)
		{
			int c1 = formula.indexOf("\'", start);
			int c2 = formula.indexOf("\"", start);
			
			s = c1 < 0 ? c2 : (c2 < 0 ? c1 : Math.min(c1, c2));
			
			if (s < 0 || formula.charAt(s - 1) != '\\')
				break;
			
			start = s + 1;
		}
		
		return s;
	}
	
	/**
	 * 查找与leftBracket位置的左括号匹配的右括号的位置
	 * 大中小括号都视作小括号检查，错误交叉的[(])将被视为(())。
	 */
	public static int rightBracket(String formula, int leftBracket) throws FormulaException
	{
		if (FormulaCommon.verify(formula, leftBracket) != 4)
			throw new FormulaException("查询匹配右括号时起始位置不是左括号。");

		int p = leftBracket;
		int len = formula.length();
		int deep = 1;
		while (p++ < len)
		{
			int r = FormulaCommon.verify(formula, p);
			if (r == 4)
			{
				deep++;
			}
			else if (r == 5)
			{
				deep--;
				if (deep == 0)
				{
					return p;
				}
			}
		}

		throw new FormulaException("括号匹配关系出错 -- 公式:" + formula + "，需要查询右匹配的左括号位置:" + leftBracket);
	}

	/**
	 * 取index位置左侧紧接的符号（忽略空格）
	 */
	public static char leftChar(String formula, int index)
	{
		char chr;
		int p = index;
		while (--p >= 0)
		{
			chr = formula.charAt(p);
			if (chr != ' ')
				return chr;
		}
		return 0;
	}
	
	/**
	 * 取index位置左侧紧接的单词（不包括index位置）
	 */
	public static String leftWord(String formula, int index)
	{
		return goWord(formula, index - 1, -1);
	}

	/**
	 * 从index向右取一个单词（包括index位置）
	 */
	public static String rightWord(String formula, int index)
	{
		return goWord(formula, index, 1);
	}

	public static String goWord(String formula, int index, int direction)
	{
		StringBuffer word = new StringBuffer();
		int p = index;
		int chrType = -1;
		while (p >= 0 && p < formula.length())
		{
			char chr = formula.charAt(p);
			int newType = verify(formula, p);
			p = p + direction;
			
			if (chrType == -1 && newType == 0)
				continue;

			if (chrType < 0) 
				chrType = newType;
			else
			{
				if (chrType == 0)
					break;
				else if (chrType == 4)
					break;
				else if (chrType == 5)
					break;
				else if (chrType == 1)
				{
					if(newType != 1)
						break;
				}
				else if (chrType == 2 || chrType == 3)
				{
					if(newType != 2 && newType != 3)
						break;
				}
			}

			if (direction < 0)
				word.insert(0, chr);
			else
				word.append(chr);
		}
		return word.toString();
	}
	
	/**
	 * 判断一个字符是否是符号
	 */
	public static boolean isSymbol(String word, int pos)
	{
		return verify(word, pos) == 1;
	}
	
	/**
	 * 判断一个字符的类型
	 */
	public static int verify(String word, int pos)
	{
		char chr = word.charAt(pos);
		
		if (chr == '.')
		{
			if (pos == word.length() - 1)
				return CHAR_NUMBER;
			else if (verify(word, pos + 1) == 3 || verify(word, pos + 1) == 0)
				return CHAR_SYMBOL;
			//仍然不全面，比如PLAN001.00001，这里的点就不是小数点。
			else if (pos > 0 && (verify(word, pos - 1) == 3 || verify(word, pos - 1) == 0))
				return CHAR_SYMBOL;
			else
				return CHAR_NUMBER;
		}
		else if (chr >= '0' && chr <= '9')
			return CHAR_NUMBER; //数字
		else if ((chr >= 'a' && chr <= 'z') || (chr >= 'A' && chr <= 'Z') || chr == '_')
			return 3; //英文
		else if (chr == ' ')
			return 0; //空格
		else if (chr == '(' || chr == '[' || chr == '{')
			return 4; //左括号
		else if (chr == ')' || chr == ']' || chr == '}')
			return 5; //右括号
		else if (chr == '\'' || chr == '\"')
			return CHAR_QUOTE;
		else
			return CHAR_SYMBOL; //符号
	}
	
	public static void main(String[] arg)
	{
		String str = "100-50*go(LEX* 2)/1000";
		for(int i=0;i<str.length();i++)
			System.out.println(leftWord(str,i));
	}
}
