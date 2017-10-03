package lerrain.tool.script.warlock.statement;

import java.util.LinkedHashMap;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;
import lerrain.tool.script.SyntaxException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Wrap;
import lerrain.tool.script.warlock.analyse.Syntax;
import lerrain.tool.script.warlock.analyse.Words;

public class StatementParagraph implements Code
{
	Code c;
	
	public StatementParagraph(Words ws)
	{
		int i = 0;
		int r = Syntax.findRightBrace(ws, i + 1);
		
		if (i != 0 || r != ws.size() - 1)
			throw new SyntaxException("表达式内部脚本作为一个值，与周围的计算无法匹配");
		
		c = new Script(ws.cut(i + 1, r));
	}

	public Object run(Factors factors)
	{
		return c.run(new Stack(factors));
	}

	public String toText(String space)
	{
		StringBuffer buf = new StringBuffer("{\n");
		buf.append(c.toText(space + "    ") + "\n");
		buf.append(space + "}");
		
		return buf.toString();
	}
}
