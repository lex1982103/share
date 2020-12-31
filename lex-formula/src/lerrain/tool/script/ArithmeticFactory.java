package lerrain.tool.script;

import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Words;

public interface ArithmeticFactory
{
    public Code buildArithmetic(Words ws, int pos);

    public int getPriority();
}
