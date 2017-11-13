package lerrain.tool.script.warlock.analyse;

import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.statement.ArithmeticAdd;
import lerrain.tool.script.warlock.statement.ArithmeticAddAdd;
import lerrain.tool.script.warlock.statement.ArithmeticAddLet;
import lerrain.tool.script.warlock.statement.ArithmeticAnd;
import lerrain.tool.script.warlock.statement.ArithmeticArray;
import lerrain.tool.script.warlock.statement.ArithmeticBrace;
import lerrain.tool.script.warlock.statement.ArithmeticColon;
import lerrain.tool.script.warlock.statement.ArithmeticComma;
import lerrain.tool.script.warlock.statement.ArithmeticDivide;
import lerrain.tool.script.warlock.statement.ArithmeticEqual;
import lerrain.tool.script.warlock.statement.ArithmeticFunction;
import lerrain.tool.script.warlock.statement.ArithmeticFunctionDim;
import lerrain.tool.script.warlock.statement.ArithmeticGreater;
import lerrain.tool.script.warlock.statement.ArithmeticGreaterEqual;
import lerrain.tool.script.warlock.statement.ArithmeticLess;
import lerrain.tool.script.warlock.statement.ArithmeticLessEqual;
import lerrain.tool.script.warlock.statement.ArithmeticLet;
import lerrain.tool.script.warlock.statement.ArithmeticMod;
import lerrain.tool.script.warlock.statement.ArithmeticMultiply;
import lerrain.tool.script.warlock.statement.ArithmeticNew;
import lerrain.tool.script.warlock.statement.ArithmeticNot;
import lerrain.tool.script.warlock.statement.ArithmeticNotEqual;
import lerrain.tool.script.warlock.statement.ArithmeticOr;
import lerrain.tool.script.warlock.statement.ArithmeticParentheses;
import lerrain.tool.script.warlock.statement.ArithmeticPointKey;
import lerrain.tool.script.warlock.statement.ArithmeticPointMethod;
import lerrain.tool.script.warlock.statement.ArithmeticQuestMark;
import lerrain.tool.script.warlock.statement.ArithmeticSub;
import lerrain.tool.script.warlock.statement.ArithmeticSubLet;
import lerrain.tool.script.warlock.statement.ArithmeticSubSub;
import lerrain.tool.script.warlock.statement.Constant;
import lerrain.tool.script.warlock.statement.Variable;

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
			if (t == Words.NULLPT || t == Words.NUMBER || t == Words.STRING || t == Words.TRUE || t == Words.FALSE)
				return new Constant(t, ws.getWord(0));
			if (t == Words.VARIABLE || t == Words.WORD)
				return new Variable(ws.getWord(0));
		}
		
		int p1 = 10000;
		int pos = -1;
		
		for (int i = ws.size() - 1; i >= 0; i--)
		{
			int t = ws.getType(i);
			
			if (t == Words.BRACE_R || t == Words.BRACKET_R || t == Words.PRT_R)
			{
				i = Syntax.findLeftBrace(ws, i - 1);
				t = ws.getType(i);
			}
			
			int p2 = getPriority(t);
			if (p2 > 0 && p2 < p1)
			{
				p1 = p2;
				pos = i;
			}
		}
		
		if (pos >= 0)
			return arithmeticOf(ws.getType(pos), ws, pos);

		throw new SyntaxException("无法处理的运算 - " + ws.toString());
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
	
	private static Code arithmeticOf(int arithmetic, Words ws, int pos)
	{
		if (arithmetic == Words.LET) return new ArithmeticLet(ws, pos);
		if (arithmetic == Words.ADDLET) return new ArithmeticAddLet(ws, pos);
		if (arithmetic == Words.SUBLET) return new ArithmeticSubLet(ws, pos);
		if (arithmetic == Words.COMMA) return new ArithmeticComma(ws, pos);
		if (arithmetic == Words.QUESTMARK) return new ArithmeticQuestMark(ws, pos);
		if (arithmetic == Words.COLON) return new ArithmeticColon(ws, pos);
		
		if (arithmetic == Words.NEW) return new ArithmeticNew(ws, pos);
		if (arithmetic == Words.FUNCTION_DIM) return new ArithmeticFunctionDim(ws, pos);

		if (arithmetic == Words.POINT_KEY) return new ArithmeticPointKey(ws, pos);
		if (arithmetic == Words.POINT_METHOD) return new ArithmeticPointMethod(ws, pos);
		if (arithmetic == Words.BRACKET) return new ArithmeticArray(ws, pos);
		if (arithmetic == Words.FUNCTION) return new ArithmeticFunction(ws, pos);
		if (arithmetic == Words.BRACE) return new ArithmeticBrace(ws, pos);
		
		if (arithmetic == Words.POSITIVE) return new ArithmeticAdd(ws, pos);
		if (arithmetic == Words.NEGATIVE) return new ArithmeticSub(ws, pos);
		if (arithmetic == Words.ADDADD) return new ArithmeticAddAdd(ws, pos);
		if (arithmetic == Words.SUBSUB) return new ArithmeticSubSub(ws, pos);
		if (arithmetic == Words.ADD) return new ArithmeticAdd(ws, pos);
		if (arithmetic == Words.SUB) return new ArithmeticSub(ws, pos);
		if (arithmetic == Words.MULTIPLY) return new ArithmeticMultiply(ws, pos);
		if (arithmetic == Words.DIVIDE) return new ArithmeticDivide(ws, pos);
		if (arithmetic == Words.MOD) return new ArithmeticMod(ws, pos);
		
		if (arithmetic == Words.AND) return new ArithmeticAnd(ws, pos);
		if (arithmetic == Words.OR) return new ArithmeticOr(ws, pos);
		if (arithmetic == Words.REVISE) return new ArithmeticNot(ws, pos);
		if (arithmetic == Words.EQUAL) return new ArithmeticEqual(ws, pos);
		if (arithmetic == Words.NOTEQUAL) return new ArithmeticNotEqual(ws, pos);
		if (arithmetic == Words.GREATER) return new ArithmeticGreater(ws, pos);
		if (arithmetic == Words.LESS) return new ArithmeticLess(ws, pos);
		if (arithmetic == Words.GREATEREQUAL) return new ArithmeticGreaterEqual(ws, pos);
		if (arithmetic == Words.LESSEQUAL) return new ArithmeticLessEqual(ws, pos);
		
		if (arithmetic == Words.PRT) return new ArithmeticParentheses(ws, pos);
		
		throw new SyntaxException("ARITHMETIC<" + arithmetic + "> not found");
	}
	
	private static int getPriority(int arithmetic)
	{
		if (arithmetic == Words.LET) return 50;
		if (arithmetic == Words.ADDLET) return 50;
		if (arithmetic == Words.SUBLET) return 50;
		if (arithmetic == Words.COMMA) return 60;
		if (arithmetic == Words.QUESTMARK) return 100;
		if (arithmetic == Words.COLON) return 101;

		if (arithmetic == Words.POINT_KEY) return 1300;
		if (arithmetic == Words.POINT_METHOD) return 1300;
		if (arithmetic == Words.FUNCTION) return 1300;
		if (arithmetic == Words.BRACKET) return 1300;
		if (arithmetic == Words.BRACE) return 1300;
		if (arithmetic == Words.PRT) return 1300;

		if (arithmetic == Words.FUNCTION_DIM) return 1260;
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

		if (arithmetic == Words.AND) return 300;
		if (arithmetic == Words.OR) return 200;
		if (arithmetic == Words.REVISE) return 1200;
		if (arithmetic == Words.EQUAL) return 700;
		if (arithmetic == Words.NOTEQUAL) return 700;
		if (arithmetic == Words.GREATER) return 800;
		if (arithmetic == Words.LESS) return 800;
		if (arithmetic == Words.GREATEREQUAL) return 800;
		if (arithmetic == Words.LESSEQUAL) return 800;

		return -1;
	}
}
