package lerrain.tool.script.warlock.analyse;

import lerrain.tool.script.ArithmeticFactory;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Chinese;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.statement.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 表达式处理
 * @author lerrain
 */
public class Expression
{
	public static final int PRIORITY_MIN		= 0;
	public static final int PRIORITY_MAX		= 100000;

//	private static Map map1 = null;
//	private static Map map2 = null;
//	
//	private static synchronized void prepare()
//	{
//		if (map1 != null)
//			return;
//		
//		map1 = new HashMap();
//		map2 = new HashMap();
//		
//		addArithmetic(Words.LET, 50, ArithmeticLet.class);
//		addArithmetic(Words.ADDLET, 50, ArithmeticAddLet.class);
//		addArithmetic(Words.SUBLET, 50, ArithmeticSubLet.class);
//		addArithmetic(Words.COMMA, 60, ArithmeticComma.class);
//		addArithmetic(Words.QUESTMARK, 100, ArithmeticQuestMark.class);
//		addArithmetic(Words.COLON, 101, ArithmeticColon.class);
//
//		addArithmetic(Words.POINT_KEY, 1300, ArithmeticPointKey.class);
//		addArithmetic(Words.POINT_METHOD, 1300, ArithmeticPointMethod.class);
//		addArithmetic(Words.BRACKET, 1300, ArithmeticArray.class); 
//		addArithmetic(Words.FUNCTION, 1300, ArithmeticFunction.class);
//
//		addArithmetic(Words.POSITIVE, 1200, ArithmeticAdd.class);
//		addArithmetic(Words.NEGATIVE, 1200, ArithmeticSub.class);
//		addArithmetic(Words.ADDADD, 1200, ArithmeticAddAdd.class);
//		addArithmetic(Words.SUBSUB, 1200, ArithmeticSubSub.class);
//		addArithmetic(Words.ADD, 1000, ArithmeticAdd.class);
//		addArithmetic(Words.SUB, 1000, ArithmeticSub.class);
//		addArithmetic(Words.MULTIPLY, 1010, ArithmeticMultiply.class);
//		addArithmetic(Words.DIVIDE, 1010, ArithmeticDivide.class);
//		addArithmetic(Words.MOD, 1010, ArithmeticMod.class);
//	
//		addArithmetic(Words.AND, 300, ArithmeticAnd.class);
//		addArithmetic(Words.OR, 200, ArithmeticOr.class);
//		addArithmetic(Words.REVISE, 1200, ArithmeticNot.class);
//		addArithmetic(Words.EQUAL, 700, ArithmeticEqual.class);
//		addArithmetic(Words.NOTEQUAL, 700, ArithmeticNotEqual.class);
//		addArithmetic(Words.GREATER, 800, ArithmeticGreater.class);
//		addArithmetic(Words.LESS, 800, ArithmeticLess.class);
//		addArithmetic(Words.GREATEREQUAL, 800, ArithmeticGreaterEqual.class);
//		addArithmetic(Words.LESSEQUAL, 800, ArithmeticLessEqual.class);
//
////		addArithmetic(Words.BRACE, 2000, StatementParagraph.class);
//		addArithmetic(Words.PRT, 2000, ArithmeticParentheses.class);
//	}

	public static Code expressionOf(Words ws)
	{
		if (ws.size() == 0)
			return null;

		if (ws.size() == 1)
		{
			int t = ws.getType(0);
			if (t == Words.NULL || t == Words.NUMBER || t == Words.STRING || t == Words.TRUE || t == Words.FALSE)
				return new Constant(ws);
			if (t == Words.VARIABLE || t == Words.WORD)
				return new Variable(ws);
			if (t == Words.CHINESE)
				return new Chinese(ws);
		}
		
		int p1 = 10000;
		int pos = -1;
		
		for (int i = ws.size() - 1; i >= 0; i--)
		{
			int t = ws.getType(i);
			
			if (t == Words.BRACE_R || t == Words.BRACKET_R || t == Words.PRT_R)
			{
				int j = i;
				i = Syntax.findLeftBrace(ws, i - 1);

				if (i < 0)
					throw new SyntaxException(ws, j, "无法找到匹配的左括号");

				t = ws.getType(i);
			}

			int p2 = t == Words.ARITHMETIC_KEYWORD || t == Words.KEYWORD ? getPriority(ws.getWord(i)) : getPriority(t);

			boolean right = t == Words.QUESTMARK; //这个运算是右边优先，和其他的相反
			boolean match = right ? p2 <= p1 : p2 < p1;

			if (p2 > 0 && match)
			{
				p1 = p2;
				pos = i;
			}
		}

		if (pos < 0)
			throw new SyntaxException(ws, "无法处理的运算");

		return arithmeticOf(ws.getType(pos), ws, pos);
		
//		if (pos >= 0)
//		{
//			Code code = arithmeticOf(ws.getType(pos), ws, pos);
//
//			if (Script.compileListener != null)
//			{
//				code = Script.compileListener.compile(CompileListener.EXPRESSION, code);
//
//				if (!(code instanceof ArithmeticFunctionBody) && code.isFixed())
//					code = Script.compileListener.compile(CompileListener.SIMPLIFY, code);
//			}
//
//			return code;
//		}
//
//		throw new SyntaxException(ws, "无法处理的运算");
	}
	
//	private static int getPriority(int arithmetic)
//	{
//		Integer p = (Integer)map1.get(new Integer(arithmetic));
//		return p == null ? -1 : p.intValue();
//	}
//	
//	private static void addArithmetic(int arithmetic, int priority, Class cls)
//	{
//		map1.put(new Integer(arithmetic), new Integer(priority));
//		map2.put(new Integer(arithmetic), cls);
//	}
//	
//	private static Code arithmeticOf(int arithmetic, Words ws, int pos)
//	{
//		Code exp = null;
//		try
//		{
//			Constructor c = ((Class)map2.get(new Integer(arithmetic))).getConstructors()[0];
//			exp = (Code)c.newInstance(new Object[] {ws, new Integer(pos)});
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			System.out.println("ARITHMETIC<" + arithmetic + "> not found");
//		}
//		return exp;
//	}

	static Map<Integer, ArithmeticFactory> arithmeticInt = new HashMap();

	static Map<String, ArithmeticFactory> arithmeticStr = new HashMap();

	public static void register(int arithmetic, ArithmeticFactory af)
	{
		arithmeticInt.put(arithmetic, af);
	}

	public static void register(String keyword, ArithmeticFactory af)
	{
		arithmeticStr.put(keyword, af);
	}

	private static Code arithmeticOf(int arithmetic, Words ws, int pos)
	{
		if (arithmeticInt.containsKey(arithmetic))
			return arithmeticInt.get(arithmetic).buildArithmetic(ws, pos);

		if (arithmetic == Words.LET) return new ArithmeticLet(ws, pos);
		if (arithmetic == Words.ADDLET) return new ArithmeticAddLet(ws, pos);
		if (arithmetic == Words.SUBLET) return new ArithmeticSubLet(ws, pos);
		if (arithmetic == Words.COMMA) return new ArithmeticComma(ws, pos);
		if (arithmetic == Words.QUESTMARK) return new ArithmeticQuestMark(ws, pos);
//		if (arithmetic == Words.COLON || arithmetic == Words.COLON_SPLIT) return new ArithmeticColon(ws, pos);
		if (arithmetic == Words.COLON_SPLIT) return new ArithmeticEntry(ws, pos);
		if (arithmetic == Words.COLON || arithmetic == Words.COLON2 || arithmetic == Words.COLON_FLAG) return new ArithmeticCode(ws, pos);

		if (arithmetic == Words.NEW) return new ArithmeticNew(ws, pos);
		if (arithmetic == Words.FUNCTION_DIM) return new ArithmeticFunctionDim(ws, pos);

		if (arithmetic == Words.POINT_KEY || arithmetic == Words.POINT_KEY2) return new ArithmeticPointKey(ws, pos);
		if (arithmetic == Words.POINT_METHOD || arithmetic == Words.POINT_METHOD2) return new ArithmeticPointMethod(ws, pos);
		if (arithmetic == Words.BRACKET) return ArithmeticArray.arrayOf(ws, pos);
		if (arithmetic == Words.BRACE) return new ArithmeticBrace(ws, pos);
		if (arithmetic == Words.FUNCTION_BODY) return new ArithmeticFunctionBody(ws, pos);

		if (arithmetic == Words.POSITIVE) return new ArithmeticAdd(ws, pos);
		if (arithmetic == Words.NEGATIVE) return new ArithmeticSub(ws, pos);
		if (arithmetic == Words.ADDADD) return new ArithmeticAddAdd(ws, pos);
		if (arithmetic == Words.SUBSUB) return new ArithmeticSubSub(ws, pos);
		if (arithmetic == Words.ADD) return new ArithmeticAdd(ws, pos);
		if (arithmetic == Words.SUB) return new ArithmeticSub(ws, pos);
		if (arithmetic == Words.MULTIPLY) return new ArithmeticMultiply(ws, pos);
		if (arithmetic == Words.DIVIDE) return new ArithmeticDivide(ws, pos);
		if (arithmetic == Words.MOD) return new ArithmeticMod(ws, pos);

		if (arithmetic == Words.INTERSECTION) return new ArithmeticBitAnd(ws, pos);
		if (arithmetic == Words.UNION) return new ArithmeticBitOr(ws, pos);
		if (arithmetic == Words.AND) return new ArithmeticAnd(ws, pos);
		if (arithmetic == Words.OR) return new ArithmeticOr(ws, pos);
		if (arithmetic == Words.REVISE) return new ArithmeticNot(ws, pos);
		if (arithmetic == Words.EQUAL) return new ArithmeticEqual(ws, pos);
		if (arithmetic == Words.APPROX) return new ArithmeticApprox(ws, pos);
		if (arithmetic == Words.NOTEQUAL) return new ArithmeticNotEqual(ws, pos);
		if (arithmetic == Words.GREATER) return new ArithmeticGreater(ws, pos);
		if (arithmetic == Words.LESS) return new ArithmeticLess(ws, pos);
		if (arithmetic == Words.GREATEREQUAL) return new ArithmeticGreaterEqual(ws, pos);
		if (arithmetic == Words.LESSEQUAL) return new ArithmeticLessEqual(ws, pos);
		
		if (arithmetic == Words.PRT)
		{
			if (pos > 0)
				return new ArithmeticFunction(ws, pos);
			return new ArithmeticParentheses(ws, pos);
		}

		if (arithmetic == Words.ARITHMETIC_KEYWORD || arithmetic == Words.KEYWORD)
		{
			String word = ws.getWord(pos);

			if (arithmeticStr.containsKey(word))
				return arithmeticStr.get(word).buildArithmetic(ws, pos);

			if ("throw".equals(word))
				return new ArithmeticThrow(ws, pos);
			if ("catch".equals(word))
				return new ArithmeticCatch(ws, pos);
		}
		
		throw new SyntaxException(ws, pos, "ARITHMETIC<" + arithmetic + "> not found");
	}

	private static int getPriority(String word)
	{
		if (arithmeticStr.containsKey(word))
		{
			int pr = arithmeticStr.get(word).getPriority();
			if (pr >= 0)
				return pr;
		}

		if ("throw".equals(word))
			return 10;
		if ("catch".equals(word))
			return 40;

		return -1;
	}
	
	private static int getPriority(int arithmetic)
	{
		if (arithmeticInt.containsKey(arithmetic))
		{
			int pr = arithmeticInt.get(arithmetic).getPriority();
			if (pr >= 0) //小于0代表不变
				return pr;
		}

		if (arithmetic == Words.COLON2) return 15;
		if (arithmetic == Words.COLON_FLAG) return 15;
		if (arithmetic == Words.COMMA) return 20;
		if (arithmetic == Words.COLON_SPLIT) return 25;
		if (arithmetic == Words.LET) return 30;

		if (arithmetic == Words.ADDLET) return 50;
		if (arithmetic == Words.SUBLET) return 50;
		if (arithmetic == Words.QUESTMARK) return 100;
		if (arithmetic == Words.COLON) return 101;

		if (arithmetic == Words.POINT_KEY) return 1300;
		if (arithmetic == Words.POINT_METHOD) return 1300;
		if (arithmetic == Words.POINT_KEY2) return 1300;
		if (arithmetic == Words.POINT_METHOD2) return 1300;
		if (arithmetic == Words.FUNCTION_BODY) return 1300;
		if (arithmetic == Words.BRACKET) return 1300;
		if (arithmetic == Words.PRT) return 1300;
		if (arithmetic == Words.BRACE) return 1230; //优先级高于四则运算，低于new xxx[100] {};

		if (arithmetic == Words.FUNCTION_DIM) return 1220;
		if (arithmetic == Words.NEW) return 1250;

		if (arithmetic == Words.POSITIVE) return 1200;
		if (arithmetic == Words.NEGATIVE) return 1200;
		if (arithmetic == Words.ADDADD) return 1200;
		if (arithmetic == Words.SUBSUB) return 1200;
		if (arithmetic == Words.MULTIPLY) return 1010;
		if (arithmetic == Words.DIVIDE) return 1010;
		if (arithmetic == Words.MOD) return 1010;
		if (arithmetic == Words.ADD) return 1000;
		if (arithmetic == Words.SUB) return 1000;

		if (arithmetic == Words.INTERSECTION) return 510;
		if (arithmetic == Words.UNION) return 500;

		if (arithmetic == Words.AND) return 300;
		if (arithmetic == Words.OR) return 200;
		if (arithmetic == Words.EQUAL) return 700;
		if (arithmetic == Words.APPROX) return 700;
		if (arithmetic == Words.NOTEQUAL) return 700;
		if (arithmetic == Words.GREATER) return 800;
		if (arithmetic == Words.LESS) return 800;
		if (arithmetic == Words.GREATEREQUAL) return 800;
		if (arithmetic == Words.LESSEQUAL) return 800;
		if (arithmetic == Words.REVISE) return 1200;

		return -1;
	}
}
