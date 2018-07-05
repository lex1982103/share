package com.polysoft.nci.ocr;



public enum OCRTypeEnum {

	FRONT("将身份证正面置于此区域内，并对齐扫描框边缘"),
	BACK("将身份证反面置于此区域内，并对齐扫描框边缘"),
	BANK("将银行卡正面置于此区域，并对齐扫描框边缘");

	public final String tipInfoMd;
	private OCRTypeEnum(String tipInfoMd) {
		// TODO Auto-generated constructor stub
		this.tipInfoMd = tipInfoMd;
	}

}
