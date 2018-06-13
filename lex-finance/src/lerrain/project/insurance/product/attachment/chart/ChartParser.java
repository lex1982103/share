package lerrain.project.insurance.product.attachment.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lerrain.project.insurance.product.attachment.AttachmentParser;
import lerrain.project.insurance.product.load.XmlNode;
import lerrain.tool.formula.FormulaUtil;

public class ChartParser implements AttachmentParser, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "chart";
	}

	public Object parse(Object source, int type)
	{
		Object result = null;
		
		if (type == AttachmentParser.XML)
			result = prepareXml((XmlNode)source);
		
		return result;
	}
	
	private Object prepareXml(XmlNode e)
	{
		List chartList = new ArrayList();
		
		for (Iterator i = e.getChildren("chart").iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();
			chartList.add(buildChart(n1));
		}

		return chartList;
	}
	
	public static ChartDef buildChart(XmlNode e)
	{
		String from = e.getAttribute("from");
		String to = e.getAttribute("to");
		String var = e.getAttribute("name");
		
		ChartDef chart = new ChartDef(FormulaUtil.formulaOf(from), FormulaUtil.formulaOf(to));
		chart.setVarName(var);
		
		for (Iterator i = e.getChildren().iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();
			if (n1.getName() != null && !"".equals(n1.getName()))
			{
				ChartItem item = new ChartItem(n1.getName(), FormulaUtil.formulaOf(n1.getText()));
				item.setName(n1.getAttribute("name"));
				item.setColor(n1.getAttribute("color"));
				chart.addItem(item);
			}
		}
		
		return chart;
	}
}
