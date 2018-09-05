package com.base.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLHandshakeException;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * @author Keeny (2012-11-19)
 **/
public class HttpClientTools {
	/**
	 * 
	 * @param isRetry
	 *            是否自动重连
	 * @return
	 */
	public DefaultHttpClient getDefaultHttpClient(boolean isRetry) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 15000)
				.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 15000);
		if (isRetry) {
			HttpRequestRetryHandler httpRequestRetryHandler = getHttpRequestRetryHandler();
			httpClient.setHttpRequestRetryHandler(httpRequestRetryHandler);// 设置自动连接
		}
		return httpClient;
	}

	/**
	 * 
	 * @param serverURL
	 * @param param
	 * @param charset
	 * @param headers
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String post(String serverURL, String param, String charset, Map<String, String> headers)
			throws ClientProtocolException, IOException {
		DefaultHttpClient httpClient = getDefaultHttpClient(true);
		CookieStore cookieStore = new BasicCookieStore();
		HttpContext context = new BasicHttpContext();
		context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		HttpPost httpPost = new HttpPost(serverURL);
		if (param != null) {// Set Params
			StringEntity entity = new StringEntity(param, "UTF-8");
			entity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(entity);
		}
		if (headers != null) {
			for (Entry<String, String> k : headers.entrySet()) {
				httpPost.setHeader(k.getKey(), k.getValue());
			}
		}
		HttpResponse httpResponse = httpClient.execute(httpPost, context);
		HttpEntity httpEntity = httpResponse.getEntity();
		String result = EntityUtils.toString(httpEntity);

		if (charset != null) {
			result = new String(result.getBytes("ISO-8859-1"), charset);
		}

//		close(httpResponse);
		close(httpClient);

		return result;
	}

	public String get(String url, String charset, HashMap<String, String> headers) {
		DefaultHttpClient httpClient = getDefaultHttpClient(true);
		HttpContext localContext = new BasicHttpContext();
		String content = "";
		HttpGet httpGet = new HttpGet(url);
		if (headers != null) {
			for (Entry<String, String> k : headers.entrySet()) {
				httpGet.setHeader(k.getKey(), k.getValue());
			}
		}
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			Header[] headerstr = response.getAllHeaders();
//			System.out.println("HEAD-----------------------------------------");
//			for (Header header : headerstr) {
//				System.out.println("key:" + header.getName() + ",\tvalue:" + header.getValue());
//			}
//			System.out.println("HEAD-----------------------------------------");

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					// entity = new BufferedHttpEntity(entity);// 放入缓存
					content = EntityUtils.toString(entity);
					if (charset != null) {
						content = new String(content.getBytes("ISO-8859-1"), charset);
					}
				}
			}
//			System.out.println("================");

//			close(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		close(httpClient);// 释放资源

		return content;
	}

	public void close(HttpClient httpClient) {
		HttpClientUtils.closeQuietly(httpClient);// 释放资源
	}

	public void close(HttpResponse response) {
		HttpClientUtils.closeQuietly(response);// 释放资源
	}

	/**
	 * Retry Connection
	 * 
	 * @return
	 */
	private HttpRequestRetryHandler getHttpRequestRetryHandler() {
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= 5) {
					// 如果超过最大重试次数，那么就不要继续了
					return false;
				}
				if (exception instanceof NoHttpResponseException) {
					// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {
					// 不要重试SSL握手异常
					return false;
				}
				HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// 如果请求被认为是幂等的，那么就重试
					return true;
				}
				return false;
			}
		};
		return myRetryHandler;
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
//		String url = "http://api.map.baidu.com/geocoder/v2/?ak=5irZplwRy5IFMQZqwFjIkoLl&callback=renderReverse&location=39.983424,116.322987&output=json&pois=0&coordtype=bd09ll";
		String url = "http://gd.aixuetang.com";
		String s = new HttpClientTools().get(url, null, null);
		System.out.println(s);
	}
}
