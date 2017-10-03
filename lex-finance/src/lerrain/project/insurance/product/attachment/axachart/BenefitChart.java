package lerrain.project.insurance.product.attachment.axachart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BenefitChart implements Serializable
{
	private static final long serialVersionUID = 1L;

	AppendAxis axis = null;
	String code;

	Map additional;

	List yearList = new ArrayList();// 低中高挡时有多条

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	public void addYearData(AppendYearData yearData)
	{
		yearList.add(yearData);
	}

	public boolean hasyearData()
	{
		return !yearList.isEmpty();
	}

	public AppendYearData getYearData(int index)
	{
		return (AppendYearData) yearList.get(index);
	}

	public int getYearCount()
	{
		return yearList.size();
	}

	public void setAxis(AppendAxis axis)
	{
		this.axis = axis;
	}

	public AppendAxis getAxis()
	{
		return this.axis;
	}

	public void setAdditional(String name, Object value)
	{
		if (additional == null)
			additional = new HashMap();

		additional.put(name, value);
	}

	public Object getAdditional(String name)
	{
		if (additional == null)
			return null;

		return additional.get(name);
	}

	public Map getAdditional()
	{
		return additional;
	}
}
