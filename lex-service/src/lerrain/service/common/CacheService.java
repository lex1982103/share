package lerrain.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lerrain.tool.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CacheService
{
    public static final long TIME_OUT = 3600000L * 24;

    public static final Translator TRANSLATOR_DEFAULT = new Translator()
    {
        @Override
        public String toString(Object val) throws RuntimeException
        {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos))
            {
                oos.writeObject(val);
                oos.flush();

                return Common.encodeBase64(baos.toByteArray());
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Object toObject(String str) throws RuntimeException
        {
            try
            {
                byte[] b = Common.decodeBase64ToByte(str);

                try (ByteArrayInputStream bais = new ByteArrayInputStream(b); ObjectInputStream oos = new ObjectInputStream(bais))
                {
                    return oos.readObject();
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    };

    @Value("${info.code:null}")
    String serviceCode;

    @Autowired
    ServiceMgr serviceMgr;

    Map<String, TimeValue> cache = new HashMap<>();

    Translator tran;

    /**
     * 将缓存的数据同步到cache服务（redis或其他），这样多实例可以共享数据，否则当前实例put的数据只能自己使用
     * 多服务即使根据id分发，create对象的机器根据nextId获取的到的对象id不一定就刚好适配到自己机器，这样再次访问根据id分发会拿不到数据，所以需要使用这个功能
     * 不根据id分发的机器，则更需要，必须打开
     * @param clazz 根据class使用json来转换
     */
    public void setTranslator(final Class clazz)
    {
        this.setTranslator(new Translator()
        {
            @Override
            public String toString(Object val)
            {
                return JSON.toJSONString(val);
            }

            @Override
            public Object toObject(String str)
            {
                return JSON.parseObject(str, clazz);
            }
        });
    }

    /**
     * 参考同名方法
     * 复杂的对象使用json自动转换会导致对象指针引用混乱，请使用自定义的转换器
     * @param tran 自定义转换器
     */
    public void setTranslator(Translator tran)
    {
        this.tran = tran;
    }

    public void put(String id, Object value)
    {
        put(id, value, TIME_OUT);
    }

    public void put(String id, Object value, long timeout)
    {
        synchronized (cache)
        {
            cache.put(id, new TimeValue(value, timeout));
        }
    }

    public Object get(String id)
    {
        TimeValue tv;

        synchronized (cache)
        {
            tv = cache.get(id);
        }

        if (tv == null)
            return null;

        tv.resetTime();
        return tv.value;
    }

    public void store(String id, Object value)
    {
        store(id, value, TIME_OUT);
    }

    public void store(final String id, final Object value, long timeout)
    {
        put(id, value, timeout);

        if (tran != null && serviceCode != null && serviceMgr.hasServers("cache"))
        {
            try
            {
                JSONObject req = new JSONObject();
                req.put("service", serviceCode);
                req.put("key", id);
                req.put("value", tran != null ? tran.toString(value) : value);
                req.put("timeout", timeout);

                serviceMgr.req("cache", "save.json", req);
            }
            catch (Exception e)
            {
                Log.alert(e);
            }
        }
    }

    public Object fetch(String id)
    {
        boolean local;

        synchronized (cache)
        {
            local = cache.containsKey(id);
        }

        if (local)
            return get(id);

        if (tran != null && serviceCode != null && serviceMgr.hasServers("cache"))
        {
            JSONObject req = new JSONObject();
            req.put("service", serviceCode);
            req.put("key", id);

            try
            {
                String res = (String)serviceMgr.reqVal("cache", "load.json", req);
                if (res != null)
                {
                    Object value = tran != null ? tran.toObject(res) : res;
                    synchronized (cache)
                    {
                        //由于cache那边没存timeout时间，拿默认的代替，这会造成timeout时间不准确
                        cache.put(id, new TimeValue(value, TIME_OUT));
                    }

                    return value;
                }
            }
            catch (Exception e)
            {
                Log.alert(e);
            }
        }

        return null;
    }

    public void free()
    {
        synchronized (cache)
        {
            Iterator<Map.Entry<String, TimeValue>> iter = cache.entrySet().iterator();
            while (iter.hasNext())
            {
                if (iter.next().getValue().expire())
                    iter.remove();
            }
        }
    }

    public static interface Translator
    {
        public String toString(Object val);

        public Object toObject(String str);
    }

    private static class TimeValue
    {
        long begin;
        long timeout;

        Object value;

        public TimeValue(Object value, long timeout)
        {
            this.value = value;
            this.timeout = timeout;

            resetTime();
        }

        public void resetTime()
        {
            begin = System.currentTimeMillis();
        }

        public boolean expire()
        {
            return System.currentTimeMillis() > begin + timeout;
        }
    }
}
