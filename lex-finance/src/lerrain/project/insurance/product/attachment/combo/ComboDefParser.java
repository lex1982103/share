package lerrain.project.insurance.product.attachment.combo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lerrain.project.insurance.product.attachment.AttachmentParser;
import lerrain.project.insurance.product.load.XmlNode;
import lerrain.tool.formula.FormulaUtil;

public class ComboDefParser implements AttachmentParser, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "combo_def";
	}

	public Object parse(Object source, int type)
	{
		Object result = null;
		
		if (type == AttachmentParser.XML)
		{
			XmlNode node = (XmlNode)source;
			result = parseBenefitCombo(node);
		}
		else
		{
		}
		
		return result;
	}

	public static Combo parseBenefitCombo(XmlNode e)
	{
		Combo interestCombo = new Combo();
		
		String hideStr = e.getAttribute("hide");
		if (hideStr != null)
		{
			String[] h = hideStr.split(",");
			for (int i=0;i<h.length;i++)
				interestCombo.addHide(h[i]);
		}
		
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			String nodeName = n1.getName();
			if ("item".equals(nodeName))
			{
				interestCombo.addCol(parseComboRow(n1));
			}
			else if ("items".equals(nodeName))
			{
				String name = n1.getAttribute("name");

				List rows = new ArrayList();
				
//				ComboCol col = new ComboCol(name, rows);
				ComboCol col = new ComboCol(name != null ? FormulaUtil.formulaOf("'" + name + "'") : FormulaUtil.formulaOf(n1.getText()), rows);
				
				for (Iterator j = n1.getChildren("item").iterator(); j.hasNext(); )
				{
					XmlNode n2 = (XmlNode)j.next();
					ComboCol subcol = parseComboRow(n2);
					subcol.setParent(col);
					rows.add(subcol);
				}
				
				String rowspan = n1.getAttribute("row");
				if (rowspan != null)
					col.setRow(Integer.parseInt(rowspan));

				interestCombo.addCol(col);
			}
			else if ("text".equals(nodeName))
			{
				String code = n1.getAttribute("code");
				String type = n1.getAttribute("type");
				String count = n1.getAttribute("count");

				if (count == null)
					count = "yes";
				
				String text = n1.getText();
				if (n1.hasAttribute("value"))
					text = n1.getAttribute("value");
				if (n1.hasAttribute("text"))
					text = n1.getAttribute("text");

				ComboText c = ComboText.comboTextOf(!"no".equalsIgnoreCase(count) && !"false".equalsIgnoreCase(count), type, text);
				interestCombo.addComboText(code, c);
			}
		}
		
		return interestCombo;
	}
	
	private static ComboCol parseComboRow(XmlNode node)
	{
		String code = node.getAttribute("code");
		String mode = node.getAttribute("mode");
		String name = node.getAttribute("name");
		String add = node.getAttribute("add");
		String style = node.getAttribute("style");
		String value = node.getAttribute("value");

		int m = "add".equalsIgnoreCase(mode) ? ComboCol.MODE_ADD :
				"accumulate".equalsIgnoreCase(mode) || "acc".equalsIgnoreCase(mode) ? ComboCol.MODE_ACCUMULATE :
				"cover".equalsIgnoreCase(mode) ? ComboCol.MODE_COVER :
				ComboCol.MODE_ADD;
		
//		ComboCol col = new ComboCol(code, name.replaceAll("[\\\\][n]", "\n"), m);
		ComboCol col = new ComboCol(code, name != null ? FormulaUtil.formulaOf("'" + name.replaceAll("[\\\\][n]", "\n") + "'") : FormulaUtil.formulaOf(node.getText()), m);
		col.setAddCol(add);

		if (value != null)
			col.setValue(FormulaUtil.formulaOf(value));

		if (style != null)
			col.setStyle(style);

		String rowspan = node.getAttribute("row");
		if (rowspan != null)
			col.setRow(Integer.parseInt(rowspan));
//		String colspan = XmlUtil.getOptions(node, "col");
//		if (colspan != null)
//			col.setCol(Integer.parseInt(colspan));
		
		return col;
	}
}
