package com.polysoft.nci.ocr;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.intsig.idcardscan.sdk.CommonUtil;
import com.intsig.idcardscan.sdk.IDCardScanSDK;
import com.intsig.idcardscan.sdk.ISCardScanActivity;
import com.intsig.idcardscan.sdk.ResultData;
import com.polysoft.nci.interf.Callback;
import com.polysoft.nci.interf.IActivity;
import com.polysoft.nci.interf.IActivityResult;
import com.polysoft.nci.ocr.OCRData.OCRBackData;
import com.polysoft.nci.ocr.OCRData.OCRFrontData;
import com.polysoft.nci.utils.BitmapUtil;

public class OCRCardCamera implements IActivityResult {

	
	public static void openFrontCamera(IActivity iActivity, Callback<OCRData> callback) {
		new OCRCardCamera(OCRTypeEnum.FRONT, callback).startCamera(iActivity);
	}
	
	public static void openBackCamera(IActivity iActivity, Callback<OCRData> callback) {
		new OCRCardCamera(OCRTypeEnum.BACK, callback).startCamera(iActivity);
	}
	
	
	
	
	
	private Callback<OCRData> callback;
	private OCRTypeEnum ocrType;
	
	public OCRCardCamera(OCRTypeEnum ocrType, Callback<OCRData> callback) {
		// TODO Auto-generated constructor stub
		this.ocrType = ocrType;
		this.callback = callback;
	}
	
	private void startCamera(IActivity iActivity) {
		Intent intent = new Intent(iActivity.getActivity(), ISCardScanActivity.class);
		File file = new File(OCRConstant.OCR_IMAGE_PATH);
		// 指定要临时保存的身份证图片路径
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_IMAGE_NAME, file.getName());
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_TRIM_IMAGE_NAME, file.getName());
		
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_IMAGE_FOLDER, file.getParent());
		// 指定SDK相机模块ISCardScanActivity四边框角线条,检测到身份证图片后的颜色
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_MATCH, 0xffff0000);
		// 指定SDK相机模块ISCardScanActivity四边框角线条颜色，正常显示颜色
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_NORMAL, 0xff00ff00);
		// 合合信息授权提供的APP_KEY
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_APP_KEY, OCRConstant.APP_KEY);
		// 指定SDK相机模块ISCardScanActivity提示字符串
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_TIPS, this.ocrType.tipInfoMd);
		// IDCardScanSDK.OPEN_COMOLETE_CHECK
		// 表示完整性判断，IDCardScanSDK.CLOSE_COMOLETE_CHECK或其它值表示关闭完整判断
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_COMPLETECARD_IMAGE, IDCardScanSDK.OPEN_COMOLETE_CHECK);
		iActivity.startActivityForResult(intent, OCRConstant.CALL_OCR_CODE, this);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode != OCRConstant.CALL_OCR_CODE || null == data) {
			return ;
		}
		
		if (resultCode == Activity.RESULT_CANCELED ) {
			
			int code = data.getIntExtra(ISCardScanActivity.EXTRA_KEY_RESULT_ERROR_CODE, 0);
			String error = CommonUtil.commentMsg(code);
			this.callback.onResult(new OCRData(code + ":" + error));
			
		} else if (resultCode == Activity.RESULT_OK){
			// 获取身份证图片绝对路径
			String imagePath = data.getStringExtra(ISCardScanActivity.EXTRA_KEY_RESULT_IMAGE);

			// 获取身份证识别ResultData识别结果
			ResultData result = (ResultData) data.getSerializableExtra(ISCardScanActivity.EXTRA_KEY_RESULT_DATA);
			String bitmapStr = this.readFileBitmapStr(imagePath);
			if (result.isFront()) { // 正面
				OCRData ocr = new OCRData("", bitmapStr, new OCRFrontData(result));
				this.callback.onResult(ocr);
			} else {
				OCRData ocr = new OCRData("", bitmapStr, new OCRBackData(result));
				this.callback.onResult(ocr);
			}
		}
	}
	
	
	private String readFileBitmapStr(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return "";
		}
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		String bitmapStr = BitmapUtil.bitmapToString(bitmap);
		bitmap.recycle();
		file.delete();
		return bitmapStr;
	}
}
