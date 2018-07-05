package com.polysoft.nci.ocr;

import com.google.gson.annotations.SerializedName;
import com.intsig.idcardscan.sdk.ResultData;

public class OCRData {
	/** 错误信息*/
	@SerializedName("error")
	final String error;

	@SerializedName("jsonData")
	final Object jsonObj;

	/** 图片字符串*/
	private String bitmapStr;

	public OCRData(String error) {
		this(error, null, null);
	}

	public OCRData(String error, String bitmapStr, Object jsonObj) {
		this.error = error;
		this.bitmapStr = bitmapStr;
		this.jsonObj = jsonObj;
	}

	public String getBitmapStr() {
		return this.bitmapStr;
	}


	/**
	 * OCR 证件正面识别后的数据实体
	 */
	public static class OCRFrontData {
		/** 姓名*/
		final String name;
		/** 性别*/
		final String sex;
		/** 出生日期*/
		final String birthday;
		/** 身份证号码*/
		final String cardNo;
		/** 地址信息*/
		final String address;
		/** 民族*/
		final String national;
		/** 获取是否彩色图片*/
		final boolean colorImage;
		/** 获取完整性*/
		final int complete;

		public OCRFrontData(ResultData data) {
			// TODO Auto-generated constructor stub
			this.name = data.getName();
			this.sex = data.getSex();
			this.cardNo = data.getId();
			this.address = data.getAddress();
			this.birthday = data.getBirthday();
			this.national = data.getNational();
			this.colorImage = data.isColorImage();
			this.complete = data.isComplete();
		}

	}

	/**
	 * OCR 证件反面识别后的数据实体
	 */
	public static class OCRBackData {
		/** 获取身份证签发机关*/
		final String issueauthority;
		/** 获取身份证有效期*/
		final String validity;
		/** 获取是否彩色图片*/
		final boolean colorImage;
		/** 获取完整性*/
		final int complete;

		public OCRBackData(ResultData data) {
			// TODO Auto-generated constructor stub
			this.issueauthority = data.getIssueauthority();
			this.validity = data.getValidity();
			this.colorImage = data.isColorImage();
			this.complete = data.isComplete();

		}

	}

	/**
	 * OCR 银行卡识别后的数据实体
	 */
	public static class OCRBankData {
		/** 银行卡号*/
		final String cardNo;
		/** 持卡人*/
		final String holderName;
		/** 过期日期*/
		final String validThru;
		/** 发卡机构*/
		final String insName;
		/** 卡片类型*/
		final int cardType;

		public OCRBankData(com.intsig.ccrengine.CCREngine.ResultData result) {
			// TODO Auto-generated constructor stub
			this.cardNo = result.getCardNumber();
			this.holderName = result.getCardHolderName();
			this.validThru = result.getCardValidThru();
			this.insName = result.getCardInsName();
			this.cardType = result.getBankCardType();

		}
	}
}
