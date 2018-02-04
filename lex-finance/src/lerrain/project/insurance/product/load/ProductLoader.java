package lerrain.project.insurance.product.load;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lerrain.project.insurance.product.*;
import lerrain.project.insurance.product.attachment.AttachmentParser;
import lerrain.project.insurance.product.rule.Rule;
import lerrain.tool.data.DataParser;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.script.Script;
import lerrain.tool.script.warlock.analyse.Text;

public class ProductLoader
{
	Company company;
	
	Insurance product;
	
	Loader loader;
	
//	String path;
	
	public ProductLoader(Company company, Loader loader)
	{
		this.company = company;
		this.loader = loader;
	}

	public Insurance loadProduct(XmlNode root, String path)
	{
		this.product = new Insurance();
		
		parseAttribution(root);
		parseRoot(root, path == null ? "" : path + File.separator);
		
		return product;
	}

	public Insurance parseAttribution(XmlNode n1)
	{
		//产品停止销售时间
		String sed = n1.getAttribute("sale_end_date");
		if (!isEmpty(sed))
		{
			Date date = dateOf(sed, "yyyy-MM-dd");
			product.setSaleEndDate(date);
		}

		//产品开始销售时间
		String sbd = n1.getAttribute("sale_begin_date");
		if (!isEmpty(sbd))
		{
			Date date = dateOf(sbd, "yyyy-MM-dd");
			product.setSaleBeginDate(date);
		}

		//根据最后修改时间，确定配置文件的版本
		String lmd = n1.getAttribute("last_modify_date");
		if (!isEmpty(lmd))
		{
			Date date = dateOf(lmd, "yyyy-MM-dd");
			if (date.before(dateOf("2015-07-23", "yyyy-MM-dd")))
				product.setVersion(1);
			else
				product.setVersion(2);
		}
		
		//如果标明了版本，那么就直接确定版本号
		if (n1.hasAttribute("version"))
			product.setVersion(Integer.parseInt(n1.getAttribute("version")));
		
		//产品在计划中的显示位置
		String seq = n1.getAttribute("sequence");
		if (isEmpty(seq))
			product.setSequence(1000);
		else
			product.setSequence(Integer.parseInt(seq));

		product.setId(n1.getAttribute("id"));
		product.setName(n1.getAttribute("name"));
		product.setAbbrName(n1.getAttribute("name_abbr"));
		product.setCode(n1.getAttribute("code"));
		
		if (n1.hasAttribute("full_code"))
			product.setFullCode(FormulaUtil.formulaOf(n1.getAttribute("full_code")));

		/*
		 * 如果是代理公司，company是代理公司，vendor保存的是实际公司的code
		 */
		String vendor = n1.getAttributeInOrder("vendor,company_id,corporation_id");
//		company = (Company)loader.getCompanys().get(companyId);
		product.setVendor(vendor);

		product.setCompany(company);

//		String free = XmlUtil.getOptions(node, "free");
//		//这个值表明这个险种是免费的或者它的保费已经在别的险种中计算了
//		//与premuim="0"有不同，当前情况取buy.getPreimum仍然可以取到保费，只是显示的时候需要计数0。
//		//有问题，统计保费会出错，暂时先不用这个了
//		if (free != null)
//		{
//			
//		}
		
		String currency = n1.getAttribute("currency");
		if ("cny".equalsIgnoreCase(currency))
			product.setCurrency(Insurance.CURRENCY_CNY);
		else if ("twd".equalsIgnoreCase(currency))
			product.setCurrency(Insurance.CURRENCY_TWD);
		else if ("hkd".equalsIgnoreCase(currency))
			product.setCurrency(Insurance.CURRENCY_HKD);
		else if ("usd".equalsIgnoreCase(currency))
			product.setCurrency(Insurance.CURRENCY_USD);
		else if ("gbp".equalsIgnoreCase(currency))
			product.setCurrency(Insurance.CURRENCY_GBP);
		else if ("jpy".equalsIgnoreCase(currency))
			product.setCurrency(Insurance.CURRENCY_JPY);
		else if ("eur".equalsIgnoreCase(currency))
			product.setCurrency(Insurance.CURRENCY_EUR);

		/*
		 * 数据计算的关联关系
		 * 某险种的某项改变时，需要清除所有和他有关的信息，保证重新计算时没有使用任何旧数据的缓冲信息。
		 * 一般险种只用清除自己的即可，但是有些特别的产品数据项是彼此相关的，他们的缓存需要一起全部清除。
		 */
		String depend = n1.getAttribute("depend");
		if (!isEmpty(depend))
		{
			InsuranceDepend insDepend = new InsuranceDepend();
			
			String[] s = depend.split(",");
			for (int i = 0; i < s.length; i++)
			{
				if ("r".equalsIgnoreCase(s[i]) || "rider".equalsIgnoreCase(s[i]))
					insDepend.addProduct("rider");
				else if ("p".equalsIgnoreCase(s[i]) || "parent".equalsIgnoreCase(s[i]))
					insDepend.addProduct("parent");
				else if (s[i] != null)
					insDepend.addProduct(s[i]);
			}
			
			product.setDepend(insDepend);
		}

		String unit = n1.getAttributeInOrder("unit,unit_amount");
		if (!isEmpty(unit))
			product.setUnit(Double.parseDouble(unit));
		
		String purchaseTypeStr = n1.getAttribute("purchase");
		product.getPurchase().setPurchaseType(
				"none".equals(purchaseTypeStr) ? Purchase.NONE : 
				"quantity".equals(purchaseTypeStr) ? Purchase.QUANTITY : 
				"rank".equals(purchaseTypeStr) ? Purchase.RANK : 
				"rank_and_quantity".equals(purchaseTypeStr) ? Purchase.RANK_AND_QUANTITY : 
				Purchase.AMOUNT);
		
		String inputStr = n1.getAttribute("input");
		if (isEmpty(inputStr))
			product.getPurchase().setInputMode(product.getPurchase().getPurchaseMode());
		else
			product.getPurchase().setInputMode(
				"quantity".equals(inputStr) ? Purchase.QUANTITY : 
//				"rank".equals(inputStr) ? Purchase.RANK : 
				"rank".equals(inputStr) ? Purchase.NONE : 
				"amount".equals(inputStr) ? Purchase.AMOUNT :
				"premium".equals(inputStr) ? Purchase.PREMIUM :
				"none".equals(inputStr) ? Purchase.NONE :
				"premium_and_amount".equals(inputStr) || "amount_and_premium".equals(inputStr) ? Purchase.PREMIUM_AND_AMOUNT :
				"premium_or_amount".equals(inputStr) ? Purchase.PREMIUM_OR_AMOUNT :
//				"rank_and_quantity".equals(inputStr) ? Purchase.RANK_AND_QUANTITY :
				"rank_and_quantity".equals(inputStr) ? Purchase.QUANTITY :
				Purchase.AMOUNT);
		
		product.setHidden("yes".equals(n1.getAttribute("hidden")));
		product.setMain(!"no".equals(n1.getAttribute("is_main")));
		product.setRider("yes".equals(n1.getAttribute("is_rider")));

		String productType = n1.getAttributeInOrder("type,type_code");
		if (!isEmpty(productType))
		{
//			String[] productTypeArray = productType.split(",");
//			List productTypeList = new ArrayList();
//			for (int j = 0; j < productTypeArray.length; j++)
//				productTypeList.add(productTypeArray[j]);
//			product.setProductType(productTypeList);
			
			product.setProductType(productType);
		}
		
		String channel = n1.getAttribute("channel");
		if (!isEmpty(channel))
			product.setChannel(channel.split(","));
		
		//累计风险保额
		
		//累积风险保费
		
		//数据
		
		//保费公式
		String premiumStr = n1.getAttribute("premium");
		if (!isEmpty(premiumStr))
			product.getPurchase().setPremium(FormulaUtil.formulaOf(premiumStr));

		String premiumFyStr = n1.getAttributeInOrder("premium_fy,premium_first_year"); //首年的总计保费，包括追加保费等
		if (!isEmpty(premiumFyStr))
			product.getPurchase().setPremiumFirstYear(FormulaUtil.formulaOf(premiumFyStr));
		
		//保额公式
		String amountStr = n1.getAttribute("amount");
		if (!isEmpty(amountStr))
			product.getPurchase().setAmount(FormulaUtil.formulaOf(amountStr));

		//份数公式
		String quantityStr = n1.getAttribute("quantity");
		if (!isEmpty(quantityStr))
			product.getPurchase().setQuantity(FormulaUtil.formulaOf(quantityStr));
		
//		//默认份数公式
//		String numberDefault = root.getAttribute("number_default");
//		if (!isEmpty(numberDefault))
//			productDef.setNumberDefault(ProductUtil.functionOf(numberDefault));

		//绑定关系
		String bind = n1.getAttribute("bind");
		if (!isEmpty(bind))
		{
			String[] defId = bind.split(",");
			for (int j=0;j<defId.length;j++)
			{
				product.addBind(defId[j]);
			}
		}
		
		product.setInitValue(new InitValue());
		
		return product;
	}
	
	private void parseRider(XmlNode e)
	{
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			if ("product".equals(n1.getName()) || "item".equals(n1.getName()))
			{
				String id = n1.getAttribute("id");
				product.addRider(id);
			}
		}
	}
	
	/**
	 * 数据源
	 */
	private void parseData(XmlNode e, String path)
	{
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			if ("item".equals(n1.getName()))
			{
				String parserId = n1.getAttribute("parser");
				String name = n1.getAttribute("name");
				String file = n1.getAttribute("file");
				String text = n1.getText();
				
				if (isEmpty(file) && n1.hasAttribute("value")) //这个是考虑向下兼容，现在都使用file项，并要求加后缀
					file = n1.getAttribute("value") + ".dat";
				
				//这里需要取产品的公司，是它的售卖公司，不一定是他的所属公司（可能是代理）
	//			String path = (String)company.getAdditional("path");
	//			path = path == null ? "" : path + File.separator;
				
				//数据源所需参数
				Map v = new HashMap();
				
//				File f = new File(path + value + ".prd");
//				if (f.exists() && f.isFile())
//					v.put("file", f);
//				else
//					v.put("file", new File(path + value + ".dat"));
				
				if (!isEmpty(file))
					v.put("file", new File(path + file));
				if (!isEmpty(text))
					v.put("text", text.trim());
				if (!isEmpty(name))
					v.put("name", name);
				
				DataParser parser = company.getDataParser(parserId);
				product.getDataHub().addSource(parser.newSource(v));
			}
		}
	}
	
	/**
	 *
	 */
	private void parseAttachment(XmlNode e)
	{
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			if (n1 != null && n1.hasChildren())
			{
				String filterName = n1.getAttribute("filter");
				if (filterName == null || "".equals(filterName))
					filterName = n1.getAttribute("name"); //旧的写法
				String parserName = n1.getAttribute("parser");
				if (!isEmpty(parserName))
				{
					AttachmentParser ap = (AttachmentParser)loader.getAttachmentParsers().get(parserName);
					if (ap != null)
					{
						product.setAttachment(n1.getName(), filterName, ap.parse(n1, AttachmentParser.XML));
					}
				}
			}
		}
	}
	
	private void parseBind(XmlNode e)
	{
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			if ("product".equals(n1.getName()) || "item".equals(n1.getName()))
			{
				String productId = n1.getAttribute("id");
				String when = n1.getAttribute("when");
				
				product.addBind(productId, when == null || "".equals(when) ? null : FormulaUtil.formulaOf(when));
			}
		}
	}

	private void parseDuty(XmlNode e)
	{
		List dutyList = new ArrayList();

		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			if ("item".equals(n1.getName()))
			{
				Duty duty = new Duty();
				duty.setName(n1.getAttribute("name"));
				duty.setCode(n1.getAttribute("code"));
				duty.setPremium(Script.scriptOf(n1.getAttribute("premium")));
				duty.setAmount(Script.scriptOf(n1.getText()));
				dutyList.add(duty);
			}
		}

		if (!dutyList.isEmpty())
			product.setDutyList(dutyList);
	}
	
	private void parseRule(XmlNode e)
	{
		String skipIdStr = e.getAttribute("skip");
		if (!isEmpty(skipIdStr))
		{
			String[] skipId = skipIdStr.split(",");
			for (int j = 0; j < skipId.length; j++)
				product.addRuleSkippedId(skipId[j]);
		}
		
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			if ("if".equals(n1.getName()) || "when".equals(n1.getName()))
				product.addRule(ruleOf(n1));
		}
	}
	
	/**
	 * 投保规则
	 */
	public static Rule ruleOf(XmlNode ruleNode)
	{
		Rule rule = new Rule();
		
		String id = ruleNode.getAttribute("id");
		if (!isEmpty(id))
			rule.setId(id);
		
		String condition = ruleNode.getAttribute("condition");
		if (!isEmpty(condition))
		{
			rule.setCondition(FormulaUtil.formulaOf(condition));

			String descType = ruleNode.getAttribute("desc");
			if ("formula".equals(descType))
				rule.setDesc(FormulaUtil.formulaOf(ruleNode.getText()));
			else
				rule.setDesc(ruleNode.getText());
		}
		else //<if>脚本</if>
		{
			rule.setCondition(FormulaUtil.formulaOf(ruleNode.getText()));
		}

		String level = ruleNode.getAttribute("level");
		if ("alert".equalsIgnoreCase(level))
			rule.setLevel(Rule.LEVEL_ALERT);
		
		String type = ruleNode.getAttribute("type");
		if ("customer".equalsIgnoreCase(type))
			rule.setType(Rule.TYPE_CUSTOMER);
		else if ("product".equalsIgnoreCase(type))
			rule.setType(Rule.TYPE_PRODUCT);
		else if ("plan".equalsIgnoreCase(type))
			rule.setType(Rule.TYPE_PLAN);
		else if ("proposal".equalsIgnoreCase(type))
			rule.setType(Rule.TYPE_PROPOSAL);
		
		String alert = ruleNode.getAttribute("alert");
		if (!isEmpty(alert))
		{
			String[] alertStr = alert.split(",");
			for (int i=0;i<alertStr.length;i++)
			{
				rule.addAlert(alertStr[i]);
			}
		}
		
		return rule;
	}
	
	/**
	 *
	 */
	private void parseInterest(XmlNode e)
	{
		String script = e.getText();
		VariableDefine interestVars = new VariableDefine();
		if (!isEmpty(script))
			interestVars.setScript(FormulaUtil.formulaOf(script));
		
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			if ("var".equals(n1.getName()) || "item".equals(n1.getName()))
			{
				String name = n1.getAttribute("name");
				String param = n1.getAttribute("param"); //公式的参数
				
				//type + value/text 是新的组合，formula/process是旧的组合
				String type = n1.getAttribute("type"); 
				
				String formula = n1.getAttribute("formula"); //公式
				String process = n1.getAttribute("process"); //引用类
//				String reference = n1.getAttribute("reference"); //相关的程序段，程序执行后所有值直接写入缓冲区
				String desc = n1.getAttribute("desc"); 
				
				if (!isEmpty(process)) //外加计算类扩展
				{
//					Formula p = null;
//					try
//					{
//						Class cls = Class.forName(process);
//
//						Constructor c = cls.getConstructor(new Class[] { String.class });
//						if (c == null)
//							p = (Formula)cls.newInstance();
//						else
//							p = (Formula)c.newInstance(new Object[] { name } );
//
//						interestVars.add(new Variable(name, p, null, desc));
//					} 
//					catch (Exception e1)
//					{
//						throw new ProductParseException("外接类构造错误", e1);
//					}
				}
				else if (!isEmpty(formula))
				{
					interestVars.add(new Variable(name, FormulaUtil.formulaOf(formula), isEmpty(param) ? null : param.split(","), true, desc));
				}
//				else if (!isEmpty(reference))
//				{
//					interestVars.add(new Variable(name, reference, null, desc));
//				}
				else if ("formula".equals(type) || isEmpty(type))
				{
					String value = n1.getAttribute("value");
					if (isEmpty(value))
						value = n1.getText();

					interestVars.add(new Variable(name, FormulaUtil.formulaOf(value), isEmpty(param) ? null : param.split(","), true, desc));
				}

			}
		}
		
		product.setInterestVars(interestVars);
	}
	
//	private void parseRisk(XmlNode e)
//	{
//		Map risk = new HashMap();
//		
//		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
//		{
//			XmlNode n1 = (XmlNode)iter.next();
//			
//			String name = n1.getName();
//			String text = n1.getText();
//			risk.put(name, FormulaUtil.formulaOf(text));
//		}
//		
//		product.setAdditional("risk", risk);
//	}
	
	private void parseInitValue(XmlNode e, String path)
	{
		String amount = e.getAttribute("amount");
		String premium = e.getAttribute("premium");
		String quantity = e.getAttribute("quantity");
		
		InitValue iv = product.getInitValue();
		iv.setAmount(FormulaUtil.formulaOf(amount));
		iv.setPremium(FormulaUtil.formulaOf(premium));
		iv.setQuantity(FormulaUtil.formulaOf(quantity));
		
		for (Iterator iter = e.getChildren("item").iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			
			String name = n1.getAttribute("name");
			String param = n1.getAttribute("param");
			String type = n1.getAttribute("type");
			String value = n1.getAttribute("value");
			String file = n1.getAttribute("file");
			String desc = n1.getAttribute("desc");
			
			if (isEmpty(value))
			{
				if (!isEmpty(file))
					value = Text.read(new File(path + file));
				else
					value = n1.getText();
				
				if (isEmpty(value)) //value为空，忽略该行
					continue;
			}
			
			if (isEmpty(type) || "formula".equals(type))
			{
//				System.out.println(value);
				Formula f = FormulaUtil.formulaOf(value);
				if (!isEmpty(param)) //如果有这个值表示是一个函数，函数有入参，结果根据不同的参数会变化，所以不能缓存结果。
					f = new Variable(name, f, param.split(","), false, desc);
				iv.set(name, f);
//				product.setAdditional(name, f);
			}
			else if ("integer".equals(type))
			{
				iv.set(name, Integer.valueOf(value));
//				product.setAdditional(name, Integer.valueOf(value));
			}
			else if ("boolean".equals(type))
			{
				iv.set(name, Boolean.valueOf(value));
//				product.setAdditional(name, Boolean.valueOf(value));
			}
			else if ("double".equals(type))
			{
				iv.set(name, Double.valueOf(value));
//				product.setAdditional(name, Double.valueOf(value));
			}
			else if ("string".equals(type))
			{
				iv.set(name, value.replaceAll("\\\\n", "\n"));
//				product.setAdditional(name, value.replaceAll("\\\\n", "\n"));
			}
			else if ("list".equals(type))
			{
				String[] s = value.trim().split(",");
				List v = new ArrayList();
				for (int j=0;j<s.length;j++)
				{
					String str = s[j].trim();
					if (str.startsWith("'") && str.endsWith("'"))
						v.add(str.substring(1, str.length() - 1));
					else
						v.add(Double.valueOf(str)); //%%%全局用double替换bigdecimal提高速度
				}
				iv.set(name, v);
//				product.setAdditional(name, v);
			}
		}
	}
	
	private void parseAccumulation(XmlNode e)
	{
		String accType = e.getAttribute("type");
		
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			String type = n1.getName();
			String formula = n1.getText();

			if ("item".equals(type))
			{
				type = n1.getAttributeInOrder("type,code");
				formula = n1.getAttribute("formula");
			}
			
			if ("premium".equals(accType))
				product.addAccumulativePremium(type, FormulaUtil.formulaOf(formula));
			else
				product.addAccumulativeAmount(type, FormulaUtil.formulaOf(formula));
		}
	}
	
	/**
	 * 缴费、保障、领取等
	 */
	private void parseOption(XmlNode e)
	{
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			parseParam(n1, n1.getName());
		}
	}
	
//	private void parseScript(Element e)
//	{
//		NodeList l1 = e.getChildNodes();
//		for (int x1 = 0; x1 < l1.getLength(); x1++)
//		{
//			if (l1.item(x1).getNodeType() != Node.ELEMENT_NODE)
//				continue;
//			
//			Element n1 = (Element)l1.item(x1);
//			if ("item".equals(n1.getTagName()))
//			{
//				String name = n1.getAttribute("name");
//				String text = n1.getTextContent();
//				
//				product.addScript(name, FormulaUtil.formulaOf(text));
//			}
//		}
//	}
	
	private void parseParam(XmlNode e, String key)
	{
		if ("input".equals(key))
			key = e.getAttribute("type");
		
		for (Iterator iter = e.getChildren("item").iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			String code = n1.getAttribute("code");
			String isDefault = n1.getAttribute("default");

			product.addOption(key, company.getOption(key, code));
			
			if ("yes".equalsIgnoreCase(isDefault))
				product.getInitValue().setOption(key, FormulaUtil.formulaOf("'" + code + "'"));
		}
	}

	private void parseInput(XmlNode e)
	{
		List fields = new ArrayList();
		for (Iterator iter = e.getChildren("item").iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			Field pi = new Field();
			pi.setName(n1.getAttribute("name"));
			pi.setLabel(n1.getAttribute("label"));
			pi.setType(n1.getAttribute("type"));
			pi.setLimit(n1.getAttribute("limit"));
			pi.setWidget(n1.getAttribute("widget"));
			pi.setOptions(Script.scriptOf(n1.getText()));
			fields.add(pi);
		}

		if (!fields.isEmpty())
			product.setInput(fields);
	}
	
	private void parsePortfolio(XmlNode e)
	{
		final Portfolio portfolio = new Portfolio();

		for (Iterator iter = e.getChildren("product").iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			final String productId = n1.getAttribute("id");
			final String parent = n1.getAttribute("parent");
			final String desc = n1.getAttribute("desc");
			final String c = n1.getAttributeInOrder("condition,c");

			String amount = n1.getAttribute("amount");
			String premium = n1.getAttribute("premium");
			String quantity = n1.getAttribute("quantity");

			final InitValue iv = new InitValue();
			iv.setAmount(FormulaUtil.formulaOf(amount));
			iv.setPremium(FormulaUtil.formulaOf(premium));
			iv.setQuantity(FormulaUtil.formulaOf(quantity));

			for (Iterator iter2 = n1.getChildren().iterator(); iter2.hasNext(); )
			{
				XmlNode n2 = (XmlNode)iter2.next();
				iv.set(n2.getName(), FormulaUtil.formulaOf(n2.getText()));
			}

			loader.addListener(new Runnable()
			{
				@Override
				public void run()
				{
					portfolio.addProduct(parent == null ? null : (Insurance)loader.products.get(parent), (Insurance)loader.products.get(productId), iv, FormulaUtil.formulaOf(c), desc);
				}
			});
		}

		product.setType(Insurance.PACKAGE);
		product.setPortfolio(portfolio);
	}
	
	private void parseRoot(XmlNode e, String path)
	{
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			
			if ("rider".equals(n1.getName()))
			{
				parseRider(n1);
			}
			else if ("bind".equals(n1.getName()))
			{
				parseBind(n1);
			}
			else if ("data".equals(n1.getName()))
			{
				parseData(n1, path);
			}
			else if ("attachment".equals(n1.getName()))
			{
				parseAttachment(n1);
			}
			else if ("duty".equals(n1.getName()))
			{
				parseDuty(n1);
			}
			else if ("rule".equals(n1.getName()))
			{
				parseRule(n1);
			}
			else if ("interest".equals(n1.getName()))
			{
				parseInterest(n1);
			}
			else if ("init".equals(n1.getName()))
			{
				parseInitValue(n1, path);
			}
			else if ("accumulation".equals(n1.getName()) || "risk".equals(n1.getName()))
			{
				parseAccumulation(n1);
			}
			else if ("option".equals(n1.getName()) || "param".equals(n1.getName()))
			{
				parseOption(n1);
			}
			if ("input".equals(n1.getName()))
			{
				parseInput(n1);
			}
			else if ("portfolio".equals(n1.getName()))
			{
				parsePortfolio(n1);
			}
//			else if ("script".equals(n1.getNodeName()))
//			{
//				parseScript(n1);
//			}
			else
			{
			}
		}
	}

	public static Date dateOf(String dateStr, String formatPattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		try 
		{
			if ((formatPattern == null) || "".equals(formatPattern)) 
				formatPattern = "yyyy-MM-dd HH:mm:ss";

			sdf.applyPattern(formatPattern);
			return sdf.parse(dateStr);
		}
		catch (Exception e) 
		{
			return null;
		}
	}
	
	public static boolean isEmpty(String s)
	{
		return s == null || "".equals(s.trim());
	}
}
