package com.polysoft.nci.ocr;

import android.webkit.WebView;

import com.google.gson.Gson;

public class OCRCallJavaScriptImpl implements IOCRScript {
	private WebView webView;
	public OCRCallJavaScriptImpl(WebView webView) {
		// TODO Auto-generated constructor stub
		this.webView = webView;
	}

	@Override
	public void buildObjData(final String flag, final String bitmapStr, final OCRData obj) {
		// TODO Auto-generated method stub
		this.webView.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String json = "";
				if (null == obj.jsonObj) {
					json = new Gson().toJson(obj);
				} else {
					json = new Gson().toJson(obj.jsonObj);
				}
				String call = "javascript:callOCRBack('" + flag+ "', '" + json + "', '" + bitmapStr + "')";
				webView.loadUrl(call, null);
			}
		});
	}

}
