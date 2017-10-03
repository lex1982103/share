package lerrain.tool.document.typeset;

import lerrain.tool.document.LexFontFamily;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class TypesetFontFamily extends LexFontFamily
{
	private static final long serialVersionUID = 1L;

	Formula pathFunc;
	
	public TypesetFontFamily(String name, String path) 
	{
		super(name, path);
	}

	public TypesetFontFamily(String name, Formula path) 
	{
		super(name, null);
		
		this.pathFunc = path;
	}
	
	public void build(Factors p)
	{
		super.setPath(Value.stringOf(pathFunc, p));
	}
}
