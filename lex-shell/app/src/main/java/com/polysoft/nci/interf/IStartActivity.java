package com.polysoft.nci.interf;

import android.content.Intent;

public interface IStartActivity {
	
	public void startActivityForResult(Intent intent, int requestCode, IActivityResult iResult);
	
}
