package lerrain.project.insurance.product.attachment.combo;

import java.io.Serializable;
import java.util.Iterator;

import lerrain.project.insurance.product.attachment.AttachmentParser;
import lerrain.project.insurance.product.load.XmlNode;
import lerrain.tool.formula.FormulaUtil;

public class ComboParser implements AttachmentParser, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "combo";
	}

	public Object parse(Object source, int type)
	{
		Object result = null;
		
		if (type == AttachmentParser.XML)
		{
			XmlNode node = (XmlNode)source;
			result = parseBenefitItem(node);
		}
		else
		{
		}
		
		return result;
	}
	
	private ComboSingle parseBenefitItem(XmlNode e)
	{
		ComboSingle bcs = new ComboSingle();
		
		for (Iterator i = e.getChildren().iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();

			String nodeName = n1.getName();
			if ("year".equals(nodeName))
			{
				String from = n1.getAttribute("from");
				String to = n1.getAttribute("to");
				String varName = n1.getAttribute("name");
				
				for (Iterator j = n1.getChildren().iterator(); j.hasNext(); )
				{
					XmlNode node2 = (XmlNode)j.next();
					
					if ("item".equals(node2.getName()))
					{
						String code = node2.getAttribute("code");
						String formula = node2.getAttribute("formula");
						String visible = node2.getAttribute("visible");
						
						ComboItem bci = new ComboItem(FormulaUtil.formulaOf(from), FormulaUtil.formulaOf(to), varName, code, FormulaUtil.formulaOf(formula));
						
						if (visible != null)
							bci.setVisible(FormulaUtil.formulaOf(visible));
						
						bcs.addItem(bci);
					}
				}
			}
			else if ("text".equals(nodeName))
			{
				String code = n1.getAttribute("code");
				String type = n1.getAttribute("type");
				String count = n1.getAttribute("count");

				bcs.addText(code);

				if (count == null)
					count = "yes";
				
				String text = n1.getText();
				if (n1.hasAttribute("value"))
					text = n1.getAttribute("value");
				if (n1.hasAttribute("text"))
					text = n1.getAttribute("text");

				ComboText c = ComboText.comboTextOf(!"no".equalsIgnoreCase(count) && !"false".equalsIgnoreCase(count), type, text);
				bcs.addComboText(code, c);
			}
			else if ("table".equals(nodeName))
			{
				Combo combo = ComboDefParser.parseBenefitCombo(n1);
				bcs.setSelfTable(combo);
			}
		}
		
		return bcs;
	}
}
