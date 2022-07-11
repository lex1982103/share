package lerrain.service.common;

import com.fasterxml.jackson.databind.JsonNode;

import javax.net.ssl.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SimpleConnector implements ServiceClientConnector
{
    String name;
    String addr;

    public SimpleConnector(String name, String addr)
    {
        this.name = name;
        this.addr = addr.endsWith("/") ? addr.substring(0, addr.length() - 1) : addr;
    }

    @Override
    public JsonNode req(String link, Object param, int timeout) throws Exception
    {
        String url = link.startsWith("/") ? addr + link : addr + "/" + link;
        return request(url, param, timeout);
    }

    public String toString()
    {
        return name + "@" + addr;
    }

    public JsonNode request(String urlstr, Object param, int timeout) throws Exception
    {
        HttpURLConnection conn = null;
        try
        {
            URL url = new URL(urlstr);

            conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(timeout);
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

            if (param != null)
            {
                try (OutputStream out = conn.getOutputStream())
                {
                    Json.write(param, out);
                    out.flush();
                }
            }

            try (InputStream in = conn.getInputStream())
            {
                return Json.OM.readTree(in);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (conn != null)
                conn.disconnect();
        }
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
