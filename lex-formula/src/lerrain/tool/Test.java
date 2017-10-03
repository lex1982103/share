package lerrain.tool;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.script.Stack;

public class Test
{
	public static void main(String[] s)
	{
		System.out.println(BigDecimal.valueOf(0.0295 * 100) + "");
		System.out.println(0.0295 * 100);
		
		BigDecimal d = BigDecimal.valueOf(0.0295 * 100 + 0.0000000001f);
		System.out.println(d.setScale(2, BigDecimal.ROUND_HALF_UP));
		
		 d = new BigDecimal(0.0295 * 100 + "");
		System.out.println(d.setScale(2, BigDecimal.ROUND_HALF_UP));

		
		Object xx = new HashMap[5][5][5];
		
		System.out.println(xx instanceof Object[][]);
		
		System.out.println(new BigDecimal(9999.9999d).intValue());
		
		Formula script = FormulaUtil.formulaOf("return 123213213312 / 5 / 112321321");
		
		Map map = new HashMap();
		map.put("y", new Integer(65));
		
		Stack stack = new Stack(new TestParam(map));
		
		long t1 = System.currentTimeMillis();
		
		System.out.println(script + " = " + script.run(stack));
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
		
		System.out.println(System.currentTimeMillis() - t1);
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
