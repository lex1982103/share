package lerrain.service.common;

import com.alibaba.fastjson.JSONObject;
import lerrain.tool.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public class CacheService
{
    @Value("${info.code:null}")
    String serviceCode;

    @Autowired
    ServiceMgr serviceMgr;

    Map<String, Object> cache = new HashMap<>();

    public void put(final String id, final Object value)
    {
        synchronized (cache)
        {
            cache.put(id, value);
        }

        if (serviceCode != null && serviceMgr.hasServers("cache"))
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    JSONObject req = new JSONObject();
                    req.put("service", serviceCode);
                    req.put("key", id);
                    req.put("value", value);

                    serviceMgr.req("cache", "save.json", req);
                }
            }).start();
        }
    }

    public Object get(String id)
    {
        synchronized (cache)
        {
            if (cache.containsKey(id))
                return cache.get(id);
        }

        if (serviceCode != null && serviceMgr.hasServers("cache"))
        {
            JSONObject req = new JSONObject();
            req.put("service", serviceCode);
            req.put("key", id);

            Object value = serviceMgr.req("cache", "load.json", req);
            if (value != null)
            {
                synchronized (cache)
                {
                    cache.put(id, value);
                }
            }
        }

        return null;
    }
}
