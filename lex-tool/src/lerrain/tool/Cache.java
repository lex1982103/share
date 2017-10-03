package lerrain.tool;

import java.util.HashMap;
import java.util.Map;

public class Cache
{
    Map<Object, Object> cache = new HashMap<>();

    public void put(Object id, Object value)
    {
        synchronized (cache)
        {
            cache.put(id, value);
        }
    }

    public <T> T get(Object id)
    {
        synchronized (cache)
        {
            return (T)cache.get(id);
        }
    }
}
