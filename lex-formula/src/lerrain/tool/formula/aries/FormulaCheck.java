package lerrain.tool.formula.aries;


/**
 * 公式校验类
 * @author lerrain
 *
 * 修改历史：
 * 
 * 配合数据参数的缓冲部分，所有的缓冲移至那里，这里必须关闭，因为清除缓存步骤只针对那里。
 * - lerrain 2011/06/02
 */

public class FormulaCheck
{
	String formula;
	
	StringBuffer message;
	
	public FormulaCheck(String formula)
	{
		this.formula = formula;
		
		message = new StringBuffer("公式: " + formula + " 检查结果：");
	}
	
	public boolean check()
	{
		if (formula == null)
			return true;
		
		formula = formula.trim();
		
		if ("".equals(formula))
			return true;
		
		if (!checkBracket())
			return false;
		
		return true;
	}
	
	private boolean checkBracket()
	{
		StringBuffer brackets = new StringBuffer();
		
		char lastQuotes = '-';
		
		boolean skip = false;
		
		for (int i=0;i<formula.length();i++)
		{
			char c = formula.charAt(i);
			
			if (isQuotes(i))
			{
				if (isQuotes(lastQuotes))
				{
					if (lastQuotes != c)
					{
						message.append("公式内引号不匹配。");
						return false;
					}
					else
					{
						skip = false;
						lastQuotes = '-';
					}
				}
				else
				{
					skip = true;
					lastQuotes = c;
				}
			}
			
			if (skip)
				continue;
			
			if (isLeftBracket(i))
			{
				brackets.append(c);
			}
			else if (isRightBracket(i))
			{
				if (brackets.length() == 0)
				{
					message.append("公式不能以右括号开头。");
					return false;
				}
				
				char left = brackets.charAt(brackets.length() - 1);
				
				if (!isBracketMatch(left, c))
				{
					message.append("公式里存在无法匹配的左右括号。");
					return false;
				}
				else
					brackets.deleteCharAt(brackets.length() - 1);
			}
		}
		
		if (skip)
		{
			message.append("缺少结尾的引号。");
			return false;
		}	
		
		return true;
	}
	
	private boolean isQuotes(int index)
	{
		char c = formula.charAt(index);
		
		if (c == '\'' || c == '\"')
		{
			if (index == 0)
				return true;
			else
			{
				char l = formula.charAt(index - 1);
				return (l != '\\'); //连续两个反斜杠的情况就不考虑了。
			}
		}
		
		return false;
	}
	
	private boolean isQuotes(char c)
	{
		return (c == '\'' || c == '\"');
	}
	
	private boolean isLeftBracket(int index)
	{
		char c = formula.charAt(index);
		
		return (c == '(' || c == '[' || c == '{');
	}
	
	private boolean isRightBracket(int index)
	{
		char c = formula.charAt(index);
		
		return (c == ')' || c == ']' || c == '}');
	}
	
	private boolean isBracketMatch(char left, char right)
	{
		return (left == '(' && right == ')') || (left == '[' && right == ']') || (left == '[' && right == ']');
	}
	
	public static void main(String[] arg) throws Exception
	{
//		FormulaCheck fc = new FormulaCheck("' 3、您是否吸烟？若“是”，已吸烟（    '+SMOKING_DATE+'   ）年，每天（     '+SMOKING_DAY_BRANCH+'  ）支？\n若已停止吸烟，停止吸烟时间（   '+STOP_SMOKING_DATE+'  ）；停止原因（'+STOP_SMOKING_REASON+'）'");
//		
//		System.out.println(fc.check());
//		System.out.println(fc.getMessage());
		
	}

	public String getMessage()
	{
		return message.toString();
	}
}
