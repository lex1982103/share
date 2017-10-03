package lerrain.tool.formula.aries.arithmetic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Formula;

public class Arithmetic implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	int priority;
	boolean funtion = true;
	List signs = new ArrayList();
	
	List paramList;
	
	protected void setPriority(int priority)
	{
		this.priority = priority;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	protected void addSign(String sign)
	{
		signs.add(sign);
	}
	
	public List getSigns()
	{
		return signs;
	}
	
	protected void setFuntion(boolean funtion)
	{
		this.funtion = funtion;
	}

	protected void setParameter(List paramList)
	{
		this.paramList = paramList;
	}
	
	protected Formula getParameter(int index)
	{
		return (Formula)paramList.get(index);
	}
	
	protected List getParameterList()
	{
		return paramList;
	}
	
	protected int getParameterCount()
	{
		return paramList.size();
	}

	public boolean isFuntion()
	{
		return funtion;
	}
}
