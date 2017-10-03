package lerrain.project.insurance.product.attachment.combo;

import java.io.Serializable;

import lerrain.tool.formula.Formula;

public class ComboItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	String code;
	
	Formula from, to;
	String varName;
	
	Formula value;
	Formula visible;
	
	public ComboItem(Formula from, Formula to, String varName, String code, Formula value)
	{
		this.from = from;
		this.to = to;
		this.varName = varName;
		this.value = value;
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public Formula getFrom()
	{
		return from;
	}

	public void setFrom(Formula from)
	{
		this.from = from;
	}

	public Formula getTo()
	{
		return to;
	}

	public void setTo(Formula to)
	{
		this.to = to;
	}

	public String getVarName()
	{
		return varName;
	}

	public void setVarName(String varName)
	{
		this.varName = varName;
	}

	public Formula getValue()
	{
		return value;
	}

	public void setValue(Formula value)
	{
		this.value = value;
	}

	public Formula getVisible()
	{
		return visible;
	}

	public void setVisible(Formula visible)
	{
		this.visible = visible;
	}
}
