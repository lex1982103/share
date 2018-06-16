package lerrain.project.mshell;

import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Layer extends RelativeLayout
{
	int topH = 52;

	Main window;
	
	RelativeLayout top;
	
	TopButton[] tpb = new TopButton[4];
	
	TextView title;

	WebView wv;

	WebViewClient wvc;

	JsBridge adapter;
	
	public Layer(Main window)
	{
		super(window);
		
		this.window = window;
		this.setBackgroundColor(0xFFAAAAAA);
		
		init();
	}

	public Layers getRoot()
	{
		return (Layers)this.getParent();
	}

	private void init()
	{
		LayoutParams lp;
		
		if (topH > 0)
		{
			top = new RelativeLayout(window);
			top.setBackgroundColor(0xFFFFFFFF);
			lp = new LayoutParams(LayoutParams.MATCH_PARENT, Ui.dp(topH));
			this.addView(top, lp);

			tpb[0] = new TopButton(this, 0);
			tpb[0].setImage("back");
			tpb[0].setPadding(Ui.dp(7), Ui.dp(7), Ui.dp(7), Ui.dp(7));
			tpb[0].setVisibility(View.INVISIBLE);
			lp = new LayoutParams(Ui.dp(44), Ui.dp(44));
			lp.setMargins(Ui.dp(6), Ui.dp(2), 0, 0);
			lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			lp.addRule(RelativeLayout.CENTER_VERTICAL);
			top.addView(tpb[0], lp);
			
			tpb[0].setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					adapter.back();
				}
			});
			
			title = new TextView(window);
	//		title.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			title.setGravity(Gravity.CENTER);
			title.setTextSize(18);
			title.setText("");
			title.setTextColor(0xFF000000);
			lp = new LayoutParams(Ui.dp(200), Ui.dp(topH - 4));
	//		lp.setMargins(Ui.dp(60), 0, 0, 0);
	//		lp.addRule(RelativeLayout.CENTER_VERTICAL);
			lp.addRule(RelativeLayout.CENTER_IN_PARENT);
			top.addView(title, lp);
			
			title.setOnLongClickListener(new OnLongClickListener() 
			{
				@Override
				public boolean onLongClick(View v)
				{
					openUrl("MF.pressTitle()");
					return true;
				}
			});
			
			title.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v)
				{
					wv.reload();
				}
			});
		
			for (int i=1;i<=2;i++)
			{
				tpb[i] = new TopButton(this, i);
				tpb[i].setPadding(Ui.dp(7), Ui.dp(7), Ui.dp(7), Ui.dp(7));
				lp = new LayoutParams(Ui.dp(44), Ui.dp(44));
				lp.setMargins(0, Ui.dp(2), Ui.dp(44 * i - 38), 0);
				lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp.addRule(RelativeLayout.CENTER_VERTICAL);
				top.addView(tpb[i], lp);
			}
		}

		wv = new WebView(getContext());
		wv.setBackgroundColor(0xFFCCCCCC);
		wv.getSettings().setLoadsImagesAutomatically(true);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setSupportZoom(false);
		wv.getSettings().setUseWideViewPort(true);
		wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
		wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

		adapter = new JsBridge(this);
		wv.addJavascriptInterface(adapter, "MF");

		wvc = new WebViewClient()
		{
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
//			{
//				String url = request.getUrl().toString();
//				if (url != null && url.startsWith("fm://"))
//					return true;
//
//				return false;
//			}

			@Override
			public void onPageFinished(WebView view, String url)
			{
			}
		};

		wv.setWebViewClient(wvc);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.setMargins(0, Ui.dp(topH), 0, 0);
		this.addView(wv, lp);
	}

	public void setBackButton(boolean b)
	{
		if (tpb[0] != null)
			tpb[0].setVisibility(b ? View.VISIBLE : View.INVISIBLE);
	}

	public void runJs(String js)
	{
		wv.loadUrl("javascript:" + js);
	}

	public void openUrl(String url)
	{
		wv.loadUrl(url);
	}

	public void openLocal(String uri)
	{
		wv.loadUrl("file:///android_asset/html/" + uri);
	}
}
