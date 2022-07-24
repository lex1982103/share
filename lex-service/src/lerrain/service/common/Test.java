package lerrain.service.common;

import com.fasterxml.jackson.core.type.TypeReference;
import lerrain.tool.Common;
import lerrain.tool.Disk;

import javax.net.ssl.*;
import java.io.*;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lerrain on 2017/8/3.
 */
public class Test
{
    static Random ran = new Random();

    static ExecutorService es = Executors.newCachedThreadPool();

    public static void main(String[] arg) throws Exception
    {
        Map map = new ConcurrentHashMap();

        for (int ppp =0 ;ppp< 10; ++ppp)
        {
            final int pppp = ppp;
            List<Callable<Integer>> list = new ArrayList<>();

            for (int i = 0; i < 10; ++i)
            {
                final int j = i;
                list.add(() -> {
                    int vv = 0;
                    for (int z = 0; z < 100000; ++z)
                    {
                        long t1 = System.currentTimeMillis();
                        Object v = map.computeIfAbsent(ran.nextInt(100000), k ->  new byte[10000]);

                        long t2 = System.currentTimeMillis();
                        if (t2 - t1 > 50)
                        {
                            System.out.println(j + " time out " + (t2 - t1) + " " + map.size());
                        }
                        else if (t2 - t1 < 10)
                        {
                            ++vv;
                        }
                    }
                    return vv;
                });
            }

            try
            {
                int vv = 0;
                for (Future<Integer> f : es.invokeAll(list))
                {
                    vv += f.get();
                }

                System.out.println(vv);
            }
            catch (Exception e)
            {
                Log.alert(e);
            }

            for (int i=0;i<30000;++i)
                map.remove(i);

            Thread.sleep(1000);
        }
    }

    public static void main2(String[] arg) throws Exception
    {
        Map m = new HashMap();
        m.put("dict", "http://localhost:8888,http://localhost:7773");

        final ServiceMgr sm = new ServiceMgr();
        sm.map.put("dict", new Service(sm, "dict"));
        sm.reset(m);

        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
//                    sm.req("dict", "test.json", new JSONObject());
                }
                catch (Exception e)
                {
                }
            }
        };

        for (int i=0;i<100;i++)
        {
            r.run();
            Thread.sleep(10);

            if (i == 10)
            {
                ((SimpleConnector)sm.map.get("dict").clients[0].client).addr = "http://localhost:7792/";
            }
        }
    }
}
