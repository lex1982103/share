package lerrain.tool;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.formula.Function;
import lerrain.tool.script.*;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;
import lerrain.tool.script.warlock.statement.ArithmeticApprox;

public class Test
{
	public static void main(String[] s)
	{
//		Map<String, String> map = System.getenv();
//		for (Map.Entry<String, String> e : map.entrySet())
//			System.out.println(e.getKey() + ": " + e.getValue());

//		Properties p = System.getProperties();
//		for (String name : p.stringPropertyNames())
//			System.out.println(name + ": " + System.getProperty(name));
//
		String str = "var pack={editor:{}}; return {\n" +
				"\t\"code\": pack.code,\n" +
				"\t\"wareId\": pack.wareId,\n" +
				"\t\"wareCode\": pack.wareCode,\n" +
				"\t\"type\": pack.type,\n" +
				"\t\"applyMode\": pack.applyMode,\n" +
				"\t\"wareName\": pack.wareName,\n" +
				"\t\"vendor\": pack.vendor,\n" +
				"\t\"page\": {\n" +
				"\t\t\"title\": null,\n" +
				"\t\t\"ownerOrigin\": ownerDomain\n" +
				"\t},\n" +
				"\t\"show\": {\n" +
				"\t\tbuyButton: saleDate,\n" +
				"\t\tdetail: pack.editor.detail,\n" +
				"\t\tfixed: pack.editor.fixed\n" +
				"\t},\n" +
				"\t\"form\": pack.editor.form,\n" +
				"\t\"extra\": {\n" +
				"\t\t\"prizes\": false,\n" +
				"\t\t\"kefuUrl\": pack.extra..kefuUrl,\n" +
				"\t\t\"informationUrl\": null,\n" +
				"\t\t\"posterUrl\": null,\n" +
				"\t\tshareObject: sb\n" +
				"\t},\n" +
				"\tdict: dict,\n" +
				"\t\"id\": pack.id\n" +
				"};";
		Script script = Script.scriptOf(str);
//		Script script = Script.scriptOf("var z=2; var x = {x:z==null?1:z!=null?z==1?7:8:4,y:z!=null?5:4}; return x;");
//		Script script = Script.scriptOf("var x = {}; var z.z = x.y catch 100; return z");
//		Script script = Script.scriptOf("try(100,0); print(try(c.z,0)); try{ var x = 1; var y = 5; for (var i = 0; i < 10; i++) { var c=null; c.x; y += x; print(y); }}  throw 100; { print(100); }  print (101);");
		try
		{
			System.out.println(script.run(new Stack()));
		}
		catch (ScriptRuntimeThrow e)
		{
			System.out.println(e.getValue());
			e.printStackTrace();
		}

		Words words = new Words(null, str);
		Words w1 = words.cut(words.size());

		System.out.println(Expression.expressionOf(w1));
	}
}
