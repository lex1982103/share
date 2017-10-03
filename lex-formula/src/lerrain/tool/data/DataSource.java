package lerrain.tool.data;

import java.io.Serializable;

import lerrain.tool.formula.Factors;

public interface DataSource extends Serializable
{
	public DataRecord search(Factors param, String groupName);
	
	public String[] getGroupsName();
}
