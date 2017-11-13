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
	public static void main(String[] s)
	{
		String str = "var scriptName = \"Pis.hexie.app\";\n" +
				"var log = null;\n" +
				"\n" +
				"var getData = function() {\n" +
				"\tvar data = {\n" +
				"        \"orderNo\":\"YB201711101651259844\",\n" +
				"        \"policyInfo\":{\n" +
				"            \"appDate\":\"2017-11-10\",\n" +
				"            \"applicantInfo\":{\n" +
				"                \"addressInfoList\":[\n" +
				"                    {\n" +
				"                        \"city\":\"110100\",\n" +
				"                        \"county\":\"110103\",\n" +
				"                        \"detail\":\"圆明园路169号协进大楼\",\n" +
				"                        \"province\":\"110000\"\n" +
				"                    }\n" +
				"                ],\n" +
				"                \"birthday\":\"1988-01-01\",\n" +
				"                \"cardNo\":\"140421198801018018\",\n" +
				"                \"cardType\":\"0\",\n" +
				"                \"email\":\"evan.yu@zhongan.com\",\n" +
				"                \"gender\":\"0\",\n" +
				"                \"mobile\":\"15215086508\",\n" +
				"                \"name\":\"测试001\",\n" +
				"                \"occupation\":\"2090114\"\n" +
				"            },\n" +
				"            \"bnfInfoList\":[\n" +
				"\n" +
				"            ],\n" +
				"            \"effectiveDate\":\"2017-11-11\",\n" +
				"            \"endDate\":\"2048-11-11\",\n" +
				"            \"insureYears\":\"60\",\n" +
				"            \"insureYearsIntv\":\"A\",\n" +
				"            \"insuredInfoList\":[\n" +
				"                {\n" +
				"                    \"addressInfoList\":[\n" +
				"                        {\n" +
				"                            \"city\":\"110100\",\n" +
				"                            \"county\":\"110103\",\n" +
				"                            \"detail\":\"圆明园路169号协进大楼\",\n" +
				"                            \"province\":\"110000\"\n" +
				"                        }\n" +
				"                    ],\n" +
				"                    \"birthday\":\"1988-01-01\",\n" +
				"                    \"cardNo\":\"140421198801018018\",\n" +
				"                    \"cardType\":\"0\",\n" +
				"                    \"email\":\"evan.yu@zhongan.com\",\n" +
				"                    \"gender\":\"0\",\n" +
				"                    \"height\":\"174\",\n" +
				"                    \"mobile\":\"15215086508\",\n" +
				"                    \"name\":\"测试001\",\n" +
				"                    \"occupation\":\"2090114\",\n" +
				"                    \"relationToApp\":\"00\",\n" +
				"                    \"weight\":\"65\"\n" +
				"                }\n" +
				"            ],\n" +
				"            \"payIntv\":\"12\",\n" +
				"            \"payYears\":\"10\",\n" +
				"            \"payYearsIntv\":\"Y\",\n" +
				"            \"planName\":\"健康之享保障计划\",\n" +
				"            \"riskInfoList\":[\n" +
				"                {\n" +
				"                    \"amnt\":\"200000.0\",\n" +
				"                    \"autoRenewal\":\"-1\",\n" +
				"                    \"code\":\"2140102\",\n" +
				"                    \"copies\":\"1\",\n" +
				"                    \"dutyInfoList\":[\n" +
				"                        {\n" +
				"                            \"code\":\"903300\",\n" +
				"                            \"insureYears\":\"60\",\n" +
				"                            \"insureYearsIntv\":\"A\",\n" +
				"                            \"payIntv\":\"12\",\n" +
				"                            \"payYears\":\"10\",\n" +
				"                            \"payYearsIntv\":\"Y\"\n" +
				"                        }\n" +
				"                    ],\n" +
				"                    \"insureYears\":\"60\",\n" +
				"                    \"insureYearsIntv\":\"A\",\n" +
				"                    \"mainCode\":\"2140102\",\n" +
				"                    \"mainFlag\":\"Y\",\n" +
				"                    \"payIntv\":\"12\",\n" +
				"                    \"payYears\":\"10\",\n" +
				"                    \"payYearsIntv\":\"Y\",\n" +
				"                    \"premium\":\"3640.0\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"amnt\":\"100000.0\",\n" +
				"                    \"autoRenewal\":\"-1\",\n" +
				"                    \"code\":\"2140112\",\n" +
				"                    \"copies\":\"1\",\n" +
				"                    \"dutyInfoList\":[\n" +
				"                        {\n" +
				"                            \"code\":\"903200\",\n" +
				"                            \"insureYears\":\"60\",\n" +
				"                            \"insureYearsIntv\":\"A\",\n" +
				"                            \"payIntv\":\"12\",\n" +
				"                            \"payYears\":\"10\",\n" +
				"                            \"payYearsIntv\":\"Y\"\n" +
				"                        }\n" +
				"                    ],\n" +
				"                    \"insureYears\":\"60\",\n" +
				"                    \"insureYearsIntv\":\"A\",\n" +
				"                    \"mainCode\":\"2140102\",\n" +
				"                    \"mainFlag\":\"N\",\n" +
				"                    \"payIntv\":\"12\",\n" +
				"                    \"payYears\":\"10\",\n" +
				"                    \"payYearsIntv\":\"Y\",\n" +
				"                    \"premium\":\"760.0\"\n" +
				"                }\n" +
				"            ],\n" +
				"            \"totalPremium\":\"4400.0\"\n" +
				"        },\n" +
				"        \"transNo\":\"201711101651259844\",\n" +
				"        \"transTime\":\"2017-11-10 16:51:25\"\n" +
				"    };\n" +
				"\tvar pisReq = {\n" +
				"\t    \"appCode\":\"sale\",\n" +
				"\t    \"callbackUrl\":\"http://www.lerrain.com:7701/ware/callback.json?platformId=2&key=6b394b16-dd77-4891-8530-006e50bedffb\",\n" +
				"\t    \"message\":data,\n" +
				"\t    \"resource\":\"HTTP\",\n" +
				"\t    \"supplier\":\"AB\",\n" +
				"\t    \"version\":\"1\"\n" +
				"\t};\n" +
				"\n" +
				"}\n" +
				"\n" +
				"var x=null;var main = function() {\n" +
				"\tvar order = context.order;\n" +
				"\tlog.info(\"order data: \"+order);\n" +
				"\tvar code = findVendor(order).code;\n" +
				"\tlog.info(\"vendor data: \" + code);\n" +
				"\torder.extra = {};\n" +
				"\n" +
				"\t//var resp = req(URL.iyb_pis + '/int/v1/processor/handleAction/application', getData());\n" +
				"\n" +
				"\t//var order = service(\"order\", \"update.json\", order);\n" +
				"\n" +
				"\n" +
				"\n" +
				"  x.zzz ; return resp;\n" +
				"}\n" +
				"return main();\n";

		Formula script = Script.scriptOf(str);
		
		Map map = new HashMap();
		map.put("LoggerFactory", new Integer(65));
		
		Stack stack = new Stack(new TestParam(map));
		
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
