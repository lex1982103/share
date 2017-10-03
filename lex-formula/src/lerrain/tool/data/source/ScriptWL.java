package lerrain.tool.data.source;

import java.io.File;
import java.util.Map;

import lerrain.tool.data.DataParser;
import lerrain.tool.data.DataRecord;
import lerrain.tool.data.DataSource;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.script.Script;
import lerrain.tool.script.warlock.analyse.Text;

public class ScriptWL implements DataParser, DataSource
{
	private static final long serialVersionUID = 1L;
	
	public ScriptWL()
	{
	}

	public DataSource newSource(Map param)
	{
		String nameStr = (String)param.get("name");
		String scriptStr = (String)param.get("script");
		File file = (File)param.get("file");
		
		ScriptWL swl = null;
		
		if (scriptStr != null)
		{
			swl = new ScriptWL(nameStr.split(","), scriptStr);
		}
		else if (file != null && file.exists() && file.isFile())
		{
			swl = new ScriptWL(nameStr.split(","), Text.read(file));
		}
		
		return swl;
	}
	
	Script script;
	
	String[] groupName;
	
	public ScriptWL(String[] groupName, String script)
	{
		this.script = (Script)FormulaUtil.formulaOf(script);
		this.groupName = groupName;
	}

	public DataRecord search(Factors factors, String groupName)
	{
		return null;
	}

	public String[] getGroupsName()
	{
		return groupName;
	}
}
