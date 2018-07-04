package lerrain.project.mshell;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Network
{
	public static String ADDRESS = "http://www.lerrain.com:7666/app/";

	public static String urlOf(String url)
	{
		if (url.startsWith("file://"))
			return url;
		
		return Network.ADDRESS + url;
	}

	public static String request(String urlstr, String req, int timeout)
	{
		String res = null;

		HttpURLConnection conn = null;
		try
		{
			URL url = new URL(urlOf(urlstr));
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
			Log.e("mshell", String.format("request: %s - %s", urlstr, e.toString()));
		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}

		return res;
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
