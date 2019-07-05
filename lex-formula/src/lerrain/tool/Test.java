package lerrain.tool;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.formula.Function;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.statement.ArithmeticApprox;

public class Test
{
	public static void main(String[] s)
	{
		Script script = Script.scriptOf("try(100,0); print(try(c.z,0)); try{ var x = 1; var y = 5; for (var i = 0; i < 10; i++) { var c=null; c.x; y += x; print(y); }}  catch() { print(100); }  print (101);");

		script.run(new Stack());
	}
}
