package lerrain.project.insurance.product.attachment.document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lerrain.project.insurance.product.attachment.AttachmentParser;
import lerrain.project.insurance.product.attachment.table.TableParser;
import lerrain.project.insurance.product.attachment.table.TableReset;
import lerrain.project.insurance.product.load.XmlNode;
import lerrain.tool.formula.FormulaUtil;

public class DocumentParser implements AttachmentParser
{

	public String getName()
	{
		return "document";
	}

	public Object parse(Object source, int type)
	{
		Object result = null;
		
		if (type == AttachmentParser.XML)
		{
			result = prepareXml((XmlNode)source);
		}
		
		return result;
	}
	
	private Object prepareXml(XmlNode e)
	{
		List r = new ArrayList();
		
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			if ("table".equals(n1.getName()))
			{
				r.add(TableParser.buildTable(n1));
			}
			else if ("text".equals(n1.getName()))
			{
				String type = n1.getAttribute("type");
				String style = n1.getAttribute("style");

				DynamicText text = new DynamicText();
				text.setStyle(style);

				if ("formula".equals(type))
					text.setText(FormulaUtil.formulaOf(n1.getText()));
				else
					text.setText(n1.getText());
				
				String condition = n1.getAttribute("condition");
				if (condition != null)
					text.setCondition(FormulaUtil.formulaOf(condition));
				
				String bold = n1.getAttribute("bold");
				text.setBold("yes".equalsIgnoreCase(bold));
				
				r.add(text);
			}
			else if ("reset".equals(n1.getName()))
			{
				TableReset reset = new TableReset();
				reset.setNewPage("yes".equalsIgnoreCase(n1.getAttribute("new_page")));
				
				String skip = n1.getAttribute("new_page");
				if (skip != null && !"".equals(skip))
					reset.setSkip(Integer.parseInt(skip));
				
				r.add(reset);
			}
		}

		return r;
	}
}
