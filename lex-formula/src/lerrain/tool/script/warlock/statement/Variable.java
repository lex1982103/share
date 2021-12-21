package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.FormulaAutoRun;
import lerrain.tool.formula.VariableFactors;
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
		
		return val(factors.get(varName), factors);
	}

    /**
     * PointKey的计算，比如AAA.BB返回值如果是FormulaAutoRun或者LoadOnUse，只改这里将无法自动计算，其他地方也要修改
     * 如果直接一个autorun用let写入，实例化的时候也有问题
     * 自定义的Array和Function运算后也有可能返回autorun，这情况太罕见，就不做处理了
     */
	public static Object val(Object v, Factors factors)
    {
        if (v instanceof FormulaAutoRun)
            v = ((FormulaAutoRun) v).run(factors);

        return v;
    }

//	@Override
//	public boolean isFixed(int mode)
//	{
//		return super.isFixed(mode) && (mode & Optimized.VARIABLE) > 0;
//	}

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
}
