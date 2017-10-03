package lerrain.project.insurance.product.attachment.table;

import java.io.Serializable;

import lerrain.tool.formula.Formula;

public class TableBlank implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String code;

	Formula rowspan;
	Formula colspan;
	Formula content;
	
	String align;
	String style;
	
	float widthScale;
	
	Formula condition;
	
	public String getStyle()
	{
		return style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}

	public TableBlank()
	{
	}
	
	public TableBlank(int rowspan, int colspan)
	{
		this.rowspan = new TableSpan(rowspan);
		this.colspan = new TableSpan(colspan);
	}

	public TableBlank(Formula rowspan, Formula colspan)
	{
		this.rowspan = rowspan;
		this.colspan = colspan;
	}

	public Formula getContent()
	{
		return content;
	}

	public Formula getRowspan()
	{
		return rowspan;
	}

	public Formula getColspan()
	{
		return colspan;
	}

	public void setContent(Formula content)
	{
		this.content = content;
	}

	public String getAlign()
	{
		return align;
	}

	public void setAlign(String align)
	{
		this.align = align;
	}

	public float getWidthScale()
	{
		return widthScale;
	}

	public void setWidthScale(float width)
	{
		this.widthScale = width;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public Formula getCondition()
	{
		return condition;
	}

	public void setCondition(Formula condition)
	{
		this.condition = condition;
	}
}
