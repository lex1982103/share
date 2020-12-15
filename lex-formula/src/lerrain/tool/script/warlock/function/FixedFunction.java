package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Function;
import lerrain.tool.script.warlock.Fixed;

public abstract class FixedFunction implements Function, Fixed
{
    @Override
    public boolean isFixed()
    {
        return true;
    }
}
