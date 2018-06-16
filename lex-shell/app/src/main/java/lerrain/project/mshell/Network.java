package lerrain.project.mshell;

import android.webkit.CookieManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Network
{
//	public static String ADDRESS = "http://58.247.38.198:8089/sirius/";
//	public static String ADDRESS = "http://192.168.0.2:8089/phone/";
	public static String ADDRESS = "http://www.lerrain.com:7701";
//	public static String ADDRESS = "http://218.244.143.11:8080/solar-phone/";
	
	public static String urlOf(String url)
	{
		if (url.startsWith("file://"))
			return url;
		
		return Network.ADDRESS + url;
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
		InputStream in = null;
		OutputStream out = null;
		try
		{
			URL url = new URL(urlstr);
			conn = (HttpURLConnection)url.openConnection();
			in = conn.getInputStream();
			out = new FileOutputStream(dstFile);

			byte[] b = new byte[2048];
			int c;
			while ((c = in.read(b)) >= 0)
			{
				out.write(b, 0, c);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
	        	if (in != null)
	        		in.close();
			}
			catch (IOException e)
			{
			}
        	
			try
			{
	        	if (out != null)
	        		out.close();
			}
			catch (IOException e)
			{
			}

			if (conn != null)
				conn.disconnect();
		}
		
		return true;
	}
	
	public static String downloadStr(String uri)
	{
		String urlstr = urlOf(uri);
		
//		CookieManager cookieManager = CookieManager.getInstance();
//		String cookie = cookieManager.getCookie(Network.SERVICE_ADDRESS);
//		cookieManager.setCookie(Network.SERVICE_ADDRESS, value);
		
		HttpURLConnection conn = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try
		{
			URL url = new URL(urlstr);
			conn = (HttpURLConnection)url.openConnection();
//			conn.addRequestProperty("Cookie", cookie);
			in = conn.getInputStream();
			
			String cookie = conn.getHeaderField("Set-Cookie");
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.setCookie(Network.ADDRESS, cookie);
			
			out = new ByteArrayOutputStream();

			byte[] b = new byte[2048];
			int c;
			while ((c = in.read(b)) >= 0)
			{
				out.write(b, 0, c);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
		finally
		{
			try
			{
	        	if (in != null)
	        		in.close();
			}
			catch (IOException e)
			{
			}
        	
			try
			{
	        	if (out != null)
	        		out.close();
			}
			catch (IOException e)
			{
			}

			if (conn != null)
				conn.disconnect();
		}
		
//		System.out.println("MSG: " + out.toString());
		
		return out.toString();
	}
}
