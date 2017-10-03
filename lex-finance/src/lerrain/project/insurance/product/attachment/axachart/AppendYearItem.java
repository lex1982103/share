package lerrain.project.insurance.product.attachment.axachart;

import java.io.Serializable;

import lerrain.tool.formula.Formula;

/**
 * @author yang
 * @date 2014-7-17 下午6:32:48
 */
public class AppendYearItem implements Serializable
{

	private static final long serialVersionUID = 1L;

	String title;
	String desc;
	Formula value;
	String mode;
	String code;

	public AppendYearItem(String title, String desc, String mode, Formula value)
	{
		this.title = title;
		this.desc = desc;
		this.mode = mode;
		this.value = value;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	public String getTitle()
	{
		return title;
	}

	public String getDesc()
	{
		return desc;
	}

	public String getMode()
	{
		return mode;
	}

	public Object getValue()
	{
		return value;
	}

	public Formula getFormula()
	{
		return (Formula) value;
	}
}
