package lerrain.tool.data.source;

import java.util.Map;

import lerrain.tool.data.DataNotFoundException;
import lerrain.tool.data.DataParser;
import lerrain.tool.data.DataRecord;
import lerrain.tool.data.DataSource;
import lerrain.tool.formula.Factors;

public class XmlDS implements DataParser, DataSource
{
	private static final long serialVersionUID = 1L;

	public XmlDS(Map param)
	{
	}
	
	public DataRecord search(Factors param, String groupName) throws DataNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getGroupsName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public DataSource newSource(Map param)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
