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

/**
 * Created by lerrain on 2017/8/3.
 */
public class Test
{
    public static void main(String[] arg) throws Exception
    {
        Object v = Json.OM.readValue("{result:'success', data:100}", new TypeReference<Result<Short>>(){});
        v = v;
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
