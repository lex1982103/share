package lerrain.tool;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.Stack;

public class Test
{
	public static void print(Object v)
	{
		System.out.println(v.getClass() + ": " + v);
	}


	public static void main(String[] s)
	{
		Map map = new HashMap();
		map.put("x", new Integer(65));
		map.put("y", new Integer(61));

		Stack stack = new Stack(new TestParam(map));

		print(Script.scriptOf("x+y").run(stack));
		print(Script.scriptOf("x*y").run(stack));
		print(Script.scriptOf("x%y").run(stack));
		print(Script.scriptOf("x/y").run(stack));
		print(Script.scriptOf("x+=y").run(stack));
		print(Script.scriptOf("x-y").run(stack));

		String str = "return main();\n";

		Formula script = Script.scriptOf(str);
		
		long t1 = System.currentTimeMillis();

		try
		{
			System.out.println(script + " = " + script.run(stack));
		}
		catch (ScriptRuntimeException e)
		{
			e.printStackTrace();
			System.out.println(e.getStackMap());
		}
//
//		for (int i = 0; i < 1000000L; i++)
//			script.run(stack);
		
//		double x;
//		
//		for (int i = 0; i < 100000000L; i++)
//		x = 12321321f / 2131231212f / 112321321f;
	
//		BigDecimal x1 = new BigDecimal(12321321f);
//		BigDecimal x2 = new BigDecimal(2131231212f);
//		BigDecimal x3 = new BigDecimal(112321321f);
//		
//		for (int i = 0; i < 1000000L; i++)
//			x1.divide(x2, 10, RoundingMode.HALF_UP).divide(x3, 10, RoundingMode.HALF_UP);
		
		System.out.println(System.currentTimeMillis() - t1 + "ms");
	}
	
//	public static void main2(String[] s)
//	{
////		File file = new File("D:/sd/workspace/base/lex-formula/src/lerrain/tool/formula/aries/FormulaCompiler.java");
//		File file = new File("D:/s/test.txt");
//		
//		Words ws = Words.wordsOf(Text.read(file));
//		
//		List text = Syntax.split(ws);
//		
////		for (int i=0;i<text.size();i++)
////			System.out.println(text.get(i));
//		
//		System.out.println(new Script(ws).toText(""));
//	}
	
	static class TestParam implements Factors
	{
		Map map;
		
		public TestParam(Map map)
		{
			this.map = map;
		}
		
		public Object get(String name)
		{
			return map.get(name);
		}
		
	}
}
