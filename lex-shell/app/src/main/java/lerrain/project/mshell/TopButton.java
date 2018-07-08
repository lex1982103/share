package lerrain.project.mshell;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Scroller;

import com.picc.ehome.R;

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
		this.setPadding(Ui.dp(6), Ui.dp(6), Ui.dp(6), Ui.dp(6));
		
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
				this.setImageResource(R.drawable.arrow_left_1);
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
