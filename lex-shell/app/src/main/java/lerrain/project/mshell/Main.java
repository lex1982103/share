package lerrain.project.mshell;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.alibaba.fastjson.JSONObject;


public class Main extends Activity
{
	Layers layers;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Ui.dp = metrics.density;
		Ui.width = metrics.widthPixels;
		Ui.height = metrics.heightPixels;
		
		super.onCreate(savedInstanceState);
		
		layers = new Layers(this);

		final Layer base = createBaseLayer();
		layers.setBaseLayer(base);
		
		this.setContentView(layers);
	}
//
//	private void initHtml()
//	{
//		AssetManager am = this.getAssets();
//
//		try (InputStream is = am.open("phone.html/template.phone.html"))
//		{
//			template = Common.stringOf(is, "utf-8");
//		}
//		catch (Exception e)
//		{
//			Log.e("mshell", e.toString());
//		}
//
//		try
//		{
//			loadHtml(am, "phone.html");
//			Log.i("mshell", pages.keySet().toString());
//		}
//		catch (Exception e)
//		{
//			Log.e("mshell", e.toString());
//		}
//	}
//
//	private void loadHtml(AssetManager am, String path) throws IOException
//	{
//		String[] files = am.list(path);
//		if (files.length > 0)
//		{
//			for (String str : files)
//				loadHtml(am, path + "/" + str);
//		}
//		else
//		{
//			if (path.endsWith(".phone.html"))
//			{
//				try (InputStream is = am.open(path))
//				{
//					String phone.html = Common.stringOf(is, "utf-8");
//					pages.put(path.substring(5, path.length() - 5), phone.html);
//				}
//				catch (Exception e)
//				{
//					Log.e("mshell", e.toString());
//				}
//			}
//		}
//
//	}
	
	protected Layer createBaseLayer()
	{
		PageLayer layer = new PageLayer(this);
		layer.openUrl(Network.WEB + "home/login.html");
//		layer.openLocal("ocr/html/demo.html");

		return layer;
	}

	public void stat(final String action)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				JSONObject json = new JSONObject();
				json.put("userKey", layers.env.get("userKey"));
				json.put("action", action);

				Network.request("util/stat.json", json.toJSONString(), 1000);
			}
		}).start();
	}
}
