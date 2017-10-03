package lerrain.tool.formula.aries;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lerrain.tool.formula.FormulaException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.aries.arithmetic.Arithmetic;
import lerrain.tool.formula.aries.arithmetic.Array;
import lerrain.tool.formula.aries.arithmetic.Constant;
import lerrain.tool.formula.aries.arithmetic.ArrayNew;
import lerrain.tool.formula.aries.arithmetic.FunctionMethod;
import lerrain.tool.formula.aries.arithmetic.FunctionAppend;
import lerrain.tool.formula.aries.arithmetic.Variable;

public class FormulaCompiler
{
	public final static int WORD_TYPE_CONSTANT		= 10;	//常量
	public final static int WORD_TYPE_VARIABLE		= 11;	//变量
	public final static int WORD_TYPE_FORMULA		= 20;	//编译完成的公式片断
	public final static int WORD_TYPE_SYMBOL		= 30;	//运算符号
	public final static int WORD_TYPE_FUNCTION		= 40;	//函数
	public final static int WORD_TYPE_METHOD		= 50;	//方法
	public final static int WORD_TYPE_ARRAY			= 60;	//数组
	public final static int WORD_TYPE_PARAMETER		= 41;	//函数/方法/数组的参数
	
	private String formula;
	
	private List subFormula;
	private List subFormulaResult;
	
	public FormulaCompiler(String formula)
	{
		this.formula = formula;
	}

	public Formula compile() throws FormulaException
	{
		FormulaCheck c = new FormulaCheck(formula);
		
		if (!c.check())
			throw new FormulaException(c.getMessage()); 
		
		try
		{
			return analyze();
		}
		catch (Exception e)
		{
			throw new FormulaException(e.getMessage() + "<formula:" + formula + ">", e);
		}
	}
	
	private Formula analyze() throws Exception
	{
		formulaSplit();
		
		int index = 0;
		int subFormulaIndex = 0;
		int[] subFormulaLR = null;
		if (subFormula != null && !subFormula.isEmpty())
			subFormulaLR = (int[])subFormula.get(subFormulaIndex);
		
		Formula block = null;
		List wordList = new ArrayList();
		List descList = new ArrayList(); //10常量，11变量，20编译完成的公式片断，30运算符号，40函数，41函数参数
		Map compute = new HashMap();
		
		while(index < formula.length())
		{
			if (subFormulaLR != null)
			{
				if (index > subFormulaLR[1])
				{
					if (++subFormulaIndex < subFormula.size())
						subFormulaLR = (int[])subFormula.get(subFormulaIndex);
					else
						subFormulaLR = null;
					continue;
				}
				else if (index >= subFormulaLR[0])
				{
					if (index == subFormulaLR[0])
					{
						wordList.add(formula.substring(subFormulaLR[0], subFormulaLR[1]+1));
						descList.add(new Integer(WORD_TYPE_FORMULA));
						compute.put(new Integer(wordList.size() - 1), subFormulaResult.get(subFormulaIndex));
					}
					index = subFormulaLR[1] + 1;
					continue;
				}
			}
			
			char chr = formula.charAt(index);
			if (chr == ' ')
				index++;
			else if (chr == '\'')
			{
				int next = FormulaCommon.nextQuote(formula, index + 1);
				if (next < 0)
				{
					
				}
				else
				{
					String word = formula.substring(index, next + 1).replaceAll("\\'", "'");
					wordList.add(word);
					descList.add(new Integer(WORD_TYPE_CONSTANT));
					compute.put(new Integer(wordList.size() - 1), Constant.constantOf(word));
					index = next + 1;
				}
			}
			else if (chr == '(') //方法
			{
				int type = ((Integer)descList.get(descList.size() - 1)).intValue();
				//这里方法把方法名定义为WORD_TYPE_METHOD，参数部分定义为WORD_TYPE_PARAMETER，与数组的处理办法不同
				if (type == WORD_TYPE_CONSTANT) //point运算的右侧会设定为字符串型的常量
				{
					descList.set(descList.size() - 1, new Integer(WORD_TYPE_METHOD));
				}
				else if (type == WORD_TYPE_VARIABLE) //左侧是一个变量的话，那就认为这是个自定义函数。
				{
					descList.set(descList.size() - 1, new Integer(WORD_TYPE_FUNCTION));
				}
				int r = FormulaCommon.rightBracket(formula, index);
				wordList.add(formula.substring(index, r + 1));
				descList.add(new Integer(WORD_TYPE_PARAMETER));
				index = r + 1;
			}
			else if (chr == '[') //数组
			{
				//这里数组把[]中的参数部分定义为WORD_TYPE_ARRAY，与函数的处理办法不同
//				int type = ((Integer)descList.get(descList.size() - 1)).intValue();
//				if (type == WORD_TYPE_VARIABLE || type == WORD_TYPE_CONSTANT) //point运算的右侧会设定为字符串型的常量
//				{
//					descList.set(descList.size() - 1, new Integer(WORD_TYPE_ARRAY));
//				}
				int r = FormulaCommon.rightBracket(formula, index);
				wordList.add(formula.substring(index, r + 1));
				//descList.add(new Integer(WORD_TYPE_PARAMETER));
				descList.add(new Integer(WORD_TYPE_ARRAY));
				index = r + 1;
			}
			else
			{
				String word = FormulaCommon.rightWord(formula, index);
				//wordMap.put(new Integer(index), word);
				wordList.add(word);

				Arithmetic arithmetic = ArithmeticMgr.getArithmetic(word);
				if (arithmetic == null)
				{
					/**
					 * 2013/03/22 李新豪
					 * 这里调整了判断次序
					 */
					if (wordList.size() > 2 && ".".equals(wordList.get(wordList.size() - 2)))
					{
						/*
						 * point这个运算的右侧是不做计算的，直接原值
						 */
						descList.add(new Integer(WORD_TYPE_CONSTANT));
						compute.put(new Integer(wordList.size() - 1), new Constant(word));							
					}
					else if (FormulaCommon.verify(word, 0) == FormulaCommon.CHAR_NUMBER)
					{
						descList.add(new Integer(WORD_TYPE_CONSTANT));
						compute.put(new Integer(wordList.size() - 1), Constant.constantOf((String)word));
					}
					else
					{
						descList.add(new Integer(WORD_TYPE_VARIABLE));
						compute.put(new Integer(wordList.size() - 1), new Variable(word));
					}
//					if (FormulaCommon.verify(word, 0) == FormulaCommon.CHAR_NUMBER)
//					{
//						descList.add(new Integer(WORD_TYPE_CONSTANT));
//						compute.put(new Integer(wordList.size() - 1), new Constant((String)word));
//					}
//					else if (wordList.size() > 2 && ".".equals(wordList.get(wordList.size() - 2)))
//					{
//						/*
//						 * point这个运算的右侧是不做计算的，直接原值
//						 */
//						descList.add(new Integer(WORD_TYPE_CONSTANT));
//						compute.put(new Integer(wordList.size() - 1), new Constant((String)("'"+word+"'")));							
//					}
//					else
//					{
//						descList.add(new Integer(WORD_TYPE_VARIABLE));
//						compute.put(new Integer(wordList.size() - 1), new Variable(word));
//					}
				}
				else if (arithmetic.isFuntion())
				{
					descList.add(new Integer(WORD_TYPE_FUNCTION));
				}
				else
				{
					descList.add(new Integer(WORD_TYPE_SYMBOL));
				}

				index += word.length();
			}
		}
		
		for (int k=0;k<wordList.size();k++)
		{
			int priority = -1;
			int pos = -1;
			for (int i=0;i<wordList.size();i++)
			{
				int type = ((Integer)descList.get(i)).intValue();
				String word = (String)wordList.get(i);
				if (type == WORD_TYPE_FUNCTION || type == WORD_TYPE_METHOD)
				{
					//priority = 1000;
					pos = i;
					break;
				}
				else if (type == WORD_TYPE_ARRAY)
				{
					if (600 > priority)
					{
						priority = 600;
						pos = i;
					}
				}
				else if (type == WORD_TYPE_SYMBOL)
				{
					Arithmetic arithmetic = ArithmeticMgr.getArithmetic(word);
					if (arithmetic != null)
					{
						int p = arithmetic.getPriority();
						if (p > priority)
						{
							priority = p;
							pos = i;
						}
					}
				}
			}
			
			if (pos < 0) break;
			{
				int type = ((Integer)descList.get(pos)).intValue();
				String word = (String)wordList.get(pos);
				if (type == WORD_TYPE_SYMBOL)
				{
					Arithmetic arithmetic = ArithmeticMgr.getArithmetic(word);
					if (arithmetic != null)
					{
						int left, right;
						for(left=pos-1;left>=0;left--)
						{
							int type2 = ((Integer)descList.get(left)).intValue();
							if (type2 == WORD_TYPE_CONSTANT || type2 == WORD_TYPE_VARIABLE || type2 == WORD_TYPE_FORMULA)
								break;
							else if (type2 != 0)
								throw new FormulaException("运算符左侧出现没有计算的公式片断。运算符："+word+"，左侧公式片断："+wordList.get(left)+"，左侧数据类型："+type2);
								
						}
						for(right=pos+1;right<descList.size();right++)
						{
							int type2 = ((Integer)descList.get(right)).intValue();
							if (type2 == WORD_TYPE_CONSTANT || type2 == WORD_TYPE_VARIABLE || type2 == WORD_TYPE_FORMULA)
								break;
							else if (type2 != 0)
								throw new FormulaException("运算符右侧出现没有计算的公式片断。\n公式："+formula+"\n运算符："+word+"，右侧公式片断："+wordList.get(right)+"，右侧数据类型："+type2);
								
						}
						List paramList = new ArrayList();
						paramList.add(compute.get(new Integer(left)));
						paramList.add(compute.get(new Integer(right)));
						
						Class cls = arithmetic.getClass();
						Constructor constructor = cls.getConstructor(new Class[] {List.class});
						block = (Formula)constructor.newInstance(new Object[] {paramList});

						//wordList.set(i, word + "(" + paramStr + ")");
						//wordList.set(i + 1, "");
						descList.set(pos, new Integer(20));
						descList.set(left, new Integer(0));
						descList.set(right, new Integer(0));
						compute.put(new Integer(pos), block);
					}
				}
				else if (type == WORD_TYPE_FUNCTION)
				{
					Arithmetic arithmetic = ArithmeticMgr.getArithmetic(word);
					if (arithmetic != null) //标准函数
					{
						Class cls = arithmetic.getClass();
						//List paramList = new ArrayList();
						String paramStr = (String)wordList.get(pos + 1);
						paramStr = paramStr.substring(1, paramStr.length() - 1);
						List paramList = splitParameter(paramStr);
//						if (paramStr.indexOf(",") < 0)
//						{
//							paramList.add(new FormulaCompiler(paramStr).compile());
//						}
//						else
//						{
//							int begin = 0;
//							for(int j=0;j<paramStr.length();j++)
//							{
//								char chr = paramStr.charAt(j);
//								if (chr == ',')
//								{
//									paramList.add(new FormulaCompiler(paramStr.substring(begin, j)).compile());
//									begin = j + 1;
//								}
//								else if (chr == '(')
//								{
//									j = FormulaCommon.rightBracket(paramStr, j);
//								}
//							}
//							paramList.add(new FormulaCompiler(paramStr.substring(begin, paramStr.length())).compile());
//						}
						Constructor constructor = cls.getConstructor(new Class[] {List.class});
						block = (Formula)constructor.newInstance(new Object[] {paramList});
						
						//wordList.set(i, word + "(" + paramStr + ")");
						//wordList.set(i + 1, "");
						descList.set(pos, new Integer(20));
						descList.set(pos + 1, new Integer(0));
						compute.put(new Integer(pos), block);
					}
					else //自定义函数
					{
						String paramStr = (String)wordList.get(pos + 1);
						paramStr = paramStr.substring(1, paramStr.length() - 1);
						FunctionAppend fu = new FunctionAppend(word, split(paramStr));
						descList.set(pos, new Integer(20));
						descList.set(pos + 1, new Integer(0));
						compute.put(new Integer(pos), fu);
					}
				}
				else if (type == WORD_TYPE_METHOD)
				{
					String paramStr = (String)wordList.get(pos + 1);
					paramStr = paramStr.substring(1, paramStr.length() - 1);

					if (!paramStr.equals(""))
						paramStr = "" + paramStr;
					
					block = (Formula)new FunctionMethod(word, splitParameter(paramStr));
					descList.set(pos, new Integer(20));
					descList.set(pos + 1, new Integer(0));
					compute.put(new Integer(pos), block);
				}
				else if (type == WORD_TYPE_ARRAY)
				{
					String paramStr = (String)wordList.get(pos);
					paramStr = paramStr.substring(1, paramStr.length() - 1);

					int left;
					for(left=pos-1;left>=0;left--)
					{
						int type2 = ((Integer)descList.get(left)).intValue();
						if (type2 == WORD_TYPE_CONSTANT || type2 == WORD_TYPE_VARIABLE || type2 == WORD_TYPE_FORMULA)
							break;
						else if (type2 != 0)
							left = -1;
							//throw new FormulaTranslateException("运算符左侧出现没有计算的公式片断。运算符："+word+"，左侧公式片断："+wordList.get(left)+"，左侧数据类型："+type2);
							
					}
					
					//数组取值
					if (left >= 0)
					{
						block = (Formula)new Array((Formula)compute.get(new Integer(left)), splitParameter(paramStr));
						
						descList.set(pos, new Integer(WORD_TYPE_FORMULA));
						descList.set(left, new Integer(0));
						compute.put(new Integer(pos), block);
					}
					//[1,2,3,4]自造数组
					else
					{
						List v = splitParameter(paramStr);
						block = new ArrayNew(v == null ? new ArrayList() : v);
						descList.set(pos, new Integer(WORD_TYPE_FORMULA));
						compute.put(new Integer(pos), block);
					}
				}
			}
		}
		
		if (block == null && wordList.size() == 1)
		{
			String word = (String)wordList.get(0);
			int type = ((Integer)descList.get(0)).intValue();
			if (type == 10)
				block = Constant.constantOf((String)word);
			else if (type == 11)
				block = new Variable(word);
			else if (type == 20)
				block = (Formula)compute.get(new Integer(0));
			else
				throw new FormulaException("公式编译错误。");
		}

		return block;
	}
	
	private Formula[] split(String paramStr)
	{
		List l = splitParameter(paramStr);
		if (l == null)
			return null;
		
		Formula[] f = new Formula[l.size()];
		int i = 0;
		Iterator iter = l.iterator();
		while (iter.hasNext())
		{
			f[i++] = (Formula)iter.next();;
		}
		
		return f;
	}
	
	/**
	 * 参数分割
	 * @param paramStr
	 * @return
	 * @throws FormulaException
	 */
	private List splitParameter(String paramStr)
	{
		if (paramStr == null || "".equals(paramStr.trim()))
			return null;
		
		List paramList = new ArrayList();
		
		if (paramStr.indexOf(",") < 0)
		{
			paramList.add(new FormulaCompiler(paramStr).compile());
		}
		else
		{
			int begin = 0;
			for(int j=0;j<paramStr.length();j++)
			{
				char chr = paramStr.charAt(j);
				if (chr == ',')
				{
					paramList.add(new FormulaCompiler(paramStr.substring(begin, j)).compile());
					begin = j + 1;
				}
				else if (FormulaCommon.verify(paramStr, j) == 4) //括号
				{
					j = FormulaCommon.rightBracket(paramStr, j);
				}
				else if (FormulaCommon.verify(paramStr, j) == FormulaCommon.CHAR_QUOTE)
				{
					int nq = FormulaCommon.nextQuote(paramStr, j + 1);
					if (nq >= 0)
						j = nq;
					else
					{
						//error 引号不匹配
					}
				}
			}
			paramList.add(new FormulaCompiler(paramStr.substring(begin, paramStr.length())).compile());
		}
		
		return paramList;
	}
	
	/**
	 * 括号分离
	 * 仅分离常规的小括号，无视中括号大括号以及函数数组后的括号。
	 * 括号前只能是关键字或运算符号，否则就是函数，函数视作一个整体，不采用括号递归运算。
	 */
	private void formulaSplit() throws Exception
	{
		int p = formula.indexOf("(");
		while (p >= 0)
		{
			int r = FormulaCommon.rightBracket(formula, p);
			String word = FormulaCommon.leftWord(formula, p);
			if ("".equals(word) || FormulaCommon.isKeyWord(word) || FormulaCommon.isSymbol(word, 0))
			{
				Formula fc = new FormulaCompiler(formula.substring(p + 1, r)).compile();
				
				if (subFormula == null)
					subFormula = new ArrayList();
				if (subFormulaResult == null)
					subFormulaResult = new ArrayList();
				
				int[] lr = new int[2];
				lr[0] = p;
				lr[1] = r;
				subFormula.add(lr);
				subFormulaResult.add(fc);
			}
			
			p = formula.indexOf("(", r + 1);
		}
	}
	
	public String toString()
	{
		return formula;
	}
}
