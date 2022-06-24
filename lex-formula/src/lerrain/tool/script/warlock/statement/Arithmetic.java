package lerrain.tool.script.warlock.statement;

import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Words;

public abstract class Arithmetic extends Code
{
    public Arithmetic(Words ws, int i)
    {
        super(ws, i);
    }

    public Arithmetic(Words ws)
    {
        super(ws);
    }
}
