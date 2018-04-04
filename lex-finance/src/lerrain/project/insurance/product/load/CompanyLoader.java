package lerrain.project.insurance.product.load;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lerrain.project.insurance.product.*;
import lerrain.project.insurance.product.attachment.AttachmentParser;
import lerrain.tool.data.source.DdsAuto;
import lerrain.tool.formula.FormulaUtil;

public class CompanyLoader
{
	String resDir;
	
	Loader loader;
	
	Company company;

	public CompanyLoader(String resDir, Loader loader)
	{
		this.resDir = resDir;
		this.loader = loader; 
	}
	
	public Company loadCompany(String parent, XmlNode node)
	{
		company = newInstance(node.getAttribute("id"), parent == null ? null : (Company)loader.getCompanys().get(parent));
		company.setName(node.getAttribute("name"));
		
		//这4项是考虑向下兼容
		company.addDataParser("file.auto", new DdsAuto());
		company.addDataParser("file.dat", new DdsAuto());
		company.addDataParser("file.prd", new DdsAuto());
		company.addDataParser("file.sig", new DdsAuto());
		//dds v1~6
		company.addDataParser("dds", new DdsAuto());

		loader.getCompanys().put(company.getId(), company);
		
		try 
		{
			readCompany(node);
		} 
		catch (Exception e) 
		{
			throw new ProductParseException("无法解析公司数据", e);
		}
		
		return company;
	}
	
	protected Company getCompany()
	{
		return company;
	}
	
	protected Company newInstance(String id, Company abstractCompany)
	{
		return new Company(id, abstractCompany);
	}
	
	private static List getFileList(String name)
	{
		List r = new ArrayList();
		
		if (name.indexOf("?") >= 0 || name.indexOf("*") >= 0)
		{
			int l = name.lastIndexOf("/");
			String path = name.substring(0, l);

			name = name.substring(l + 1);
			name = name.replaceAll("[.]", "\\\\.");
			name = name.replaceAll("[*]", ".*");
			name = name.replaceAll("[?]", ".?");
			name = "^" + name + "$";
			
			File file = new File(path);
			String[] flist = file.list();
			for(int i=0;flist!= null && i<flist.length;i++)
			{
				if (flist[i].matches(name))
				{
					r.add(path + File.separator + flist[i]);
				}
			}
		}
		else
			r.add(name);
		
		return r;
	}
	
	private void readCompany(XmlNode e) throws Exception
	{
		//一个company的定义是由多个xml组成的，每个都要加入
		for (Iterator iter = e.getChildren("item").iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			String matchName = resDir + File.separator + n1.getAttribute("src");
			List fileList = getFileList(matchName);
			for (int i = 0; i < fileList.size(); i++)
			{
				String fileName = (String)fileList.get(i);
//				company.setAdditional("path", new File(fileName).getParent());

				readFile(new File(fileName));
			}
		}
	}
	
	private void readFile(File f) throws Exception
	{
		XmlNode e = null;
		
		String fileName = f.getAbsolutePath();
		if (fileName.endsWith(".xml"))
		{
//			File pakFile = new File(fileName.substring(0, fileName.length() - 4) + ".prd");
//			if (pakFile.exists() && pakFile.isFile()) //如果同名xml和prd共存，则忽略xml
//				return;
			
			e = XmlNode.nodeOf(new File(fileName));
		}
//		else if (fileName.endsWith(".prd"))
//		{
//			File pakFile = new File(fileName);
//			List nameList = PackFile.list(pakFile);
//			for (int j=0;nameList != null && j<nameList.size();j++)
//			{
//				String name = (String)nameList.get(j);
//				if (name.endsWith(".xml"))
//				{
//					InputStream is = PackFile.open(pakFile, name);
//					e = loadXml(is).getDocumentElement();
//					is.close();
//				}
//			}
//		}
		
		if (e == null)
		{
			System.out.println(f.getAbsolutePath());
			return;
		}
		
		if (!"declare".equals(e.getName()))
			return;
		
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			
			if ("variable".equals(n1.getName()))
			{
				String typeStr = n1.getAttribute("type");
				VariableDefine interestVars = "plan".equals(typeStr) ? company.getPlanVars() : "product".equals(typeStr) ? company.getProductVars() : null;
				if (interestVars != null)
					preloadVariable(interestVars, n1, false);
			}
			else if ("function".equals(n1.getName()))
			{
				String typeStr = n1.getAttribute("type");
				VariableDefine interestVars = "plan".equals(typeStr) ? company.getPlanVars() : "product".equals(typeStr) ? company.getProductVars() : null;
				if (interestVars != null)
					preloadVariable(interestVars, n1, true);
			}
//			else if ("function".equals(n1.getTagName()))
//			{
//				preloadFunction(n1);
//			}
			else if ("accumulation".equals(n1.getName()) || "risk".equals(n1.getName()))
			{
				preloadAccumulation(n1);
			}
			else if ("rule".equals(n1.getName()))
			{
				preloadRule(n1);
			}
			else if ("attachment".equals(n1.getName()))
			{
				preloadAttachment(n1);
			}
			else if ("parameter".equals(n1.getName()))
			{
				preloadParameter(n1);
			}
			else if ("agent".equals(n1.getName())) //代理的产品
			{
				/*
				 * 代理先通过直接配的形式实现
				 * 以后与代理公司接触后了解细节需求在决定下一步如何做
				 */
//				preloadAgent(n1);
			}
			else if ("product".equals(n1.getName()))
			{
				try
				{
					Insurance ins = new ProductLoader(company, loader).loadProduct(n1, f.getParent());
					company.addProduct(ins);
					loader.products.put(ins.getId(), ins);
				}
				catch (Exception e2)
				{
					System.out.println("[lex-finance]跳过 -- " + f.getAbsolutePath());
					e2.printStackTrace();
				}
			}
			else if ("package".equals(n1.getName()))
			{
				try
				{
					PackageIns ins = new PackageLoader(company, loader).loadProduct(n1);
					company.addPackage(ins);
					loader.products.put(ins.getId(), ins);
				}
				catch (Exception e2)
				{
					System.out.println("[lex-finance]跳过 -- " + f.getAbsolutePath());
					e2.printStackTrace();
				}
			}
		}
	}
	
//	private void preloadAgent(Element e)
//	{
//		NodeList l1 = e.getElementsByTagName("product");
//		for (int x1 = 0; x1 < l1.getLength(); x1++)
//		{
//			Element n1 = (Element)l1.item(x1);
//			String productsId[] = n1.getTextContent().trim().split(",");
//			
//			/*
//			 * 由于代理公司可能会对产品做一定修改（比如打折优惠、加更多限制等等）
//			 * 所以不能直接使用以前的产品对象，需要另拷贝一份
//			 */
//		}
//	}
	
	/**
	 * 初始化变量与对象映射
	 */
	private void preloadVariable(VariableDefine interestVar, XmlNode e, boolean func)
	{
		for (Iterator iter = e.getChildren("item").iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			
			String name = n1.getAttribute("name");
			String type = n1.getAttribute("type");
			String param = n1.getAttribute("param"); //公式的参数
			String desc = n1.getAttribute("desc"); 
			
			String formula = n1.getAttribute("formula"); //公式
			String process = n1.getAttribute("process"); //引用类
			
			if (!isEmpty(process)) //外加计算类扩展
			{
//				Formula p = null;
//				try
//				{
//					Class cls = Class.forName(process);
//
//					Constructor c = cls.getConstructor(new Class[] { String.class });
//					if (c == null)
//						p = (Formula)cls.newInstance();
//					else
//						p = (Formula)c.newInstance(new Object[] { name } );
//
//					interestVar.add(new Variable(name, p, null, desc));
//				} 
//				catch (Exception e1)
//				{
//					throw new ProductParseException("外接类构造错误", e1);
//				}
			}
			else if (!isEmpty(formula))
			{
				interestVar.add(new Variable(name, FormulaUtil.formulaOf(formula), isEmpty(param) ? (func ? new String[] {} : null) : param.split(","), false, desc));
			}
			else if ("formula".equals(type) || isEmpty(type))
			{
				String value = n1.getAttribute("value");
				if (isEmpty(value))
					value = n1.getText();

				interestVar.add(new Variable(name, FormulaUtil.formulaOf(value), isEmpty(param) ? (func ? new String[] {} : null) : param.split(","), false, desc));
			}
		}
	}
	
//	/**
//	 * 初始化自定义函数表
//	 * @param functionSet
//	 * @param parentNode
//	 * @throws Exception
//	 */
//	private void preloadFunction(Element e)
//	{
//		NodeList l1 = e.getElementsByTagName("function");
//		for (int x1 = 0; x1 < l1.getLength(); x1++)
//		{
//			Element n1 = (Element)l1.item(x1);
//
//			String name = n1.getAttribute("name");
//			String param = n1.getAttribute("param");
////			String type = node.getAttribute("type");
//				
//			Node childNode = n1.getFirstChild();
//			String formula = childNode == null ? "" : childNode.getNodeValue().trim();
//
//			company.addFunction(name, ProductUtil.functionOf(formula), param == null ? null : param.split(","));
//		}
//	}

	private void preloadAccumulation(XmlNode e)
	{
		for (Iterator iter = e.getChildren("product").iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			String type = n1.getAttribute("type");
			Map accu = new HashMap();
			
			for (Iterator j = n1.getChildren().iterator(); j.hasNext(); )
			{
				XmlNode n2 = (XmlNode)j.next();

				String code = n2.getName();
				String formula = n2.getText();
				accu.put(code, FormulaUtil.formulaOf(formula));
			}

			company.addAccumulation(type, accu);
		}
	}

	private void preloadRule(XmlNode e)
	{
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();
			if ("if".equals(n1.getName()) || "when".equals(n1.getName()))
				company.addRule(ProductLoader.ruleOf(n1));
		}
	}
	
//	private void preloadRisk(XmlNode e)
//	{
//		String type = e.getAttribute("type");
//		
//		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
//		{
//			XmlNode n1 = (XmlNode)iter.next();
//			
//		}
//	}
	
	/**
	 * attachment.xml
	 */
	private void preloadAttachment(XmlNode e)
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
						company.setAttachment(n1.getName(), filterName, ap.parse(n1, AttachmentParser.XML));
				}
			}
		}
	}

	/**
	 * 初始化产品参数，缴费、保障、领取等
	 */
	private void preloadParameter(XmlNode e)
	{
		for (Iterator i = e.getChildren().iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();

			String name = n1.getName();
			String varName = n1.getAttribute("name");
			company.addOptionType(name, varName);

			for (Iterator j = n1.getChildren("item").iterator(); j.hasNext(); )
			{
				XmlNode n2 = (XmlNode)j.next();

				Option item = new Option();
				
				String code = n2.getAttribute("code");
				String mode = n2.getAttribute("mode");
				String value = n2.getAttribute("value");
				String desc = n2.getAttribute("desc");
				String show = n2.getAttribute("show");

				item.setCode(code);
				
				if (!isEmpty(show))
					item.setShow(show);
				
				if (!isEmpty(desc))
					item.setDesc(FormulaUtil.formulaOf(desc));
				
				if (!isEmpty(value))
					item.setValue(FormulaUtil.formulaOf(value));
				
				if (!isEmpty(mode))
					item.setMode(FormulaUtil.formulaOf(mode));
				
				company.addOption(name, item);
			}
		}
	}
	
	public static boolean isEmpty(String s)
	{
		return s == null || "".equals(s.trim());
	}
}
