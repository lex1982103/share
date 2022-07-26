package lerrain.service.common;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import lerrain.tool.Common;
import lerrain.tool.Disk;
import lerrain.tool.script.Script;
import lerrain.tool.script.SyntaxException;

import javax.net.ssl.*;
import java.io.*;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
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
        String s1 = "{nodes:[1,1], links:[1,2,3,4]}";
        GraphResult vr = Json.OM.readValue(s1, GraphResult.class);

        JsonParser jp2 = SimpleConnector.factory.createParser(s1);
        GraphResult gr = jp2.readValueAs(GraphResult.class);


        String str = "{result:'success', content:{nodes:[1,1], links:[1,2,3,4]}}";

        JsonParser jp = SimpleConnector.factory.createParser(str);

        Result r = new Result();

        while (!jp.isClosed())
        {
            String field = jp.nextFieldName();
            if ("content".equals(field))
            {
                jp.nextValue();
                r.setContent(jp.readValueAs(GraphResult.class));
            }
            else if ("code".equals(field))
                r.setCode(jp.nextIntValue(-9));
            else if ("result".equals(field))
                r.setResult(jp.nextTextValue());
            else if ("reason".equals(field))
                r.setReason(jp.nextTextValue());
        }

        r = r;
    }

    public static class GraphResult
    {
        long[] nodes;
        int[] links;

        public long[] getNodes()
        {
            return nodes;
        }

        public int[] getLinks()
        {
            return links;
        }

        public void setNodes(long[] nodes)
        {
            this.nodes = nodes;
        }

        public void setLinks(int[] links)
        {
            this.links = links;
        }

    }

    public static void main555(String[] arg) throws Exception
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
