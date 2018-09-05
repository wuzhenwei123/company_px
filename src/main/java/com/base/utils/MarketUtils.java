package com.base.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * 物流查询
 * 
 * @author zhanglibo
 *
 */
public class MarketUtils {

	private static String URL = "http://jisukdcx.market.alicloudapi.com";
	private static String APPCODE = "1714c3f2d7064828ad25c24fb02928cd";
	/**
	 * 阿里物流查询
	 * 
	 * @param number
	 */
	public static String aliQuery(String number) {
		try {
			String host = URL;
			String path = "/express/query";
			String method = "GET";
			String appcode = APPCODE;
			Map<String, String> headers = new HashMap<String, String>();
			// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
			headers.put("Authorization", "APPCODE " + appcode);
			Map<String, String> querys = new HashMap<String, String>();
			querys.put("number", number);
			querys.put("type", "auto");

			HttpResponse response = HttpClientUtils.doGet(host, path, method, headers, querys);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
