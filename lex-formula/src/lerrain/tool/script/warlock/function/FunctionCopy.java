package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionCopy implements Function
{
    @Override
    public Object run(Object[] objects, Factors factors)
    {
        return copy(objects[0], objects.length == 0 || Value.booleanOf(objects[1]));
    }

    private static Object copy(Object v, boolean deep)
    {
        if (v instanceof List)
        {
            List r = new ArrayList();
            for (Object o : (List)v)
                r.add(deep ? copy(o, deep) : o);

            return r;
        }
        else if (v instanceof Map)
        {
            Map r = new HashMap();
            for (Map.Entry<?, ?> e : ((Map<?, ?>)v).entrySet())
                r.put(e.getKey(), deep ? copy(e.getValue(), deep) : e.getValue());

            return r;
        }
        else
        {
            return v;
        }
    }
}
