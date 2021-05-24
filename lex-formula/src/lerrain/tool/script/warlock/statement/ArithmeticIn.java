package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.warlock.analyse.Words;

import java.util.List;
import java.util.Map;

public class ArithmeticIn extends Arithmetic2Elements
{
    public ArithmeticIn(Words ws, int i)
    {
        super(ws, i);
    }

    private boolean same(Object lo, Object ro)
    {
        if (lo == null)
            return ro == null;

        if (lo instanceof Number)
        {
            if (ro instanceof Number)
                return ((Number) lo).doubleValue() == ((Number) ro).doubleValue();
            else
                return false;
        }

        return lo.equals(ro);
    }

    public Object run(Factors factors)
    {
        Object lo = this.l.run(factors);
        Object ro = this.r.run(factors);

        if (ro instanceof Object[])
        {
            for (Object v : (Object[])ro)
                if (same(lo, v))
                    return true;
        }
        else if (ro instanceof List)
        {
            for (Object v : (List)ro)
                if (same(lo, v))
                    return true;
        }
        else if (ro instanceof Map)
        {
            for (Object v : ((Map<?, ?>) ro).keySet())
                if (same(lo, v))
                    return true;
        }
        else if (ro instanceof int[])
        {

        }
        else if (ro instanceof long[])
        {

        }
        else if (ro instanceof double[])
        {

        }
        else if (ro instanceof float[])
        {

        }
        else if (ro instanceof short[])
        {

        }
        else if (ro instanceof byte[])
        {

        }

        return false;
    }
}
