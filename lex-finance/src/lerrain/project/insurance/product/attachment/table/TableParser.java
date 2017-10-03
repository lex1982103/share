package lerrain.project.insurance.product.attachment.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lerrain.project.insurance.product.attachment.AttachmentParser;
import lerrain.project.insurance.product.attachment.document.DynamicText;
import lerrain.project.insurance.product.load.XmlNode;
import lerrain.tool.formula.FormulaUtil;

public class TableParser implements AttachmentParser, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "table";
	}

	public Object parse(Object source, int type)
	{
		Object result = null;
		
		if (type == TableParser.XML)
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
		List tableList = new ArrayList();
		
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			if ("table".equals(n1.getName()))
			{
				tableList.add(buildTable(n1));
			}
			else if ("supply".equals(n1.getName()))
			{
				tableList.add(buildSupply(n1));
			}
			else if ("text".equals(n1.getName()))
			{
//				TableTextDef text = new TableTextDef();
//				text.setText(n1.getText());
//				
//				String condition = n1.getAttribute("condition");
//				if (condition != null)
//					text.setCondition(FormulaUtil.formulaOf(condition));
//				
//				String bold = n1.getAttribute("bold");
//				text.setBold("yes".equalsIgnoreCase(bold));
//				
//				tableList.add(text);
				
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
				
				tableList.add(text);
			}
			else if ("reset".equals(n1.getName()))
			{
				TableReset reset = new TableReset();
				reset.setNewPage("yes".equalsIgnoreCase(n1.getAttribute("new_page")));
				
				String skip = n1.getAttribute("new_page");
				if (skip != null && !"".equals(skip))
					reset.setSkip(Integer.parseInt(skip));
				
				tableList.add(reset);
			}
		}

		return tableList;
	}
	
	public static TableSupply buildSupply(XmlNode e)
	{
		TableSupply ts = new TableSupply(e.getAttribute("code"));
		
		for (Iterator i = e.getChildren().iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();
			
			if ("blank".equals(n1.getName()))
			{
				TableBlankSupply tbs = new TableBlankSupply(n1.getAttribute("code"), FormulaUtil.formulaOf(n1.getText()));
				tbs.setMode("acc".equals(n1.getAttribute("mode")) ? TableBlankSupply.MODE_ACC : TableBlankSupply.MODE_ADD);
				ts.addBlankSupply(tbs);
			}
		}
		
		return ts;
	}
	
	public static TableDef buildTable(XmlNode e)
	{
		TableDef table = new TableDef();
		
		table.setName(e.getAttribute("name"));
		
		table.setAdditional("desc", e.getAttribute("desc"));
		table.setAdditional("style", e.getAttribute("style"));
		
		String code = e.getAttribute("code");
		if (code != null)
			table.setCode(code);
		
		String condition = e.getAttributeInOrder("condition,c");
		if (condition != null)
			table.setAdditional("condition", FormulaUtil.formulaOf(condition));

		String align = e.getAttribute("align");
		if (align != null)
			table.setAdditional("align", align.trim());

		String windage = e.getAttribute("windage");
		if (windage != null)
			table.setAdditional("windage", windage.trim());

		String width = e.getAttribute("width");
		if (width != null)
			table.setAdditional("width", width.trim());

		String border = e.getAttribute("border");
		if (border != null)
			table.setAdditional("border", border.trim());

		for (Iterator i = e.getChildren().iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();
			
			if ("row".equals(n1.getName()))
			{
				table.addRow(buildRow(n1));
			}
			else if ("loop".equals(n1.getName()))
			{
				table.addLoop(buildLoop(n1));
			}
			else if ("text".equals(n1.getName()))
			{
				if (table.size() > 0)
				{
					List list = (List)table.getAdditional("text_peroration");
					if (list == null)
					{
						list = new ArrayList();
						table.setAdditional("text_peroration", list);
					}
					list.add(n1.getText());
				}
				else
				{
					List list = (List)table.getAdditional("text_foreword");
					if (list == null)
					{
						list = new ArrayList();
						table.setAdditional("text_foreword", list);
					}
					list.add(n1.getText());
				}
			}
		}
		
		return table;
	}
	
	private static TableRow buildRow(XmlNode e)
	{
		TableRow row = new TableRow();
		String type = e.getAttribute("type");
		if ("title".equals(type))
			row.setType(TableRow.TYPE_TITLE);
		
		String c = e.getAttributeInOrder("condition,c");
		if (c != null)
			row.setCondition(FormulaUtil.formulaOf(c));
		
		for (Iterator i = e.getChildren("blank").iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();
			row.addBlank(buildBlank(n1));
		}

		return row;
	}
	
	private static TableLoop buildLoop(XmlNode loopNode)
	{
		String from = loopNode.getAttribute("from");
		String to = loopNode.getAttribute("to");
		String var = loopNode.getAttribute("name");
		
		TableLoop loop = new TableLoop(FormulaUtil.formulaOf(from), FormulaUtil.formulaOf(to), var);

		for (Iterator i = loopNode.getChildren("row").iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();
			loop.addElement(buildRow(n1));
		}

		return loop;
	}
	
	private static TableBlank buildBlank(XmlNode blankNode)
	{
		String rowStr = blankNode.getAttribute("row");
		String colStr = blankNode.getAttribute("col");
		int row = rowStr == null || "".equals(rowStr) ? 1 : Integer.parseInt(rowStr);
		int col = colStr == null || "".equals(colStr) ? 1 : Integer.parseInt(colStr);
		
		TableBlank blank = new TableBlank(row, col);

		String code = blankNode.getAttribute("code");
		if (code != null)
			blank.setCode(code);

		String align = blankNode.getAttribute("align");
		blank.setAlign(align);
		String style = blankNode.getAttribute("style");
		blank.setStyle(style);

		String width = blankNode.getAttribute("width");
		if (width != null && !"".equals(width))
		{
			width = width.trim();
			if (width.endsWith("%"))
				blank.setWidthScale(Float.parseFloat(width.substring(0, width.length() - 1)) / 100);
			else
				blank.setWidthScale(Float.parseFloat(width));
		}
		
		String c = blankNode.getAttributeInOrder("condition,c");
		if (c != null)
			blank.setCondition(FormulaUtil.formulaOf(c));

		String text = blankNode.getText();
		if (text != null && !"".equals(text.trim()))
			blank.setContent(FormulaUtil.formulaOf(text.trim().replaceAll("\\\\n", "\n")));
		
		return blank;
	}
}
