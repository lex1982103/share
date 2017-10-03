package lerrain.tool.formula;

/**
 * 关于私有参数表：
 * 1. 主要应用于多公式嵌套，可以使整体结构更清楚。
 * 2. 对于一个公式A+B，如果A和B均为一个公式对象，则可以在初始化他们的公示对象的时候传入各自的私有参数表。
 * 3. 需要注意的是，最终计算用的参数表优先级高于私有参数表，同名对象会覆盖。如果希望始终调用的是自己的参数，则
 *    需要在参数名前面加this，即修改C为this.C。
 * by 李新豪 2010-11-12 
 */

import lerrain.tool.script.Script;
import lerrain.tool.script.warlock.analyse.Words;

public class FormulaUtil
{
	private static FormulaEngine formulaEngine;
//	private static FormulaEngine formulaEngine = new FormulaAries();
	
	public static FormulaEngine getFormulaEngine() 
	{
		if (formulaEngine == null)
		{
			formulaEngine = new FormulaEngine()
			{
				public Formula formulaOf(String formula)
				{
					if (formula == null || "".equals(formula.trim()))
						return null;

					return new Script(Words.wordsOf(formula), true);
				}
			};

		}
		
		return formulaEngine;
	}

	public static void setFormulaEngine(FormulaEngine formulaEngine) 
	{
		FormulaUtil.formulaEngine = formulaEngine;
	}
	
	public static Formula formulaOf(String formulaText)
	{
		return getFormulaEngine().formulaOf(formulaText);
	}
}
