package lerrain.tool.script;

import lerrain.tool.script.warlock.Code;

public interface CompileListener
{
//    public static final int FUNCTION = 1; //函数+参数
//    public static final int FUNCTION_INSTANT = 4; //函数体
//
//    public static final int CONST = 2;
//    public static final int VARIABLE = 3;
//
//    public static final int SIMPLIFY = 6;
//    public static final int EXPRESSION = 5;

    public static final int CHINESE = 9;

    public Code compile(int type, Code code);
}
