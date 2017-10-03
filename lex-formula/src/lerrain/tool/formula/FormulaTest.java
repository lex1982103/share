package lerrain.tool.formula;

import java.math.BigInteger;

public class FormulaTest 
{
	public static void main(String[] s)
	{
//		String s2 = Double.toString(1.555);
//		System.out.println(s2);
//		
//		BigDecimal s9 = new BigDecimal(1.555);
//		System.out.println(s9);
//		BigDecimal s10 = BigDecimal.valueOf(1.555);
//		System.out.println(s10);
		
		t1();
		t2();
	}
	
	private static void t1()
	{
		Formula f = FormulaUtil.formulaOf("A*B*C");
		Factors p = new Factors() 
		{
			public Object get(String name) 
			{
				if ("A".equals(name))
					return new Double(15.3);
				else if ("B".equals(name))
					return new Integer(2);
				else if ("C".equals(name))
					return BigInteger.valueOf(10);

				return null;
			}
		};
		
		System.out.println(f.run(p));
	}
	
	private static void t2()
	{
		final Formula f = FormulaUtil.formulaOf("A*B*C + ', ' + F1.toString() + ', ' + F2.toString()");
		Factors p = new Factors() 
		{
			public Object get(String name) 
			{
				if ("A".equals(name))
					return new Double(15.3);
				else if ("B".equals(name))
					return new Integer(2);
				else if ("C".equals(name))
					return BigInteger.valueOf(10);
				else if ("F1".equals(name))
					return new FormulaTest();
				else if ("F2".equals(name))
					return f;

				return null;
			}
		};
		
		System.out.println(f.run(p));
	}
}
