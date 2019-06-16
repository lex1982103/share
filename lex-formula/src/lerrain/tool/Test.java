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
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.statement.ArithmeticApprox;

public class Test
{
	static Map s = new HashMap();
	static Map m = new HashMap();

	static class MapTest implements Runnable
	{

		@Override
		public void run()
		{
			test();
		}

		public void test()
		{
			Thread.currentThread().interrupt();
			System.out.println(Thread.currentThread().isInterrupted());
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				throw new ScriptRuntimeException("interrupt", e);
			}
		}
	}

	public static void main(String[] s)
	{
		MapTest mt = new MapTest();

		Thread thread = new Thread(mt);
		thread.start();
	}

	public static void main5(String[] s)
	{
		String str = "_version=2.5&_client=2&__MYLOG_UID=bfcc7e5f-cb21-4688-979e-08c738d6ae8f&__MYLOG_SID=879cac2a-9257-4229-938e-b75e811e5b44";
		Map param = new HashMap();
		for (String ss : str.split("\\&"))
		{
			String[] pp = ss.split("\\=");
			if (pp.length >= 2)
				param.put(pp[0], pp[1]);
		}

		System.out.println(param);

		Script script = Script.scriptOf("var x = 1; var y = 2; for (var i = 0; i < 10; i++) { y += x; print(y); }");
		script.markBreakPoint(40);

		final Stack stack = new Stack();
		stack.setBreakListener(new Stack.BreakListener()
		{
			@Override
			public void onBreak(Code code, Stack f)
			{
				System.out.println("DEBUG: " + code);
				System.out.println("STACK: " + f);
			}

			@Override
			public void onReturn(Code code, Stack f, Object val)
			{

			}
		});

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				for (int i=0;i<10;i++)
				{
					try
					{
						Thread.sleep(2000);
					}
					catch (InterruptedException e)
					{
					}

					synchronized (stack.getBreakListener())
					{
						stack.getBreakListener().notifyAll();
					}
				}
			}
		}).start();

		System.out.println(script.run(stack));
	}

	public static void main3(String[] s)
	{
		Script xx = Script.scriptOf("var tdy = timestr(time(time(), 0, 0, 1), 'yyyy.MM.dd');\n" +
				"var expe = timestr(time(time(), 1, 0, 0), 'yyyy.MM.dd');\n" +
				"\n" +
				"var insMax = 7;\n" +
				"\n" +
				"return {\n" +
				"\t\"page\": {\n" +
				"\t\t\"title\": \"众安女性专属疾病保险\"\n" +
				"\t},\n" +
				"\t\"show\": {\n" +
				"\t\tdetail: [\n" +
				"\t\t\t{\n" +
				"\t\t\t\t\"widget\": \"plan-card-box\",\n" +
				"\t\t\t\t\"vendorName\": pack.vendor.name,\n" +
				"\t\t\t\t\"productName\": pack.wareName,\n" +
				"\t\t\t\t\"options\": [\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"label\": \"保险期间\",\n" +
				"\t\t\t\t\t\t\"value\": tdy + \"-\" + expe\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"label\": \"保障期限\",\n" +
				"\t\t\t\t\t\t\"value\": \"1年\"\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t]\n" +
				"\t\t\t},\n" +
				"\t\t\t{\n" +
				"\t\t\t\t\"widget\": \"person-box\",\n" +
				"\t\t\t\t\"title\": \"投保人信息\",\n" +
				"\t\t\t\t\"name\": \"applicant\",\n" +
				"\t\t\t\t\"dataBind\": \"detail.applicant\",\n" +
				"\t\t\t\t\"fields\": [\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"input\",\n" +
				"\t\t\t\t\t\t\"name\": \"name\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"name\",\n" +
				"\t\t\t\t\t\t\"label\": \"投保人姓名\",\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"placeholder\": \"请输入\",\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请输入投保人姓名\" },\n" +
				"\t\t\t\t\t\t\t\t{ \"pattern\": \"^[^\\d]{2,}$\", \"message\": \"姓名需至少2个字符，名字中不含数字\"},\n" +
				"\t\t\t\t\t\t\t\t{ \"pattern\": \"^[^\\~\\!\\@\\#\\$\\%\\^\\*\\+\\|\\}\\{\\?\\=]{0,}$\", \"message\": \"姓名中不得含有下列特殊字符：~!@#$%^*+|}{?/\\=\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"select\",\n" +
				"\t\t\t\t\t\t\"name\": \"certType\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"certType\",\n" +
				"\t\t\t\t\t\t\"label\": \"证件类型\",\n" +
				"\t\t\t\t\t\t\"detail\": [[\"I\", \"身份证\"]],\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"value\": \"I\",\n" +
				"\t\t\t\t\t\t\"placeholder\": \"请输入\",\n" +
				"\t\t\t\t\t\t\"cascade\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": {\"I\": {\"validate\": 'isIdCard', \"path\": 'certNo'}\n" +
				"\t\t\t\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t\t},\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请选择投保人证件类型\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"input\",\n" +
				"\t\t\t\t\t\t\"name\": \"certNo\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"certNo\",\n" +
				"\t\t\t\t\t\t\"label\": \"证件号码\",\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"placeholder\": \"请输入\",\n" +
				"\t\t\t\t\t\t\"refreshPrem\": true,\n" +
				"\t\t\t\t\t\t\"cascade\": {\n" +
				"\t\t\t\t\t\t\t\"BIRTHDAY\": {\n" +
				"\t\t\t\t\t\t\t\t\"rule\": \"\",\n" +
				"\t\t\t\t\t\t\t\t\"defaultVal\": \"getBirthday\"\n" +
				"\t\t\t\t\t\t\t},\n" +
				"\t\t\t\t\t\t\t\"GENDER\": {\n" +
				"\t\t\t\t\t\t\t\t\"rule\": \"\",\n" +
				"\t\t\t\t\t\t\t\t\"defaultVal\": \"getGender\"\n" +
				"\t\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t\t},\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请输入投保人证件号码\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t]\n" +
				"\t\t\t},\n" +
				"\t\t\t{\n" +
				"\t\t\t\t\"widget\": \"person-list\",\n" +
				"\t\t\t\t\"name\": \"insurants\",\n" +
				"\t\t\t\t\"dataBind\": \"detail.insurants\",\n" +
				"\t\t\t\t\"canDelete\": true,\n" +
				"\t\t\t\t\"title\": \"被保险人${index}信息\",\n" +
				"\t\t\t\t\"min\": 1,\n" +
				"\t\t\t\t\"max\": insMax,\n" +
				"\t\t\t\t\"listDesc\": [\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"item-ico\",\n" +
				"\t\t\t\t\t\t\"icon\": \"remind\",\n" +
				"\t\t\t\t\t\t\"value\": \"受益人为法定受益人\"\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t],\n" +
				"\t\t\t\t\"addBtnLabel\": \"添加被保人\",\n" +
				"\t\t\t\t\"fields\": [\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"select\",\n" +
				"\t\t\t\t\t\t\"name\": \"packId\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"packId\",\n" +
				"\t\t\t\t\t\t\"label\": \"投保计划\",\n" +
				"\t\t\t\t\t\t\"detail\": [[\"144\", \"经典版\"],[\"145\", \"优选版\"],[\"146\", \"尊享版\"],[\"147\", \"防癌加强版\"]],\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"value\": str(pack.id),\n" +
				"\t\t\t\t\t\t\"refreshPrem\": true,\n" +
				"\t\t\t\t\t\t\"placeholder\": \"请选择\",\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请选择投保计划\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"select-relation\",\n" +
				"\t\t\t\t\t\t\"name\": \"relation\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"relation\",\n" +
				"\t\t\t\t\t\t\"label\": \"是投保人的\",\n" +
				"\t\t\t\t\t\t\"detail\": [[\"1\",\"本人\",\"self\"],[\"2\",\"配偶\",\"coupon\"],[\"3\",\"子女\",\"lineal\"],[\"4\",\"父母\",\"lineal\"]],\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"value\": \"1\",\n" +
				"\t\t\t\t\t\t\"refreshPrem\": true,\n" +
				"\t\t\t\t\t\t\"alwaysShow\": true,\n" +
				"\t\t\t\t\t\t\"placeholder\": \"请选择\",\n" +
				"\t\t\t\t\t\t\"cascade\": {\n" +
				"\t\t\t\t\t\t\t\"__display__\": {\n" +
				"\t\t\t\t\t\t\t\t\"1\": [\"packId\",\"relation\", \"premium\"],\n" +
				"\t\t\t\t\t\t\t\t\"2\": [\"packId\",\"relation\", \"premium\", \"certType\", \"certNo\", \"BIRTHDAY\", \"GENDER\",\"name\"],\n" +
				"\t\t\t\t\t\t\t\t\"3\": [\"packId\",\"relation\", \"premium\", \"certType\", \"certNo\", \"BIRTHDAY\", \"GENDER\",\"name\"],\n" +
				"\t\t\t\t\t\t\t\t\"4\": [\"packId\",\"relation\", \"premium\", \"certType\", \"certNo\", \"BIRTHDAY\", \"GENDER\",\"name\"]\n" +
				"\t\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t\t},\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请选择与投保人关系\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"input\",\n" +
				"\t\t\t\t\t\t\"name\": \"name\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"name\",\n" +
				"\t\t\t\t\t\t\"label\": \"姓名\",\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"placeholder\": \"请输入\",\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请输入被保人姓名\" },\n" +
				"\t\t\t\t\t\t\t\t{ \"pattern\": \"^[^\\d]{2,}$\", \"message\": \"姓名需至少2个字符，名字中不含数字\"},\n" +
				"\t\t\t\t\t\t\t\t{ \"pattern\": \"^[^\\~\\!\\@\\#\\$\\%\\^\\*\\+\\|\\}\\{\\?\\=]{0,}$\", \"message\": \"姓名中不得含有下列特殊字符：~!@#$%^*+|}{?/\\=\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"select\",\n" +
				"\t\t\t\t\t\t\"name\": \"certType\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"certType\",\n" +
				"\t\t\t\t\t\t\"label\": \"证件类型\",\n" +
				"\t\t\t\t\t\t\"detail\": [[\"I\", \"身份证\"],[\"P\", \"护照\"],[\"GA\", \"港澳通行证\"],[\"TW\", \"台湾通行证\"]],\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"value\": \"I\",\n" +
				"\t\t\t\t\t\t\"refreshPrem\": true,\n" +
				"\t\t\t\t\t\t\"placeholder\": \"请选择\",\n" +
				"\t\t\t\t\t\t\"cascade\": {\n" +
				"\t\t\t\t\t\t\t\"__display__\": {\n" +
				"\t\t\t\t\t\t\t\t\"I\": [\"packId\",\"relation\", \"premium\", \"certType\", \"certNo\",\"name\"],\n" +
				"\t\t\t\t\t\t\t\t\"P\": [\"packId\",\"relation\", \"premium\", \"certType\", \"certNo\", \"BIRTHDAY\", \"GENDER\", \"name\"],\n" +
				"\t\t\t\t\t\t\t\t\"GA\": [\"packId\",\"relation\", \"premium\", \"certType\", \"certNo\", \"BIRTHDAY\", \"GENDER\", \"name\"],\n" +
				"\t\t\t\t\t\t\t\t\"TW\": [\"packId\",\"relation\", \"premium\", \"certType\", \"certNo\", \"BIRTHDAY\", \"GENDER\", \"name\"]\n" +
				"\t\t\t\t\t\t\t},\n" +
				"\t\t\t\t\t\t\t\"rules\": {\n" +
				"\t\t\t\t\t\t\t\t\"I\": {\"validate\": 'isIdCard', \"path\": 'certNo'},\n" +
				"\t\t\t\t\t\t\t\t\"P\": {\"path\": \"certNo\"},\n" +
				"\t\t\t\t\t\t\t\t\"GA\": {\"path\": \"certNo\"},\n" +
				"\t\t\t\t\t\t\t\t\"TW\": {\"path\": \"certNo\"}\n" +
				"\t\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t\t},\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请选择被保人证件类型\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"input\",\n" +
				"\t\t\t\t\t\t\"name\": \"certNo\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"certNo\",\n" +
				"\t\t\t\t\t\t\"label\": \"证件号码\",\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"refreshPrem\": true,\n" +
				"\t\t\t\t\t\t\"placeholder\": \"请输入\",\n" +
				"\t\t\t\t\t\t\"cascade\": {\n" +
				"\t\t\t\t\t\t\t\"BIRTHDAY\": {\n" +
				"\t\t\t\t\t\t\t\t\"rule\": \"\",\n" +
				"\t\t\t\t\t\t\t\t\"defaultVal\": \"getBirthday\"\n" +
				"\t\t\t\t\t\t\t},\n" +
				"\t\t\t\t\t\t\t\"GENDER\": {\n" +
				"\t\t\t\t\t\t\t\t\"rule\": \"\",\n" +
				"\t\t\t\t\t\t\t\t\"defaultVal\": \"getGender\"\n" +
				"\t\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t\t},\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请输入被保人证件号码\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"date\",\n" +
				"\t\t\t\t\t\t\"name\": \"BIRTHDAY\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"birthday\",\n" +
				"\t\t\t\t\t\t\"label\": \"出生日期\",\n" +
				"\t\t\t\t\t\t\"type\": \"date\",\n" +
				"\t\t\t\t\t\t\"value\": \"1999-01-01\",\n" +
				"\t\t\t\t\t\t\"refreshPrem\": true,\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请选择出生日期\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"switch\",\n" +
				"\t\t\t\t\t\t\"name\": \"GENDER\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"gender\",\n" +
				"\t\t\t\t\t\t\"label\": \"性别\",\n" +
				"\t\t\t\t\t\t\"detail\": [[\"M\",\"男\"],[\"F\",\"女\"]],\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"value\": \"M\",\n" +
				"\t\t\t\t\t\t\"refreshPrem\": true,\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请选择性别\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"labelvalue\",\n" +
				"\t\t\t\t\t\t\"name\": \"premium\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"premium\",\n" +
				"\t\t\t\t\t\t\"label\": \"保费\",\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"showPre\": \"￥\",\n" +
				"\t\t\t\t\t\t\"value\": \"0\",\n" +
				"\t\t\t\t\t\t\"alwaysShow\": true\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t]\n" +
				"\t\t\t},\n" +
				"\t\t\t{\n" +
				"\t\t\t\t\"widget\": \"contact-box\",\n" +
				"\t\t\t\t\"name\": \"contact\",\n" +
				"\t\t\t\t\"dataBind\": \"detail.contact\",\n" +
				"\t\t\t\t\"title\": \"联系信息\",\n" +
				"\t\t\t\t\"fields\": [\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"input\",\n" +
				"\t\t\t\t\t\t\"name\": \"mobile\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"mobile\",\n" +
				"\t\t\t\t\t\t\"label\": \"手机号\",\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"placeholder\": \"请输入投保人手机号\",\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请输入手机号码\" },\n" +
				"\t\t\t\t\t\t\t\t{ \"pattern\": \"^1[3456789]\\d{9}$\", \"message\": \"请输入正确的手机号码\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"smscode\",\n" +
				"\t\t\t\t\t\t\"name\": \"smsCode\",\n" +
				"\t\t\t\t\t\t\"label\": \"验证码\",\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"placeholder\": \"请输入\",\n" +
				"\t\t\t\t\t\t\"source\": [\n" +
				"\t\t\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\t\t\"key\": \"mobile\"\n" +
				"\t\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t\t],\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请输入验证码\" },\n" +
				"\t\t\t\t\t\t\t\t{ \"pattern\": \"^\\d{6}$\", \"message\": \"验证码为6位数字\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t},\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"input\",\n" +
				"\t\t\t\t\t\t\"name\": \"email\",\n" +
				"\t\t\t\t\t\t\"dataBind\": \"email\",\n" +
				"\t\t\t\t\t\t\"label\": \"电子邮箱\",\n" +
				"\t\t\t\t\t\t\"type\": \"string\",\n" +
				"\t\t\t\t\t\t\"placeholder\": \"用于接受电子保单\",\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请输入正确的电子邮箱\" },\n" +
				"\t\t\t\t\t\t\t\t{ \"pattern\": \"^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$\", \"message\": \"请输入正确的电子邮箱\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t]\n" +
				"\t\t\t},\n" +
				"\t\t\t{\n" +
				"\t\t\t\t\"widget\": \"read-box\",\n" +
				"\t\t\t\t\"name\": \"readDoc\",\n" +
				"\t\t\t\t\"dataBind\": \"detail.readDoc\",\n" +
				"\t\t\t\t\"fields\": [\n" +
				"\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\"widget\": \"agree-terms\",\n" +
				"\t\t\t\t\t\t\"name\": \"insuredoc\",\n" +
				"\t\t\t\t\t\t\"value\": false,\n" +
				"\t\t\t\t\t\t\"label\": \"本人已确认阅读 RDOM(file:{\\\"href\\\": \\\"https://static.zhongan.com/website/health/iyb/resource/product/ware78/doc/baoxiantiaokuan.pdf\\\", \\\"value\\\": \\\"《女性特定疾病保险条款》\\\"})、\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t RDOM(file:{\\\"href\\\": \\\"https://static.zhongan.com/website/health/iyb/resource/product/ware78/doc/toubaoxuzhii.pdf\\\", \\\"value\\\": \\\"《投保须知》\\\"})\",\n" +
				"\t\t\t\t\t\t\"validate\": {\n" +
				"\t\t\t\t\t\t\t\"rules\": [\n" +
				"\t\t\t\t\t\t\t\t{ \"required\": true, \"message\": \"请阅读并授权此内容\" }\n" +
				"\t\t\t\t\t\t\t]\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t]\n" +
				"\t\t\t}\n" +
				"\t\t],\n" +
				"\t\t\"fixed\": {\n" +
				"\t\t\t\"top\": [],\n" +
				"\t\t\t\"right\": [],\n" +
				"\t\t\t\"bottom\": [\n" +
				"\t\t\t\t{\n" +
				"\t\t\t\t\t\"widget\": \"trial-bottom-btns\",\n" +
				"\t\t\t\t\t\"detail\": [\n" +
				"\t\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\t\"mode\": \"text\",\n" +
				"\t\t\t\t\t\t\t\"type\": \"price\",\n" +
				"\t\t\t\t\t\t\t\"flex\": \"2\",\n" +
				"\t\t\t\t\t\t\t\"label\": \"价格：\",\n" +
				"\t\t\t\t\t\t\t\"dataBind\": \"price\",\n" +
				"\t\t\t\t\t\t\t\"showPre\": \"￥\",\n" +
				"\t\t\t\t\t\t\t\"value\": \"0\"\n" +
				"\t\t\t\t\t\t},\n" +
				"\t\t\t\t\t\t{\n" +
				"\t\t\t\t\t\t\t\"mode\": \"btn\",\n" +
				"\t\t\t\t\t\t\t\"type\": \"ajax\",\n" +
				"\t\t\t\t\t\t\t\"label\": \"立即投保\",\n" +
				"\t\t\t\t\t\t\t\"style\": {\n" +
				"\t\t\t\t\t\t\t\t\"backgroundColor\": \"#fb0\"\n" +
				"\t\t\t\t\t\t},\n" +
				"\t\t\t\t\t\t\"flex\": \"1\",\n" +
				"\t\t\t\t\t\t\"log\": \"xxxx\",\n" +
				"\t\t\t\t\t\t\"data\": {\n" +
				"\t\t\t\t\t\t\ttype: \"post\",\n" +
				"\t\t\t\t\t\t\turl: IYB.WEB_ORIGIN['apitm']+\"sale/v2/transition.json\",\n" +
				"\t\t\t\t\t\t\tdataType: \"json\",\n" +
				"\t\t\t\t\t\t\tdata: {\n" +
				"\t\t\t\t\t\t\t\t_eventsType: 'insure_next_c_a',\n" +
				"\t\t\t\t\t\t\t\twareId: pack.wareId,\n" +
				"\t\t\t\t\t\t\t\tpackId: pack.id,\n" +
				"\t\t\t\t\t\t\t\taccountId: param.accountId,\n" +
				"\t\t\t\t\t\t\t\tplatType: param.platType,\n" +
				"\t\t\t\t\t\t\t\tchannelUserId: param.channelUserId,\n" +
				"\t\t\t\t\t\t\t\torderId: rc..orderId,\n" +
				"\t\t\t\t\t\t\t\toSign: param.oSign\n" +
				"\t\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t}]\n" +
				"\t\t\t\t}],\n" +
				"\t\t\t\"left\": []\n" +
				"\t\t}\n" +
				"\t},\n" +
				"\t\"dict\": {\n" +
				"\t\t\"citys\": {\n" +
				"\t\t\t\"type\": \"post\",\n" +
				"\t\t\t\"url\": IYB.WEB_ORIGIN['apitm']+\"dict/view.json\",\n" +
				"\t\t\t\"dataType\": \"json\",\n" +
				"\t\t\t\"data\": {\"company\": \"haixia\", \"name\": \"city\", \"version\": \"1\"}\n" +
				"\n" +
				"\t\t}\n" +
				"\t}\n" +
				"};\n" +
				"\n");

		System.out.println(xx);

//		char ch1 = (char) 84426;
//		char ch2 = '䧊';
//		char ch3 = (char) 18890;
//
//		System.out.println("dasfdsad" + ch1);
//		System.out.println(ch2);
//		System.out.println(ch3);
//		System.out.println(ch1 == ch2);
//		System.out.println(ch1 == ch3);
//		System.out.println(ch2 == ch3);
//
//		try
//		{
//			//final Script sc = Script.scriptOf("z", "x+y/k");
//			final Script sc = Script.scriptOf("z", "try({x+y/k}, 105)");
//
//			Function ff = new Function()
//			{
//				@Override
//				public Object run(Object[] v, Factors p)
//				{
//					return sc.run(p);
//				}
//			};
//
//			Map map = new HashMap();
//			map.put("k", 2);
//			map.put("x", new Integer(65));
//			map.put("y", new Integer(61));
//			map.put("z", ff);
//
//			Stack stack = new Stack(new TestParam(map));
//
//			print(Script.scriptOf("z()").run(stack));
//		}
//		catch (ScriptRuntimeException e)
//		{
//			System.out.println(e.toStackString());
//		}
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
