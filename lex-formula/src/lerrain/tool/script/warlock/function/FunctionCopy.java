package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionCopy extends OptimizedFunction
{
    @Override
    public Object run(Object[] objects, Factors factors)
    {
        return copy(objects[0], objects.length < 2 || Value.booleanOf(objects[1]));
    }

    private static Object copy(Object v, boolean deep)
    {
        if (v instanceof List)
        {
            List r = new ArrayList();
            for (Object o : (List) v)
                r.add(deep ? copy(o, deep) : o);

            return r;
        }
        else if (v instanceof Map)
        {
            Map r = new HashMap();
            for (Map.Entry<?, ?> e : ((Map<?, ?>) v).entrySet())
                r.put(e.getKey(), deep ? copy(e.getValue(), deep) : e.getValue());

            return r;
        }
        else if (v instanceof Object[])
        {
            Object[] x = (Object[]) v;
            Object[] r = new Object[x.length];
            for (int i = 0; i < x.length; i++)
                r[i] = x[i];

            return r;
        }
        else if (v instanceof Object[])
        {
            return ((Object[]) v).clone();
        }
        else if (v instanceof byte[])
        {
            return ((byte[]) v).clone();
        }
        else if (v instanceof char[])
        {
            return ((char[]) v).clone();
        }
        else if (v instanceof int[])
        {
            return ((int[]) v).clone();
        }
        else if (v instanceof float[])
        {
            return ((float[]) v).clone();
        }
        else if (v instanceof double[])
        {
            return ((double[]) v).clone();
        }
        else if (v instanceof long[])
        {
            return ((long[]) v).clone();
        }

        return v;
    }
}
