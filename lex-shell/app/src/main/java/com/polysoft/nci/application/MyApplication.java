package com.polysoft.nci.application;

import android.app.Application;

import com.polysoft.nci.utils.CtxUtil;

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
