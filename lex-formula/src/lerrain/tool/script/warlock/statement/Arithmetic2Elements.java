package lerrain.tool.script.warlock.statement;

import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

/**
 * 2元运算符
 */
public abstract class Arithmetic2Elements extends Code
{
    protected Code l, r;

    public Arithmetic2Elements(Words ws, int i)
    {
        super(ws, i);

        l = Expression.expressionOf(ws.cut(0, i));
        r = Expression.expressionOf(ws.cut(i + 1));
    }

    @Override
    public boolean isFixed()
    {
        return l.isFixed() && r.isFixed();
    }
}
