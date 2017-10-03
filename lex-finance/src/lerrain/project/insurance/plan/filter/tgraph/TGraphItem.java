package lerrain.project.insurance.plan.filter.tgraph;


public class TGraphItem
{
	String text;
	Object value;
	boolean hasValue = false;
	
	boolean bold = false;
	
	public TGraphItem(String text, Object value)
	{
		this.text = text;
		this.value = value;
		this.hasValue = true;
	}
	
	public TGraphItem(String text)
	{
		this.text = text;
	}
	
	public boolean hasValue()
	{
		return hasValue;
	}
	
	public boolean isSameAs(TGraphItem tgi)
	{
		return text != null && text.equals(tgi.getText());
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
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
