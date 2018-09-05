package com.base.production.tools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class AssistTools {
	private static SimpleDateFormat DATEFORMAT = null;
	private static final char UNDERLINE = '_';

	/**
	 * 
	 * @param source
	 * @param defaultVale
	 */
	public static String ifNull(String source, String defaultVale) {
		if (source == null || "".equals(source))
			return defaultVale;
		return source;
	}
	/**
	 * 下划线格式字符串转换为驼峰格式字符串
	 * 
	 * @param param
	 * @return
	 */
	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	public static void ifNullToErr(Object source, String errMsg)
			throws Exception {
		if (source == null || "".equals(source)) {
			throw new Exception(errMsg);
		}
	}

	public static void ifExistsToErr(String path, String errMsg)
			throws Exception {
		File file = new File(path);
		if (file.exists()) {
			throw new Exception(errMsg);
		}
	}

	/**
	 * 当前时间
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return DATEFORMAT.format(new Date());
	}

	/**
	 * 当前字符串首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String toUppercase(String str) {
		String tem = str;
		if (tem != null && tem.length() > 0) {
			String b = tem.substring(0, 1);
			String e = tem.substring(1, tem.length());

			tem = b.toUpperCase() + e;
		}
		return tem;
	}

	/**
	 * 当前字符串首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String toLowercase(String str) {
		String tem = str;
		if (tem != null && tem.length() > 0) {
			String b = tem.substring(0, 1);
			String e = tem.substring(1, tem.length());

			tem = b.toUpperCase() + e;
		}
		return tem;
	}

	/**
	 * 
	 * @param source
	 *            源目录
	 * @param toFile
	 *            目标目录
	 */
	public static void copyFile(File source, File toFile) {
		if (source.isFile()) {
			copy(source, toFile);
		} else {
			File[] files = source.listFiles();
			for (File file : files) {
				copyFile(file, new File(toFile.getAbsoluteFile()
						+ File.separator + file.getName()));
			}
		}
	}

	public static void copy(File srcFile, File destFile) {
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteAllFile(File destFile) {
		if (destFile.exists()) {
			if (destFile.isFile()) {

			} else {
				File[] files = destFile.listFiles();
				for (File file : files) {
					deleteAllFile(file);
				}
			}
			destFile.delete();
		}
	}

	public static void main(String[] args) {
		deleteAllFile(new File("D:\\bin"));
	}
}
