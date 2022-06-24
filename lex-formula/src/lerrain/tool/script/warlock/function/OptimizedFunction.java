package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Function;
import lerrain.tool.script.warlock.Code;

public interface OptimizedFunction extends Function
{
    public default boolean isFixed(int mode, Code code)
    {
        return true;
    }
}
