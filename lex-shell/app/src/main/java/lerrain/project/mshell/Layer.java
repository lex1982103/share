package lerrain.project.mshell;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public abstract class Layer extends RelativeLayout
{
	Main window;

	RelativeLayout rl;

	WebView wv;

	WebViewClient wvc;

	JsBridge adapter;

	Scroller scroller;

	int mode;

	public Layer(Main window, int top)
	{
		super(window);
		
		this.window = window;
		this.setBackgroundColor(0xC0000000);

		rl = new RelativeLayout(window);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.setMargins(0, top, 0, 0);
		super.addView(rl, lp);

		scroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());
	}

	public Layers getRoot()
	{
		return (Layers)this.getParent();
	}

	protected WebView initWebView()
	{
		wv = new WebView(getContext());
		wv.setBackgroundColor(0xFFCCCCCC);
		wv.getSettings().setLoadsImagesAutomatically(true);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setSupportZoom(false);
		wv.getSettings().setUseWideViewPort(true);
		wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
		wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		adapter = new JsBridge(this);
		wv.addJavascriptInterface(adapter, "MF");

		wvc = new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				if (url != null && url.startsWith("fm://"))
					return true;

				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageFinished(WebView view, String url)
			{
			}
		};

		wv.setWebViewClient(wvc);
		return wv;
	}

	public void addView(View v, LayoutParams lp)
	{
		rl.addView(v, lp);
	}

	public void setBackButton(boolean b)
	{
	}

	public void setTitle(String text)
	{
	}

//	public void openPage(String link)
//	{
//		int p2 = link.lastIndexOf("?");
//		String uri = p2 < 0 ? link : link.substring(0, p2);
//
//		if (window.pages.containsKey(uri))
//		{
//			Log.i("mshell", "open local: "+ link);
//
//			wv.loadDataWithBaseURL("file:///android_asset/html/" + link, window.pages.get(uri), "application/html", "utf-8", null);
//		}
//		else
//		{
//			Log.i("mshell", "open link: "+ link);
//
//			int m = uri.lastIndexOf("/");
//			int n = uri.lastIndexOf(".");
//			String js = uri.substring(m < 0 ? 0 : m + 1, n < 0 ? uri.length() : n);
//			wv.loadDataWithBaseURL("file:///android_asset/html/" + link, window.template.replace("<!-- JS -->", js), "application/html", "utf-8", null);
//		}
//	}

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

	protected abstract void playEnter();

	protected abstract void playOut();

	protected void play(int x, int y, int dx, int dy, int mode)
	{
		this.mode = mode;

		scroller.startScroll(x, y, dx, dy, 300);

		this.postInvalidate();
	}

	@Override
	public void computeScroll()
	{
		if (scroller.computeScrollOffset())
		{
			this.postInvalidate();

			if (Math.abs(scroller.getFinalX() - scroller.getStartX()) > 0)
			{
				int alpha = mode == 1 ?
					Math.abs((scroller.getCurrX() - scroller.getStartX()) * 192 / (scroller.getFinalX() - scroller.getStartX())):
					Math.abs((scroller.getCurrX() - scroller.getFinalX()) * 192 / (scroller.getFinalX() - scroller.getStartX()));

				Layer.this.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
			}
			else if (Math.abs(scroller.getFinalY() - scroller.getStartY()) > 0)
			{
				int alpha = mode == 1 ?
					Math.abs((scroller.getCurrY() - scroller.getStartY()) * 192 / (scroller.getFinalY() - scroller.getStartY())):
					Math.abs((scroller.getCurrY() - scroller.getFinalY()) * 192 / (scroller.getFinalY() - scroller.getStartY()));

				Layer.this.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
			}

			rl.setTranslationX(scroller.getCurrX());
			rl.setTranslationY(scroller.getCurrY());
		}
		else if (scroller.isFinished())
		{
			if (mode == 1)
			{
				rl.setTranslationX(0);
				rl.setTranslationY(0);

				Layer.this.setBackgroundColor(0xC0000000);
			}
			else if (mode == 2)
			{
				((Layers) Layer.this.getParent()).removeView(Layer.this);
			}

			mode = 0;
		}

		super.computeScroll();
	}

}
