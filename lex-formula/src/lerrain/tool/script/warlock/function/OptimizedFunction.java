package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Function;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Optimized;

public interface OptimizedFunction extends Function
{
    public default boolean isFixed(int mode, Code code)
    {
        return true;
    }
}
