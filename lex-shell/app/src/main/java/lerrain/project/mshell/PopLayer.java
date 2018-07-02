package lerrain.project.mshell;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PopLayer extends Layer
{
	int percent = 75;

	public PopLayer(Main window, int percent)
	{
		super(window, Ui.height * (100 - percent) / 100);

		this.percent = percent;

		WebView wv = super.initWebView();
		this.addView(wv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		this.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.i("mshell", "close popwin");
				getRoot().back(null);
			}
		});
	}

	protected void playEnter()
	{
		play(0, Ui.height * percent / 100, 0, -Ui.height * percent / 100, 1);
	}

	protected void playOut(int mode)
	{
		play(0, 0, 0, Ui.height * percent / 100, mode);
	}

}
