package com.picc.ehome.application;

import android.app.Application;

import com.picc.ehome.utils.CtxUtil;

public class MyApplication extends Application {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.init();
	}

	private void init() {
		CtxUtil.setApplication(this);
	}
}
