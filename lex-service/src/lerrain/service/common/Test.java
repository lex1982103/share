package lerrain.service.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lerrain.tool.Common;
import lerrain.tool.Network;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by lerrain on 2017/8/3.
 */
public class Test
{
    public static void main(String[] arg) throws Exception
    {
//        ServiceMgr cm = new ServiceMgr();
//        ServiceClient client = cm.getClient("http://localhost:7773");
//
//        JSONObject p = new JSONObject();
//        p.put("name", "marriage");
//        p.put("company", "dawn");
//
//        Object val = client.req("dict/view.json", p);
//        Log.info(val.toString());

        System.out.println(-3 % 2);

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
