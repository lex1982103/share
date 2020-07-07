package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.CompileListener;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.analyse.Words;

public class Variable extends Code implements Reference
{
	String varName;
	
	public Variable(Words ws)
	{
		super(ws);

		this.varName = ws.getWord(0);

		if (Script.compileListener != null)
			Script.compileListener.compile(CompileListener.VARIABLE, this);
	}

	public Object run(Factors factors)
	{
		//作废
		if ("timems".equals(varName))
			return Double.valueOf((double)System.currentTimeMillis());
		
		Object v = factors.get(varName);

		if (v instanceof LoadOnUse)
			v = ((LoadOnUse)v).load();

		return v;
	}
	
	public void let(Factors factors, Object value)
	{
		((Stack)factors).set(varName, value);
	}
	
	public String toString()
	{
		return varName;
	}

	public String toText(String space, boolean line)
	{
		return varName;
	}

	public interface LoadOnUse
	{
		public Object load();
	}
}
