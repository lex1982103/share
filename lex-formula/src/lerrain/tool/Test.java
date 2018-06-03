package lerrain.tool;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.formula.Function;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.statement.ArithmeticApprox;

public class Test
{
	public static void print(Object v)
	{
		System.out.println(v.getClass() + ": " + v);
	}


	public static void main(String[] s)
	{
		try
		{
			//final Script sc = Script.scriptOf("z", "x+y/k");
			final Script sc = Script.scriptOf("z", "try({x+y/k}, 105)");

			Function ff = new Function()
			{
				@Override
				public Object run(Object[] v, Factors p)
				{
					return sc.run(p);
				}
			};

			Map map = new HashMap();
			map.put("k", 2);
			map.put("x", new Integer(65));
			map.put("y", new Integer(61));
			map.put("z", ff);

			Stack stack = new Stack(new TestParam(map));

			print(Script.scriptOf("z()").run(stack));
		}
		catch (ScriptRuntimeException e)
		{
			System.out.println(e.toStackString());
		}
	}

	public static void main2(String[] s)
	{
		HashMap m1 = new HashMap();
		m1.put("10.0", "100");

		HashMap m2 = new HashMap();
		m2.put(10, "100.0");

		System.out.println(ArithmeticApprox.compare(m1, m2));
	}


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
