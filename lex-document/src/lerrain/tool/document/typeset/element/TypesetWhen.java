package lerrain.tool.document.typeset.element;

import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetBuildException;
import lerrain.tool.document.typeset.TypesetParameters;
import lerrain.tool.document.typeset.TypesetUtil;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;

public class TypesetWhen extends TypesetPanel
{
	public TypesetWhen(Formula condition)
	{
		setCondition(condition);
	}

	/* (non-Javadoc)
	 * @see lerrain.tool.document.typeset.element.TypesetPanel#build(lerrain.tool.document.typeset.TypesetVarSet)
	 */
	public LexElement build(TypesetParameters tvs)
	{
		boolean pass = false;
		try
		{
			pass = Value.booleanOf(condition, tvs);
		}
		catch (Exception e)
		{
			if (TypesetUtil.getMode() == TypesetUtil.MODE_FAIL)
			{
				throw new TypesetBuildException("exception in condition's formula: " + condition, e);
			}
			else if (TypesetUtil.getMode() == TypesetUtil.MODE_ALWAYS)
			{
				pass = true;
				System.out.println("exception in condition's formula: " + condition + ", set it TRUE instead.");
			}
		}
			
		return pass ? super.build(tvs) : null;
	}
}
