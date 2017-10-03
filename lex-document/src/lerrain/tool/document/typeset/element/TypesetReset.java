package lerrain.tool.document.typeset.element;

import lerrain.tool.document.element.DocumentReset;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetNumber;
import lerrain.tool.document.typeset.TypesetParameters;


public class TypesetReset extends TypesetElement
{
	boolean newPage = false;
	
	TypesetNumber skip;
	
	public TypesetReset()
	{
	}

	public TypesetReset(TypesetNumber skip)
	{
		this.skip = skip;
	}
	
	public TypesetReset(boolean newPage)
	{
		this.newPage = newPage;
	}
	
	public TypesetReset(TypesetNumber skip, boolean newPage)
	{
		this.skip = skip;
		this.newPage = newPage;
	}
	
	public LexElement build(TypesetParameters tvs)
	{
		int datum = tvs.getY();
		
		if (newPage)
		{
			tvs.setDatum(datum + (skip == null ? 0 : skip.value(tvs)));
			tvs.setY(tvs.getDatum());
			
			DocumentReset reset = new DocumentReset(true);
			reset.setLocation(0, tvs.getDatum());
			
			return reset;
		}
		else
		{
			tvs.setDatum(datum + (skip == null ? 0 : skip.value(tvs)));
			tvs.setY(tvs.getDatum());
			
			return null;
		}
	}
}
