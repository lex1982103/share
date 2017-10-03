package lerrain.project.insurance.product.attachment.axachart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lerrain.project.insurance.product.attachment.AttachmentParser;
import lerrain.project.insurance.product.load.XmlNode;
import lerrain.tool.formula.FormulaUtil;

public class AxaChartParser implements AttachmentParser, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "chart@axa";
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

		for (Iterator i = e.getChildren().iterator(); i.hasNext();)
		{
			XmlNode n1 = (XmlNode) i.next();
			if ("year_data".equals(n1.getName()))
			{
				chartList.add(buildYearData(n1));
			}

			if ("chart".equals(n1.getName()))
			{
				chartList.add(buildChart(n1));
			}
		}

		return chartList;
	}

	private static AppendYearData buildYearData(XmlNode node)
	{
		String from = node.getAttribute("from");
		String to = node.getAttribute("to");
		String step = node.getAttribute("step");
		String loopVar = node.getAttribute("name");
		String condition = node.getAttribute("condition");
		String code = node.getAttribute("code");

		AppendYearData appendYearData = new AppendYearData(FormulaUtil.formulaOf(from), FormulaUtil.formulaOf(to),
				FormulaUtil.formulaOf(step), loopVar);
		if (condition != null)
			appendYearData.setCondition(FormulaUtil.formulaOf(condition));
		if (code != null)
			appendYearData.setCode(code);

		for (Iterator i = node.getChildren("item").iterator(); i.hasNext();)
		{
			XmlNode n1 = (XmlNode) i.next();
			appendYearData.addItem(buildYearItem(n1));
		}

		return appendYearData;
	}

	private static AppendYearItem buildYearItem(XmlNode node)
	{
		String title = node.getAttribute("title");
		String desc = node.getAttribute("desc");
		String mode = node.getAttribute("mode");
		String content = node.getText() == null ? null : node.getText().trim().replaceAll("\\\\n", "\n");
		AppendYearItem yearItem = new AppendYearItem(title, desc, mode, !"".equals(content) ? FormulaUtil.formulaOf(content)
				: null);
		String code = node.getAttribute("code");
		if (code != null)
		{
			yearItem.setCode(code);
		}
		return yearItem;
	}

	private static BenefitChart buildChart(XmlNode detail)
	{
		BenefitChart chart = new BenefitChart();

		String desc = detail.getAttribute("desc");
		if (desc != null)
		{
			chart.setAdditional("desc", desc.trim().replaceAll("\\\\n", "\n"));
		}

		String width = detail.getAttribute("width");
		if (width != null)
			chart.setAdditional("width", width.trim());

		String height = detail.getAttribute("height");
		if (height != null)
			chart.setAdditional("height", height.trim());

		String border = detail.getAttribute("border");
		if (border != null)
			chart.setAdditional("border", border.trim());

		String code = detail.getAttribute("code");
		if (code != null)
			chart.setCode(code);

		for (Iterator i = detail.getChildren().iterator(); i.hasNext();)
		{
			XmlNode n1 = (XmlNode) i.next();
			if ("axis".equals(n1.getName()))
			{
				chart.setAxis(buildAxis(n1));
			}

			if ("year_data".equals(n1.getName()))
			{
				chart.addYearData(buildYearData(n1));
			}
		}

		return chart;
	}

	private static AppendAxis buildAxis(XmlNode loopNode)
	{
		String from = loopNode.getAttribute("from");
		String to = loopNode.getAttribute("to");
		String var = loopNode.getAttribute("name");

		AppendAxis axis = new AppendAxis(FormulaUtil.formulaOf(from), FormulaUtil.formulaOf(to), var);

		for (Iterator i = loopNode.getChildren("item").iterator(); i.hasNext();)
		{
			XmlNode n1 = (XmlNode) i.next();
			axis.addElement(buildItem(n1, from, to));
		}

		return axis;
	}

	private static AppendItem buildItem(XmlNode itemNode, String from, String to)
	{
		String name = itemNode.getAttribute("name");
		if (name != null)
		{
			name = name.trim().replaceAll("\\\\n", "\n");
		}
		String type = itemNode.getAttribute("type");
		String color = itemNode.getAttribute("color");

		from = itemNode.getAttribute("from") == null ? from : itemNode.getAttribute("from");
		to = itemNode.getAttribute("to") == null ? to : itemNode.getAttribute("to");

		AppendItem item = new AppendItem(name, type, color);
		if (null != from && from.length() != 0)
			item.setStart(FormulaUtil.formulaOf(from));

		if (null != to && to.length() != 0)
			item.setEnd(FormulaUtil.formulaOf(to));

		String text = itemNode.getText();
		if (text != null)
			item.setContent(FormulaUtil.formulaOf(text.trim().replaceAll("\\\\n", "\n")));

		return item;
	}

}
