package lerrain.tool;

import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeThrow;
import lerrain.tool.script.Stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test
{
	public static void main(String[] s)
	{
		Map m1 = new HashMap<>();
		Map m2 = new HashMap<>();
		m1.put("m2", m2);
		m2.put("m1", m1);
		System.out.println(m1);

//		char c = '，';
//		System.out.println(c < 0x4E00 || c > 0x9FA5);

//		Map<String, String> map = System.getenv();
//		for (Map.Entry<String, String> e : map.entrySet())
//			System.out.println(e.getKey() + ": " + e.getValue());

//		Properties p = System.getProperties();
//		for (String name : p.stringPropertyNames())
//			System.out.println(name + ": " + System.getProperty(name));

//		Script ss = Script.scriptOf("select count(~) from 1");
//		System.out.println(ss.run(null));
//
//		String str = "AGENTS { PREMIUM } ";
//		Script script = Script.scriptOf(str);
//
//		List list = new ArrayList<>();
//		for (int i=0;i<100;i++)
//		{
//			Stack m = new Stack();
//			m.declare("PREMIUM", i + 1);
//			list.add(m);
//		}
//
//		try
//		{
//			Stack stack = new Stack();
//			stack.declare("AGENTS", list);
//			System.out.println(script.run(stack));
//		}
//		catch (ScriptRuntimeThrow e)
//		{
//			System.out.println(e.getValue());
//			e.printStackTrace();
//		}

//		Words words = new Words(null, str);
//		Words w1 = words.cut(words.size());
//
//		System.out.println(Expression.expressionOf(w1));
	}
}
