package lerrain.project.insurance.product.attachment.tgraph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lerrain.project.insurance.product.attachment.AttachmentParser;
import lerrain.project.insurance.product.load.XmlNode;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;

public class TGraphParser implements AttachmentParser, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "tgraph";
	}

	public Object parse(Object source, int type)
	{
		Object result = null;
		
		if (type == TGraphParser.XML)
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
		List items = new ArrayList();
		
		for (Iterator iter = e.getChildren("item").iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			TGraphItemDynamic gid;

			Formula f = n1.hasAttribute("value") ? FormulaUtil.formulaOf(n1.getAttribute("value")) : null;
			
			if (n1.hasAttribute("desc"))
				gid = new TGraphItemDynamic(FormulaUtil.formulaOf(n1.getAttribute("desc")), f);
			else
				gid = new TGraphItemDynamic(n1.getText(), f);
			
			if (n1.hasAttribute("condition"))
				gid.setCondition(FormulaUtil.formulaOf(n1.getAttribute("condition")));
			
			String bold = n1.getAttribute("bold");
			gid.setBold("yes".equalsIgnoreCase(bold));
			
			items.add(gid);
		}

		return items;
	}
}
