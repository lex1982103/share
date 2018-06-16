package lerrain.project.mshell;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;


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
	
	protected Layer createBaseLayer()
	{
		PageLayer layer = new PageLayer(this);
		//layer.openUrl("http://www.lerrain.com:7701/nci/apply/plan.html");
		layer.openLocal("home/home.html");

		return layer;
	}
}
