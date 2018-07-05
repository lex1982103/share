package com.polysoft.nci.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

	public static String readConent(String path, String charset) {
		File file = new File(path);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			return readContent(fis, charset);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	
	public static String readContent(InputStream is, String charset) throws IOException {
		byte[] buffer = new byte[is.available()];
		is.read(buffer);
		return new String(buffer, charset);
	}
}
