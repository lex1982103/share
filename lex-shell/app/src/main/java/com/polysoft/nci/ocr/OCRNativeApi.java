package com.polysoft.nci.ocr;

import android.webkit.JavascriptInterface;

import com.polysoft.nci.interf.Callback;
import com.polysoft.nci.interf.IActivity;

public class OCRNativeApi {

	private IOCRScript iScript;
	private IActivity iActivity;

	public OCRNativeApi(IActivity iActivity, IOCRScript iScript) {
		// TODO Auto-generated constructor stub
		this.iActivity = iActivity;
		this.iScript = iScript;
	}

	/**
	 * 调用OCR证件 正面识别，目前只支持身份证
	 * @param custType 客户类别
	 */
	@JavascriptInterface
	public void callCardFront(String custType, final String flag) {

		Callback<OCRData> callback = new Callback<OCRData>() {
			@Override
			public void onResult(OCRData ocr) {
				// TODO Auto-generated method stub
				iScript.buildObjData(flag, ocr.getBitmapStr(), ocr);
			}
		};

		OCRCardCamera.openFrontCamera(this.iActivity, callback);
	}

	/**
	 * 调用OCR证件 反面识别，目前只支持身份证
	 * @param custType 客户类别
	 */
	@JavascriptInterface
	public void callCardBack(String custType, final String flag) {
		Callback<OCRData> callback = new Callback<OCRData>() {
			@Override
			public void onResult(OCRData ocr) {
				// TODO Auto-generated method stub
				iScript.buildObjData(flag, ocr.getBitmapStr(), ocr);
			}
		};

		OCRCardCamera.openBackCamera(this.iActivity, callback);
	}

	/**
	 * 调用OCR银行卡 识别
	 * @param custType 客户类别
	 */
	@JavascriptInterface
	public void callCardBank(String custType, final String flag) {
		Callback<OCRData> callback = new Callback<OCRData>() {
			@Override
			public void onResult(OCRData ocr) {
				// TODO Auto-generated method stub
				iScript.buildObjData(flag, ocr.getBitmapStr(), ocr);
			}
		};

		OCRBankCamera.openBankCamera(this.iActivity, callback);
	}

}
