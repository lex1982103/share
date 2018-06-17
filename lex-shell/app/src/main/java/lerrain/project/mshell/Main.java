package lerrain.project.mshell;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.InputStream;


public class Main extends Activity
{
	Layers layers;

	String template;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		try (InputStream is = this.getAssets().open("html/template.html"))
		{
			template = Common.stringOf(is, "utf-8");
		}
		catch (Exception e)
		{
			Log.e("mshell", e.toString());
		}

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
	
	protected Layer createBaseLayer()
	{
		PageLayer layer = new PageLayer(this);
		layer.openPage("home/login");

		return layer;
	}
}
