package com.polysoft.nci.ocr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import com.intsig.ccrengine.CCREngine;
import com.intsig.ccrengine.CCREngine.ResultData;
import com.intsig.ccrengine.CommonUtil;
import com.intsig.ccrengine.ISCardScanActivity;
import com.polysoft.nci.interf.Callback;
import com.polysoft.nci.interf.IActivity;
import com.polysoft.nci.interf.IActivityResult;
import com.polysoft.nci.ocr.OCRData.OCRBankData;
import com.polysoft.nci.utils.BitmapUtil;

public class OCRBankCamera implements IActivityResult {

	
	public static void openBankCamera(IActivity iActivity, Callback<OCRData> callback) {
		new OCRBankCamera(callback).startCamera(iActivity);
	}
	
	
	
	private Callback<OCRData> callback;
	public OCRBankCamera(Callback<OCRData> callback) {
		// TODO Auto-generated constructor stub
		this.callback = callback;
	}
	
	
	private void startCamera(IActivity iActivity) {
		Intent intent = new Intent(iActivity.getActivity(), ISCardScanActivity.class);
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_ORIENTATION, ISCardScanActivity.ORIENTATION_HORIZONTAL);
		/*
		 * @CN: 指定SDK相机模块ISCardScanActivity四边框角线条,检测到银行卡图片后的颜色
		 * 
		 * @EN: set the Quadrilateral angle color when the camera is checking
		 * the picture.
		 */
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_MATCH, 0xffff0000);
		/*
		 * @CN: 指定SDK相机模块ISCardScanActivity四边框角线条颜色，正常显示颜色
		 * 
		 * @EN: set the Quadrilateral angle default color
		 */
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_NORMAL, 0xff00ff00);
		/*
		 * @CN: 指定SDK相机模块ISCardScanActivity提示字符串
		 * 
		 * @EN: set the title of the user define camera
		 */
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_TIPS, OCRTypeEnum.BANK.tipInfoMd);
		/*
		 * @CN: 合合信息授权提供的APP_KEY
		 * 
		 * @EN: set the appkey of intsig
		 */
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_APP_KEY, OCRConstant.APP_KEY);
		/*
		 * @CN: 指定SDK相机模块是否返回银行卡卡号截图
		 * 
		 * @EN: Specifies whether the SDK camera module returns the bank card
		 * number. set true or false
		 */
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_GET_NUMBER_IMG, true);
		/*
		 * @CN: 指定SDK相机模块银行卡切边图路径
		 * 
		 * @EN:Specify the SDK camera module bank khache edge graph path
		 */
//		intent.putExtra(ISCardScanActivity.EXTRA_KEY_GET_TRIMED_IMG, "/sdcard/trimedcard.jpg");
		/*
		 * @CN: 指定SDK相机模块银行卡原图路径
		 * 
		 * @EN:Specify the SDK camera module bank card original path
		 */
		intent.putExtra(ISCardScanActivity.EXTRA_KEY_GET_ORIGINAL_IMG, OCRConstant.OCR_IMAGE_PATH);
		
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
			
		} else if (resultCode == Activity.RESULT_OK) {
			ResultData result = (ResultData) data.getSerializableExtra(ISCardScanActivity.EXTRA_KEY_RESULT);
			Bitmap bitmap = data.getParcelableExtra(ISCardScanActivity.EXTRA_KEY_GET_NUMBER_IMG);
			String bitmapStr = BitmapUtil.bitmapToString(bitmap);
			bitmap.recycle();
			
			this.callback.onResult(new OCRData("", bitmapStr, new OCRBankData(result)));
		}
	}
	
	
	@SuppressWarnings("unused")
	private String getCreditCardType(int type) {
		switch (type) {
		case CCREngine.CCR_CARD_TYPE_VISA:// 4; // Visa
			return "Visa";
		case CCREngine.CCR_CARD_TYPE_MASTER:// 5; // MasterCard
			return "MasterCard";
		case CCREngine.CCR_CARD_TYPE_MAESTRO:// 6; // Maestro
			return "Maestro";
		case CCREngine.CCR_CARD_TYPE_AMEX:// 7; // American Express
			return "American Express";
		case CCREngine.CCR_CARD_TYPE_DINERS:// 8; // Diners Club
			return "Diners Club";
		case CCREngine.CCR_CARD_TYPE_DISCOVER:// 9; // Discover
			return "Discover";
		case CCREngine.CCR_CARD_TYPE_JCB:// 10; // JCB
			return "JCB";
		case CCREngine.CCR_CARD_TYPE_CHINA_UNIONPAY:// 11; // 银联
			return "银联";
		default:
			return " ";
		}
	}
}
