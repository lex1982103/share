package lerrain.project.mshell;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class Layers extends RelativeLayout
{
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
			Layer lastLayer = (Layer) this.getChildAt(this.getChildCount() - 2);
			lastLayer.runJs("APP.callback(\"" + val + "\")");
		}

		layer = (Layer)this.getChildAt(this.getChildCount() - 1);
		layer.playOut();
	}
}
