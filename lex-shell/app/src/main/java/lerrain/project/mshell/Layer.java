package lerrain.project.mshell;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.polysoft.nci.ocr.IOCRScript;
import com.polysoft.nci.ocr.OCRCallJavaScriptImpl;
import com.polysoft.nci.ocr.OCRNativeApi;

public abstract class Layer extends RelativeLayout
{
	Main window;

	RelativeLayout rl;

	WebView wv;

	WebViewClient wvc;

	JsBridge adapter;

	Scroller scroller;

	int mode;

	String currentUrl;

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
        //允许JavaScript执行
        wv.getSettings().setJavaScriptEnabled(true);

        // 开启DOM缓存，开启LocalStorage存储（html5的本地存储方式）
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setDatabaseEnabled(true);//允许JavaScript执行
        wv.getSettings().setJavaScriptEnabled(true);

        // 开启DOM缓存，开启LocalStorage存储（html5的本地存储方式）
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setDatabaseEnabled(true);
		adapter = new JsBridge(this);
		wv.addJavascriptInterface(adapter, "MF");

		IOCRScript iScript = new OCRCallJavaScriptImpl(wv);
		wv.addJavascriptInterface(new OCRNativeApi(this.window, iScript), "OCR");
		setWebViewLoadListener(wv);

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
                onShow();
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
//			wv.loadDataWithBaseURL("file:///android_asset/phone.html/" + link, window.pages.get(uri), "application/phone.html", "utf-8", null);
//		}
//		else
//		{
//			Log.i("mshell", "open link: "+ link);
//
//			int m = uri.lastIndexOf("/");
//			int n = uri.lastIndexOf(".");
//			String js = uri.substring(m < 0 ? 0 : m + 1, n < 0 ? uri.length() : n);
//			wv.loadDataWithBaseURL("file:///android_asset/phone.html/" + link, window.template.replace("<!-- JS -->", js), "application/phone.html", "utf-8", null);
//		}
//	}

	public void onShow()
	{
		runJs("if (APP.onShow) { APP.onShow() }");
	}

	public void runJs(String js)
	{
		wv.loadUrl("javascript:" + js);
	}

	public void openUrl(String url)
	{
		if (url != null)
		{
			int p2 = url.lastIndexOf("?");
			window.stat("open:" + (p2 < 0 ? url : url.substring(0, p2)));
		}

		wv.loadUrl(url);
	}

	public void openLocal(String uri)
	{
		if (uri != null)
		{
			int p2 = uri.lastIndexOf("?");
			window.stat("open:" + (p2 < 0 ? uri : uri.substring(0, p2)));
		}

		wv.loadUrl("file:///android_asset/html/" + uri);
	}

	protected abstract void playEnter();

	protected abstract void playOut(int mode);

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
				int alpha = mode == 1 || mode == 3 ?
					Math.abs((scroller.getCurrY() - scroller.getStartY()) * 192 / (scroller.getFinalY() - scroller.getStartY())):
					Math.abs((scroller.getCurrY() - scroller.getFinalY()) * 192 / (scroller.getFinalY() - scroller.getStartY()));

				Layer.this.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
			}

			rl.setTranslationX(scroller.getCurrX());
			rl.setTranslationY(scroller.getCurrY());
		}
		else if (scroller.isFinished())
		{
			//1取消回退一页，2回退一页，3取消回退到首页，4回退到首页
			if (mode == 1 || mode == 3)
			{
				rl.setTranslationX(0);
				rl.setTranslationY(0);

				Layer.this.setBackgroundColor(0xC0000000);

				if (mode == 3)
					for (int i = this.getChildCount() - 1; i > 1; i--)
						((Layers) Layer.this.getParent()).getChildAt(i).setVisibility(View.VISIBLE);
			}
			else if (mode == 2)
			{
				((Layers) Layer.this.getParent()).removeView(Layer.this);
			}
			else if (mode == 4)
			{
				Layers root = (Layers) Layer.this.getParent();
				root.removeViews(2, root.getChildCount() - 2);
			}

			mode = 0;
		}

		super.computeScroll();
	}

	private static void setWebViewLoadListener(WebView webView) {
		WebChromeClient listener = new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {

				}
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
				// TODO Auto-generated method stub
				AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());
				b.setTitle("Alert");
				b.setMessage(message);
				b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}
				});
				b.setCancelable(false);
				b.create().show();
				return true;
			}
		};
		webView.setWebChromeClient(listener);
	}
}
