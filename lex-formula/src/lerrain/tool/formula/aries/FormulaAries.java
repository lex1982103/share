package lerrain.tool.formula.aries;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.formula.FormulaEngine;
import lerrain.tool.formula.FormulaException;
import lerrain.tool.formula.Formula;

public class FormulaAries implements FormulaEngine, Serializable
{
	private static final long serialVersionUID = 1L;

	private static Map formulaCompiledMap	= new HashMap();
	
	public Formula formulaOf(String formula) throws FormulaException
	{
		Formula formulaCompiled = (Formula)formulaCompiledMap.get(formula);
		
		if (formulaCompiled == null)
		{
			formulaCompiled = new FormulaCompiler(formula).compile();
			formulaCompiledMap.put(formula, formulaCompiled);
		}
		
		return formulaCompiled;
	}
}
