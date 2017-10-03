package lerrain.tool.document.typeset.element;

import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetParameters;
import lerrain.tool.formula.Formula;

public class TypesetScript extends TypesetElement
{
	Formula script;
	
	public TypesetScript(Formula script)
	{
		this.script = script;
	}
	
	public LexElement build(TypesetParameters factors)
	{
		script.run(factors);
		
		return null;
	}

}
