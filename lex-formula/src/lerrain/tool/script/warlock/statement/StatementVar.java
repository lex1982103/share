package lerrain.tool.script.warlock.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

/**
 * 这个类将来要改成个Arithmetic
 */
public class StatementVar extends Code
{
	Code r;
	
	List names = new ArrayList();
	
	public StatementVar(Words ws)
	{
		super(ws);

		names.add(ws.getWord(1));

		if (ws.size() > 2)
		{
			r = new StatementExpression(ws.cut(1));
			
			for (int i = 2; i < ws.size(); i++)
			{
				if (ws.getType(i) == Words.COMMA)
				{
					names.add(ws.getWord(i + 1));
					i++;
				}
				else if (Syntax.isLeftBrace(ws, i))
				{
					i = Syntax.findRightBrace(ws, i + 1);
				}
			}
		}
	}

	public Object run(Factors factors)
	{
		super.debug(factors);

		for (int i = 0; i < names.size(); i++)
			((Stack)factors).declare((String)names.get(i));
		
		if (r == null)
			return null;
		
		return r.run(factors);
	}

	@Override
	public boolean isFixed()
	{
		return r == null || r.isFixed();
	}

	public String toText(String space, boolean line)
	{
		return "VAR " + (r == null ? names.toString() : r.toText("", line));
	}

	@Override
	public Code[] getChildren()
	{
		return new Code[] {r};
	}
}
