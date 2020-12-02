package lerrain.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lerrain.tool.Common;
import lerrain.tool.Disk;
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
        double x = 0 /0;
        System.out.println(x);

    }

    public static void main5(String[] arg) throws Exception
    {
//        System.out.println(1587640391384L - 1587640292360L);
//        System.out.println(1587640406798L - 1587640394763L);
        //1587640387296  1587640216865
        //1587640406798  1587640394763

        //1587640289374  313765 383968
        //289 313 383

        /*
        216 //SECURE 0
        216~289 *DEBUG 0~POST
        292 *DEBUG 0
        313 DEBUG 0~POST
        383 *DEBUG 0~POST
        387 //SECURE POST
        391 *DEBUG POST


        394 //SECURE 0
        406 //SECURE 0 POST


        RETRY 289~387 4req+3debug(216*289~313~383/3retry)
        PRIMARY 394~406 Nreq+1debug(292~391)

         转换json失败，三次都是解析失败，没有请求，不会进入secure序列，报文较小无太大问题。
         但导致挂起70秒，后台大量积累，70秒内的debug数据形成大型报文
         大型报文请求超时失败，重试3次，每次都会进入secure正式序列
         secure报文过大无法发送，重试时报文继续增大一直挂起，3次debug都进入，报文达到600m

正式队列发送失败不提示错误，转入重试队列，以下为重试错误

2020-04-23 19:12:07 <ERROR> lerrain.service.biz.SecureQueue.retry: 发送至secure服务失败 ->
lerrain.service.common.ServiceException: request: secure/action.json -- Read timed out
--
2020-04-23 19:12:42 <ERROR> lerrain.service.biz.SecureQueue.retry: 发送至secure服务失败 ->
lerrain.service.common.ServiceException: request: secure/action.json -- Read timed out
--
2020-04-24 19:14:43 <ERROR> lerrain.service.common.ServiceStat.retry: 发送至secure服务失败 ->
lerrain.service.common.ServiceException: request: secure/action.json -- Read timed out
--
2020-04-24 19:15:05 <ERROR> lerrain.service.common.ServiceStat.retry: 发送至secure服务失败 ->
lerrain.service.common.ServiceException: request: secure/action.json -- Read timed out
--
2020-04-24 19:16:54 <ERROR> lerrain.service.common.ServiceStat.retry: 发送至secure服务失败 ->
lerrain.service.common.ServiceException: request: secure/action.json -- Read timed out

         正式序列每次失败后丢给备用序列，正式序列报文大小尚可控
         第一波70秒debug报文3次失败后record放弃它，此时第二波70秒debug报文到位，并进入正式序列，正式序列也超时
         */


        String str = Disk.load(new File("x:/33.txt"), "utf-8");
        str = str.trim();
        System.out.println(str.length());
        JSONArray ooo = JSON.parseArray(str.trim());
        System.out.println(ooo.size());
        if (true)  return;

        //        JSONArray json = JSON.parseArray(str.trim());

//        String str = Disk.load(new File("x:/1.csv"), "utf-8");
//        StringBuffer sb = new StringBuffer();
        int last = 0;
        int d = 0;
        int t1 = 0, t2 = 0;
        long l1 = 0, l2 = 0;
        int p1 = str.indexOf("script/record.json");
        System.out.println(p1);
        for (int i=0;i<str.length();i++)
        {
            if (i > p1 && p1 > 0)
            {
                System.out.println("SPLIT");
                p1 = str.indexOf("script/record.json", i);
            }

            char c  =str.charAt(i);
            if (c == ':')
            {
//                if ("\"scriptId\"".equals(str.substring(i-10,i)))// && str.charAt(i+1) != '\"')
//                {
//                    String s2 = str.substring(last, i);
//                    int p1 = s2.lastIndexOf("\"time\"");
//                    String s4 = "";
//                    if (p1>=0)
//                        s4 = s2.substring(p1 + 7, p1 + 7 + 13);
//                    if (l1 == 0)
//                        l1 = Common.toLong(s4);
//                    l2 = Common.toLong(s4);
//
//                    String s1 = str.substring(i+2, i+50);
////                    if (s1.startsWith("service"))
////                        t1++;
////                    else if (s1.startsWith("request") || s1.startsWith("response"))
////                        t2++;
//
//                    System.out.println(s1 + ":" + s4 + " -> " + (i - last) / 1000);
//                    last = i;
//                    d++;
//                }


                if ("\"time\"".equals(str.substring(i-6,i)) && str.charAt(i+1) != '\"')
                {
                    String s1 = str.substring(i+1, i+14);
                    System.out.println(s1 + " - " + str.substring(i-20, i-8)  +" -> " + (i - last) / 1000);
                    last = i;
                    d++;
                }

            }
        }

        //158764039476
        //158764040679

        System.out.println(d);
        System.out.println(t1);
        System.out.println(t2);
        System.out.println((l2 - l1)/1000);

//            sb.append(str.charAt(i));
//
//        Disk.saveToDisk(new ByteArrayInputStream(sb.toString().getBytes("gbk")), new File("x:/3.txt"));

//        try
//        {
//            throw new RuntimeException("test");
//        }
//        catch (Exception e)
//        {
//            for (Object str : getExceptionStack(e))
//                System.out.println(str);
//        }
    }

    public static void main11(String[] arg) throws Exception
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String str = Disk.load(new File("x:/3.csv"), "utf-8");
        byte[] b = new byte[1];
        for (int x=0,i=0;i<str.length();i++)
        {
            if (str.charAt(i) ==',')
            {
                int m = Common.intOf(str.substring(x,i), 32);
                b[0]=(byte)m;
                baos.write(b);
                x=i+1;
            }
        }

        Disk.saveToDisk(new ByteArrayInputStream(baos.toByteArray()), new File("x:/33.txt"));

//        StringBuffer sb = new StringBuffer();
//
//        for (int i=0;i<str.length();i+=2)
//        sb.append(str.charAt(i));
//                Disk.saveToDisk(new ByteArrayInputStream(sb.toString().getBytes("gbk")), new File("x:/21.txt"));
    }

    public static void main2(String[] arg) throws Exception
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
                ((ServiceMgr.NetworkClient)sm.map.get("dict").clients[0].client).addr = "http://localhost:7792/";
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

    private static JSONArray getExceptionStack(Throwable e)
    {
        JSONArray list = new JSONArray();
        list.add(e.getMessage());

        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement traceElement : trace)
            list.add("\tat " + traceElement);

        for (Throwable se : e.getSuppressed())
            list.addAll(getExceptionStack(se));

        Throwable ourCause = e.getCause();
        if (ourCause != null)
            list.addAll(getExceptionStack(e.getCause()));

        return list;
    }
}
