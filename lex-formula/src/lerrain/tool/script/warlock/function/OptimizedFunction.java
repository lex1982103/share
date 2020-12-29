package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Function;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.Optimized;

public abstract class OptimizedFunction implements Function, Optimized
{
    @Override
    public boolean isFixed(Code code)
    {
        return true;
    }
}
