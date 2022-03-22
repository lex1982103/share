package lerrain.tool.script.warlock;

import lerrain.tool.formula.AutoConstant;
import lerrain.tool.formula.Factors;

public class AutoCodeConstant implements AutoConstant
{
    boolean computed = false;

    Code r;
    Factors f;

    Object v;

    public AutoCodeConstant(Code r, Factors f)
    {
        this.r = r;
        this.f = f;
    }

    @Override
    public synchronized Object run()
    {
        if (computed)
            return v;

        v = r.run(f);
        computed = true;

        return v;
    }
}