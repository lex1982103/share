package lerrain.project.insurance.product.attachment.liability;

import java.util.Iterator;

import lerrain.project.insurance.product.attachment.AttachmentParser;
import lerrain.project.insurance.product.attachment.table.TableParser;
import lerrain.project.insurance.product.load.XmlNode;
import lerrain.tool.formula.FormulaUtil;

public class LiabilityParser implements AttachmentParser
{

	public String getName()
	{
		return "liability";
	}

	public Object parse(Object source, int type)
	{
		Object result = null;
		
		if (type == AttachmentParser.XML)
		{
			result = prepareXml((XmlNode)source);
		}
		else
		{
			
		}
		
		return result;
	}
	
	private Object prepareXml(XmlNode e)
	{
		LiabilityDef coverage = new LiabilityDef();
		coverage.setType(LiabilityDef.TYPE_GROUP);
		
		for (Iterator i = e.getChildren("paragraph").iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();

			String title = n1.getAttribute("title");
			
			LiabilityDef cp = new LiabilityDef();
			cp.setType(LiabilityDef.TYPE_GROUP);
			cp.setTitle(title);
			
			String condition = n1.getAttributeInOrder("condition,c");
			if (condition != null)
				cp.setCondition(FormulaUtil.formulaOf(condition));
			
			for (Iterator j = n1.getChildren().iterator(); j.hasNext(); )
			{
				XmlNode n2 = (XmlNode)j.next();
				
				if ("item".equals(n2.getName()))
				{
					LiabilityDef item = new LiabilityDef();
					item.setType(LiabilityDef.TYPE_TEXT);

					String cstr = n2.getAttributeInOrder("condition,c");
					if (cstr != null)
						item.setCondition(FormulaUtil.formulaOf(cstr));
					
					String text = n2.getText();
					text = text.replaceAll("[\\\\][n]", "\n");

					String type = n2.getAttribute("type");
					if ("formula".equals(type))
						item.setContent(FormulaUtil.formulaOf(text));
					else
						item.setContent(text);
					
					cp.addParagraph(item);
				}
				else if ("table".equals(n2.getName()))
				{
					LiabilityDef item = new LiabilityDef();
					item.setType(LiabilityDef.TYPE_TABLE);
					item.setContent(TableParser.buildTable(n2));

					String cstr = n2.getAttributeInOrder("condition,c");
					if (cstr != null)
						item.setCondition(FormulaUtil.formulaOf(cstr));

					cp.addParagraph(item);
				}
			}

			coverage.addParagraph(cp);
		}

		return coverage;
	}
}
