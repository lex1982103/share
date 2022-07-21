package lerrain.service.common;

import com.fasterxml.jackson.core.type.TypeReference;
import lerrain.tool.Common;
import lerrain.tool.Disk;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lerrain on 2017/8/3.
 */
public class Test
{
    public static void main(String[] arg) throws Exception
    {
        ConcurrentHashMap map = new ConcurrentHashMap();

        Thread th = new Thread(() -> {
            map.computeIfAbsent("AAA", k -> {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println(2);
                return 2;
            });
        });
        th.start();

//        map.computeIfAbsent("AAA", k -> {
//            try
//            {
//                Thread.sleep(1000);
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//            System.out.println(1);
//            return 1;
//        });


        Thread.sleep(100);

        System.out.println(map.get("AAA"));

//        long tm = (1657967734996L - Common.getDate("2020-01-01").getTime()) / 600000;
//
//        int minute = (int)(tm % 60);
//        int hour = (int)(tm / 60) % 24;
//
//        System.out.println(hour);
//        System.out.println(minute);

//        Object v = Json.OM.readValue("{result:'success', data:100}", new TypeReference<Result<Short>>(){});
//        v = v;
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
