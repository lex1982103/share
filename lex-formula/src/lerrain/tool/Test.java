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
			final Script sc = Script.scriptOf("z", "x+y/k");

			Function ff = new Function()
			{
				@Override
				public Object run(Object[] v, Factors p)
				{
					return sc.run(p);
				}
			};

			Map map = new HashMap();
			map.put("k", null);
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
