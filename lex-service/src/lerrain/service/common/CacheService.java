package lerrain.service.common;

import lerrain.tool.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheService
{
    public static long TIME_OUT = 3600000L * 24;

    @Value("${sys.code:null}")
    String serviceCode;

    @Autowired
    ServiceMgr serviceMgr;

    protected Map<String, TimeValue> cache = new ConcurrentHashMap<>();

    protected Translator tran;

    ExecutorService es = Executors.newCachedThreadPool();

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
                return Json.writeNull(val);
            }

            @Override
            public Object toObject(String str)
            {
                return Json.parseNull(str, clazz);
            }
        });
    }

    public void setDefaultTranslator()
    {
        this.setTranslator(new Translator()
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
        cache.put(id, new TimeValue(value, timeout));
    }

    public Object get(String id)
    {
        TimeValue tv = cache.get(id);

        if (tv == null)
            return null;

        Object r = tv.val.get();
        if (r == null) //弱引用已经释放了，如果缓存的值就是null，这里会有点问题，无法被实际缓存
        {
            cache.remove(id);
            return null;
        }

        tv.resetTime();
        return r;
    }

    public void store(String id, Object value)
    {
        store(id, value, TIME_OUT, true);
    }

    public void store(String id, Object value, boolean memory)
    {
        store(id, value, TIME_OUT, memory);
    }

    public void store(final String id, final Object value, final long timeout, boolean memory)
    {
        if (memory)
            put(id, value, timeout);

        if (tran != null && serviceCode != null && serviceMgr.hasClients("cache"))
        {
            es.execute(() ->
            {
                try
                {
                    Map req = new HashMap();
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
            });
        }
    }

    public void storeSynch(final String id, final Object value, final long timeout, boolean memory)
    {
        if (memory)
            put(id, value, timeout);

        if (tran != null && serviceCode != null && serviceMgr.hasClients("cache"))
        {
            try
            {
                Map req = new HashMap();
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

    public void clear()
    {
        cache.clear();
    }

    public void remove(String key)
    {
        cache.remove(key);
    }

    public Object fetch(String id)
    {
        boolean local = cache.containsKey(id);

        if (local)
            return get(id);

        if (tran != null && serviceCode != null && serviceMgr.hasClients("cache"))
        {
            Map req = new HashMap();
            req.put("service", serviceCode);
            req.put("key", id);

            try
            {
                String res = serviceMgr.reqVal("cache", "load.json", req);
                if (res != null)
                {
                    Object value = tran != null ? tran.toObject(res) : res;
                    //由于cache那边没存timeout时间，拿默认的代替，这会造成timeout时间不准确
                    cache.put(id, new TimeValue(value, TIME_OUT));

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
        Iterator<Map.Entry<String, TimeValue>> iter = cache.entrySet().iterator();
        while (iter.hasNext())
        {
            if (iter.next().getValue().expire())
                iter.remove();
        }
    }

    public interface Translator
    {
        String toString(Object val);

        Object toObject(String str);
    }

    protected static class TimeValue
    {
        long begin;
        long timeout;

        SoftReference val;

        public TimeValue(Object value, long timeout)
        {
            this.val = new SoftReference(value);
            this.timeout = timeout;

            resetTime();
        }

        public Object getValue()
        {
            return val.get();
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
