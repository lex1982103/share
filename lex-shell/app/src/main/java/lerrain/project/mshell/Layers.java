package lerrain.project.mshell;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class Layers extends RelativeLayout implements View.OnTouchListener
{
	Scroller scroller;

	Layer layer;

	boolean dragging = false;
	float dragX;

	public Layers(Context context)
	{
		super(context);

		scroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());

		this.setOnTouchListener(this);
	}

	public void setBaseLayer(Layer layout)
	{
		this.addView(layout);
	}

	public void addLayout(Layer layer)
	{
		if (dragging)
			return;

		layer.setTranslationX(Ui.width);
		layer.setBackButton(true);

		this.addView(layer);

		this.layer = layer;
		playEnter();
	}

	public void back()
	{
		if (dragging)
			return;

		this.layer = (Layer)this.getChildAt(this.getChildCount() - 1);
		playOut();
	}

	private void playEnter()
	{
		scroller.startScroll(Ui.width, 0, -Ui.width, 0, 300);

		this.postInvalidate();
	}

	private void playOut()
	{
		scroller.startScroll(0, 0, Ui.width, 0, 300);

		this.postInvalidate();
	}

	@Override
	public void computeScroll()
	{
		if (layer != null)
		{
			if (scroller.computeScrollOffset())
			{
				this.postInvalidate();

				int x = scroller.getCurrX();
				layer.setTranslationX(x);
			} else if (scroller.isFinished())
			{
				int x = scroller.getCurrX();
				if (x > Ui.width / 2)
					this.removeView(layer);
				else
					layer.setTranslationX(0);
			}
		}

		super.computeScroll();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if (dragging)
			{
				layer.setTranslationX(event.getX() - dragX);
			}
		}
		else if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			dragging = true;
			dragX = event.getX();

			layer = (Layer)this.getChildAt(this.getChildCount() - 1);
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
			if (dragging)
			{
				dragging = false;
				layer.setTranslationX(0);
			}
		}

		return false;
	}
}
