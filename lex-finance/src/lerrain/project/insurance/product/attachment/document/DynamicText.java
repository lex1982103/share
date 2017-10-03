package lerrain.project.insurance.product.attachment.document;

import java.util.HashMap;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;

public class DynamicText
{
	String text;
	Formula textFormula;
	
	Map style;
	Formula condition;
	
	boolean bold = false;
	
	public DynamicText()
	{
	}
	
//	public DynamicText(String text, String styleStr)
//	{
//		this.text = text;
//		setStyle(styleStr);
//	}
//	
//	public DynamicText(Formula text, String styleStr)
//	{
//		this.textFormula = text;
//		setStyle(styleStr);
//	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void setText(Formula textFormula)
	{
		this.textFormula = textFormula;
	}
	
	public void setStyle(String styleStr)
	{
		if (styleStr != null && !"".equals(styleStr.trim()))
		{
			style = new HashMap();
			
			String[] css = styleStr.split(";");
			for (int i = 0; i < css.length; i++)
			{
				String[] ss = css[i].split(":");
				if (ss.length >= 2)
					style.put(ss[0].trim(), ss[1].trim());
				else
					style.put(ss[0].trim(), "");
			}
		}
	}
	
	public String getText(Factors f)
	{
		if (textFormula != null)
			return Value.stringOf(textFormula, f);
		
		return text;
	}
	
	public String getStyle(String name)
	{
		return style == null ? null : (String)style.get(name);
	}
	
	public Map getStyle()
	{
		return style;
	}

	public Formula getCondition()
	{
		return condition;
	}

	public void setCondition(Formula condition)
	{
		this.condition = condition;
	}

	public boolean isBold()
	{
		return bold;
	}

	public void setBold(boolean bold)
	{
		this.bold = bold;
	}
}
