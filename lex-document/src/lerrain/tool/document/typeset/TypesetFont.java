package lerrain.tool.document.typeset;

import java.io.Serializable;

import lerrain.tool.formula.Formula;

public class TypesetFont implements Serializable
{
	private static final long serialVersionUID = 1L;

	Formula familyName;
	Formula fontSize;

	public Formula getFamilyName()
	{
		return familyName;
	}

	public void setFamilyName(Formula familyName)
	{
		this.familyName = familyName;
	}

	public Formula getFontSize()
	{
		return fontSize;
	}

	public void setFontSize(Formula fontSize)
	{
		this.fontSize = fontSize;
	}
}
