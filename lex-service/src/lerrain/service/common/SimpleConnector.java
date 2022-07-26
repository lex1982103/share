package lerrain.service.common;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;

public class SimpleConnector implements ServiceClientConnector
{
    String name;
    String addr;

    static JsonFactory factory = new JsonFactory(Json.OM);

    static
    {
        factory.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        factory.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    public SimpleConnector(String name, String addr)
    {
        this.name = name;
        this.addr = addr.endsWith("/") ? addr.substring(0, addr.length() - 1) : addr;
    }

    public String toString()
    {
        return name + "@" + addr;
    }

    @Override
    public <T> Result<T> req(String link, Object param, int timeout, Class<T> clazz) throws Exception
    {
        HttpURLConnection conn = null;
        try
        {
            URL url = new URL(link.startsWith("/") ? addr + link : addr + "/" + link);

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

            int reqBytes = -1;
            if (param != null)
            {
                try (StatOutputStream out = new StatOutputStream(conn.getOutputStream()))
                {
                    Json.write(param, out);
                    out.flush();

                    reqBytes = out.size;
                }
            }

            try (StatInputStream in = new StatInputStream(conn.getInputStream()))
            {
                Result r = new Result();

                JsonParser jp = factory.createParser(in);
                while (!jp.isClosed())
                {
                    String field = jp.nextFieldName();
                    if ("content".equals(field))
                    {
                        jp.nextValue();
                        r.setContent(jp.readValueAs(clazz));
                    }
                    else if ("code".equals(field))
                        r.setCode(jp.nextIntValue(-9));
                    else if ("result".equals(field))
                        r.setResult(jp.nextTextValue());
                    else if ("reason".equals(field))
                        r.setReason(jp.nextTextValue());
                }

                r.reqBytes = reqBytes;
                r.resBytes = in.size;

                return r;
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

    private static class StatInputStream extends BufferedInputStream
    {
        int size;

        public StatInputStream(InputStream in)
        {
            super(in, 4096);
        }

        public int read(byte[] b, int off, int len) throws IOException
        {
            int p = super.read(b, off, len);
            if (p >= 0)
                size += len;

            return p;
        }
    }

    private static class StatOutputStream extends BufferedOutputStream
    {
        int size;

        public StatOutputStream(OutputStream out)
        {
            super(out, 4096);
        }

        public void write(byte[] b, int off, int len) throws IOException
        {
            super.write(b, off, len);

            size += len;
        }
    }
}
