package lerrain.tool.script.warlock;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;

public interface Code extends Formula
{
	public Object run(Factors factors);
	
	public String toText(String space);

	//public void printCode();
}
