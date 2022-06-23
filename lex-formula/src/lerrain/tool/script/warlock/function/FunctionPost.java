package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FunctionPost implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		String url = v[0].toString();
		String req = null;
		int timeOut = 10000;

		if (v.length > 1)
			req = v[1].toString();

		if (v.length > 2)
			timeOut = Value.intOf(v[2], 10000);

		return request(url, req, timeOut);
	}

	private static String request(String urlstr, String req, int timeout)
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
			if (info != null)
				conn.setRequestProperty("Content-Length", String.valueOf(info.length));

			try (OutputStream out = conn.getOutputStream())
			{
				if (info != null)
					out.write(info);
				out.flush();
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

			res = baos.toString();
		}
		catch (Exception e)
		{
			throw Script.EXC != null ? Script.EXC : new ScriptRuntimeException(e);
		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}

		return res;
	}
}
