package com.polysoft.nci.utils;

import java.io.IOException;
import java.io.InputStream;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;

public class CtxUtil {
	
	private static Application application;
	
	public static void setApplication(Application application) {
		CtxUtil.application = application;
	}
	
	public static Context getAppContext() {
		return CtxUtil.application;
	}
	
	
	public static boolean isInstallApk(String packageName) {
    	try {
    		application.getPackageManager().getApplicationInfo(packageName,PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
    }
	
	public static String readAssetsContent(String path, String charset) {
		AssetManager assets = getAppContext().getAssets();
		InputStream is = null;
		
		try {
			is = assets.open(path);
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			return new String(buffer, charset);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "";
	}
}
