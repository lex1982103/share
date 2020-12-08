package lerrain.tool.script;

import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Words;

public interface CompileListener
{
    public static int FUNCTION = 1;

    public static int CONST = 2;
    public static int VARIABLE = 3;

    public void compile(int type, Code code);
}
