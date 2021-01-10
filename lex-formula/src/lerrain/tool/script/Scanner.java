package lerrain.tool.script;

import lerrain.tool.script.warlock.Code;

@Deprecated
public interface Scanner
{
    public static final int TRY = 100;
    public static final int TRY_CATCH = 101;
    public static final int TRY_THROW = 102;
    public static final int BREAK = 110;
    public static final int THROW = 120;
    public static final int THROW_FROM = 121;
    public static final int FUNCTION_BODY = 130;
    public static final int FUNCTION_PARAMETER = 131;
    public static final int IF = 140;
    public static final int IF_TRUE = 141;
    public static final int IF_FALSE = 142;
    public static final int EXPRESSION_LEFT = 150;
    public static final int EXPRESSION_RIGHT = 151;
    public static final int ARRAY_OBJECT = 160;
    public static final int ARRAY_INDEX = 161;
    public static final int FUNCTION = 170;
    public static final int SENTENCE = 180;
    public static final int BRACE_LEAD = 190;
    public static final int BRACE_BODY = 191;

    public void scan(Code code);
}
