package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.VariableFactors;
import lerrain.tool.script.CompileListener;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Optimized;
import lerrain.tool.script.warlock.Reference;
import lerrain.tool.script.warlock.analyse.Words;

public class Variable extends Code implements Reference
{
	String varName;
	
	public Variable(Words ws)
	{
		super(ws);

		this.varName = ws.getWord(0);

//		if (Script.compileListener != null)
//			Script.compileListener.compile(CompileListener.VARIABLE, this);
	}

	public String getVarName()
	{
		return varName;
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

	@Override
	public boolean isFixed(int mode)
	{
		return (mode & Optimized.VARIABLE) > 0;
	}

	public void let(Factors factors, Object value)
	{
		((VariableFactors)factors).set(varName, value);
	}
	
	public String toString()
	{
		return varName;
	}

	public String toText(String space, boolean line)
	{
		return varName;
	}

	/**
	 * 这个只适合门户的常量使用，每次拿值的时候实例化
	 * 如果临时生成的值是这种类型，就难以操作，不仅仅script的main部分返回的时候要实例化，express的每一步都要判断太麻烦
	 */
	@Deprecated
	public interface LoadOnUse
	{
		public Object load();
	}
}
