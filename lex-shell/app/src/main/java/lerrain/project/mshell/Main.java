package lerrain.project.mshell;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


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
//		try (InputStream is = am.open("html/template.html"))
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
//			loadHtml(am, "html");
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
//			if (path.endsWith(".html"))
//			{
//				try (InputStream is = am.open(path))
//				{
//					String html = Common.stringOf(is, "utf-8");
//					pages.put(path.substring(5, path.length() - 5), html);
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
		layer.openLocal("home/login.html");

		return layer;
	}
}
