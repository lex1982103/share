package lerrain.project.mshell;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Scroller;

public class TopButton extends ImageView implements OnClickListener
{
	int index;
	
	int lastImg;
	
	Layer layout;
	
	Scroller scroller;
	
	public TopButton(Layer layout, int index)
	{
		super(layout.window);
		
		this.layout = layout;
		this.index = index;

		this.setOnClickListener(this);
		
		scroller = new Scroller(getContext());
	}
	
	public void hideIcon()
	{
		scroller.startScroll(100, 0, -100, 0, 200);
		this.postInvalidate();
	}
	
	public void showIcon()
	{
		scroller.startScroll(0, 0, 100, 0, 200);
		this.postInvalidate();
	}
	
	@Override  
    public void computeScroll() 
	{
        if (scroller.computeScrollOffset())
        {  
        	this.postInvalidate();
        	
        	float x = scroller.getCurrX();
        	
        	this.setScaleX(x / 100);
        	this.setScaleY(x / 100);
        }
        else if (scroller.isFinished())
        {
        	if (scroller.getCurrX() < 50)
        	{
        		super.setImageDrawable(null);
        	}
        	else
        	{
	        	this.setScaleX(1);
	        	this.setScaleY(1);
        	}
        }
        
        super.computeScroll(); 
	}
	
	public void setImageResource(int res)
	{
		if (lastImg != res)
		{
			super.setImageResource(res);
			showIcon();
		}
	
		lastImg = res;
	}
	
	public void setImage(String str)
	{
		if (str == null || "".equals(str))
		{
			if (lastImg != 0)
			{
				lastImg = 0;
				hideIcon();
			}
		}
		else
		{
			if ("back".equals(str))
				this.setImageResource(R.drawable.arrow_left);
//			else if ("search".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.search);
//			else if ("left".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.left);
//			else if ("right".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.right);
//			else if ("add".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.add);
//			else if ("edit".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.edit);
//			else if ("star".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.star);
//			else if ("list".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.list);
//			else if ("progress".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.progress);
//			else if ("trash".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.trash);
//			else if ("book".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.book);
//			else if ("document".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.document);
//			else if ("person".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.person);
//			else if ("home".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.home);
//			else if ("zoomIn".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.zoom_in);
//			else if ("zoomOut".equals(str))
//				this.setImageResource(com.lerrain.www.aframe.R.drawable.zoom_out);
//			else if ("checked".equals(str))
//				this.setImageResource(R.drawable.checked);
//			else if ("unchecked".equals(str))
//				this.setImageResource(R.drawable.unchecked);
//			else if ("detail".equals(str))
//				this.setImageResource(R.drawable.detail);
		}
	}

	@Override
	public void onClick(View v)
	{
		if (!this.isEnabled())
			return;

		TopButton.this.setBackgroundColor(0xAAFFFFFF);
	}
}
