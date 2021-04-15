package lerrain.tool.script.warlock.statement;

import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.Arrays;
import java.util.List;

/**
 * 2元运算符
 */
public abstract class Arithmetic2Elements extends Code
{
    public Code l, r;

    public Arithmetic2Elements(Words ws, int i)
    {
        super(ws, i);

        l = Expression.expressionOf(ws.cut(0, i));
        r = Expression.expressionOf(ws.cut(i + 1));
    }

    @Override
    public boolean isFixed(int mode)
    {
        return l.isFixed(mode) && r.isFixed(mode);
    }

    @Override
    public Code[] getChildren()
    {
        return new Code[] {l, r};
    }

    @Override
    public void replaceChild(int i, Code code)
    {
        if (i == 0)
            l = code;
        else if (i == 1)
            r = code;
    }
}
