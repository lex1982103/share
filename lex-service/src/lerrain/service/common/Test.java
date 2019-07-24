package lerrain.service.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lerrain.tool.Common;
import lerrain.tool.Network;
import org.springframework.core.env.Environment;

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
    static int[] XX = new int[4];

    public static void main(String[] arg) throws Exception
    {
        Map m = new HashMap();
        m.put("dict", "http://localhost:8888,http://localhost:7773");

        final ServiceMgr sm = new ServiceMgr();
        sm.map.put("dict", sm.new Servers("dict"));
        sm.reset(m);

        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    sm.req("dict", "test.json", new JSONObject());
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
                ((ServiceMgr.NetworkClient)sm.map.get("dict").clients[0].client).url = "http://localhost:7792/";
            }
        }
//            new Thread(r).start();


//            System.out.println(nextId("order"));

//        ServiceMgr cm = new ServiceMgr();
//        ServiceClient client = cm.getClient("http://localhost:7773");
//
//        JSONObject p = new JSONObject();
//        p.put("name", "marriage");
//        p.put("company", "dawn");
//
//        Object val = client.req("dict/view.json", p);
//        Log.info(val.toString());

//        JSONObject ee = new JSONObject();
//        JSONObject dd = new JSONObject();
//        JSONArray aa = new JSONArray();
//        for (int i=0;i<10;i++) {
//            JSONObject ll = new JSONObject();
//            ll.put("index", i+1);
//            ll.put("name", "平"+i);
//            ll.put("gender", "男");
//            ll.put("certNo", "32030419890709001X");
//            ll.put("plan", "测两万计划A");
//            ll.put("price", "200.00");
//            aa.add(ll);
//        }
//        dd.put("detail", aa);
//        ee.put("content", dd);
//        ee.put("outputType", "pdf");
//        ee.put("templateCode", "zhongan3");
//
//        System.out.println(request("http://www.lerrain.com:7511/print.stream", ee.toJSONString()));
    }

    static Map<String, long[]> map = new HashMap<>();
    static Random ran = new Random();

    public static synchronized Long nextId(String code)
    {
        long[] v = map.get(code);

        if (v == null)
        {
            v = reqId(code, new long[3], 5000, 10000, 30000);
            map.put(code, v);
        }
        else
        {
            if (v[2] <= 1)
                v[0]++;
            else
                v[0] += ran.nextInt((int)v[2]);

            if (v[0] > v[1])
                reqId(code, v, 5000, 10000, 30000);
        }

        return v[0];
    }

    static int id = 312313200;

    private static long[] reqId(String code, long[] r, int... sleep)
    {
        try
        {
            String[] res = (id + ",100000,10").split(",");

            r[0] = Long.parseLong(res[0]);
            r[1] = Long.parseLong(res[1]);
            r[2] = Long.parseLong(res[2]);

            if (r[2] > 1)
                r[0] += ran.nextInt((int)r[2]);

            id += r[1];

            return r;
        }
        catch (Exception e3)
        {
            if (sleep != null && sleep.length > 0)
            {
                try
                {
                    Thread.sleep(sleep[0]);
                }
                catch (InterruptedException i3)
                {
                }

                int[] ns = new int[sleep.length - 1];
                for (int i = 0; i < ns.length; i++)
                    ns[i] = sleep[i + 1];

                return reqId(code, r, ns);
            }
        }

        throw new RuntimeException("获取id失败，tools服务异常");
    }


    public static String request(String urlstr)
    {
        return request(urlstr, null, 5000, "GET");
    }

    public static String request(String urlstr, String req)
    {
        return request(urlstr, req, 5000, req == null ? "GET" : "POST");
    }

    public static String request(String urlstr, String req, int timeout)
    {
        return request(urlstr, req, timeout, "POST");
    }

    public static String request(String urlstr, String req, int timeout, String method)
    {
        String res = null;

        HttpURLConnection conn = null;
        try
        {
            URL url = new URL(urlstr);
            byte[] info = req == null ? null : req.getBytes("UTF-8");

            conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(timeout);
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            if (conn instanceof HttpsURLConnection)
            {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                TrustManager[] tm = {new MyX509TrustManager()};
                sslContext.init(null, tm, new java.security.SecureRandom());;
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                ((HttpsURLConnection) conn).setSSLSocketFactory(ssf);
            }

            if (info != null)
            {
                conn.setRequestProperty("Content-Length", String.valueOf(info.length));

                try (OutputStream out = conn.getOutputStream())
                {
                    out.write(info);
                    out.flush();
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (InputStream in = conn.getInputStream())
            {
                byte[] b = new byte[1024];
                int c = 0;
                while ((c = in.read(b)) >= 0)
                {
                    baos.write(b, 0, c);
                }
            }
            baos.close();

            res = baos.toString("utf-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println(String.format("request: %s<%s> - %s", urlstr, req, e.getMessage()));
        }
        finally
        {
            if (conn != null)
                conn.disconnect();
        }

        return res;
    }

    public static String request(String urlstr, byte[] info, int timeout)
    {
        String res = null;

        HttpURLConnection conn = null;
        try
        {
            URL url = new URL(urlstr);

            conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(timeout);
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            if (conn instanceof HttpsURLConnection)
            {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                TrustManager[] tm = {new MyX509TrustManager()};
                sslContext.init(null, tm, new java.security.SecureRandom());;
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                ((HttpsURLConnection) conn).setSSLSocketFactory(ssf);
            }

            if (info != null)
            {
                conn.setRequestProperty("Content-Length", String.valueOf(info.length));

                try (OutputStream out = conn.getOutputStream())
                {
                    out.write(info);
                    out.flush();
                }
            }

            try (InputStream in = conn.getInputStream())
            {
                res = Common.stringOf(in, "utf-8");
            }
        }
        catch (Exception e)
        {
            System.out.println(String.format("request: %s<%s> - %s", urlstr, info == null ? null : new String(info), e.getMessage()));
        }
        finally
        {
            if (conn != null)
                conn.disconnect();
        }

        return res;
    }

    public static String request(String urlstr, int timeout)
    {
        return request(urlstr, (byte[])null, timeout);
    }

    public static boolean download(String urlstr, File dstFile)
    {
        File dir = dstFile.getParentFile();
        if (!dir.exists())
        {
            if (!dir.mkdirs())
                return false;
        }

        if (!dir.isDirectory())
            return false;

        HttpURLConnection conn = null;

        try
        {
            URL url = new URL(urlstr);
            conn = (HttpURLConnection)url.openConnection();

            try (InputStream in = conn.getInputStream(); OutputStream out = new FileOutputStream(dstFile))
            {
                byte[] b = new byte[2048];
                int c;
                while ((c = in.read(b)) >= 0)
                    out.write(b, 0, c);
            }

        }
        catch (Exception e)
        {
            System.out.println("download: " + urlstr + " - " + e.getMessage());
            return false;
        }
        finally
        {
            if (conn != null)
                conn.disconnect();
        }

        return true;
    }

    private static class MyX509TrustManager implements X509TrustManager
    {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)  throws CertificateException
        {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }
    }

}
