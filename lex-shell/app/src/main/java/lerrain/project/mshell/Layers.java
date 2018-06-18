package lerrain.project.mshell;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Layers extends RelativeLayout
{
	Map<String, String> env = new HashMap<>();

	Layer layer;

	public Layers(Context context)
	{
		super(context);
	}

	public void setBaseLayer(Layer layout)
	{
		this.addView(layout);
	}

	public void addLayout(Layer layer)
	{
		layer.setBackButton(true);

		this.layer = layer;
		this.addView(layer);

		layer.playEnter();
	}

	public void back(String val)
	{
		if (this.getChildCount() >= 2)
		{
			if (val != null)
				val = "\"" + val.replaceAll("\"", "\\\\\"") + "\"";
			Log.i("mshell", "callback: " + val);

			Layer lastLayer = (Layer) this.getChildAt(this.getChildCount() - 2);
			lastLayer.runJs("APP.callback(" + val + ")");
		}

		layer = (Layer)this.getChildAt(this.getChildCount() - 1);
		layer.playOut();
	}
}
