package com.wx.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.utils.ConfigConstants;
import com.base.utils.https.HttpUtils;
import com.wx.utils.https.HttpClientConnectionManager;
import com.wx.utils.https.HttpRequest;

/**
 * 用于微信自定义菜单的创建，查询和删除。
 * @author GXYHWZ16
 *
 */

public class WxMenuUtils {
	
	// http客户端  
//    public static  CloseableHttpClient httpclient;  
//      
//    static {  
//    	
//    	
//        httpclient = HttpClientBuilder.create().build();
//        httpclient = (CloseableHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端  
//    }  
  
    public static void main(String args[]){
   	 try {  
   		 ConfigConstants.init();
           String accessToken = getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET); 
           
           String MENU = "{\"button\":[{\"name\":\"信息查询\",\"sub_button\":" +
        			"[{\"type\":\"view\",\"name\":\"产品及政策\",\"url\":\""+getUrl("CP")+"\"}," +
        			"{\"type\":\"view\",\"name\":\"工作信息\",\"url\":\""+getUrl("GZ")+"\"}," +
        			"{\"type\":\"view\",\"name\":\"情报收集\",\"url\":\""+getUrl("QB")+"\"}]}," +
        			"{\"name\":\"业务支撑\",\"sub_button\":"+
        			"[{\"type\":\"view\",\"name\":\"业务交流\",\"url\":\""+getUrl("YW")+"\"}," +
        			"{\"type\":\"view\",\"name\":\"在线培训\",\"url\":\""+getUrl("XX")+"\"}," +
        			"{\"type\":\"view\",\"name\":\"问题与建议\",\"url\":\""+getUrl("WT")+"\"}," +
        			"{\"type\":\"view\",\"name\":\"测试与调研\",\"url\":\""+getUrl("DC")+"\"}]}," +
        			"{\"name\":\"更多\",\"sub_button\":"+
        			"[{\"type\":\"view\",\"name\":\"绑定用户\",\"url\":\""+getUrl("BD")+"\"},"+ 
        			"{\"type\":\"view\",\"name\":\"个人中心\",\"url\":\""+getUrl("GR")+"\"}," +
        			"{\"type\":\"view\",\"name\":\"操作指南\",\"url\":\""+getUrl("CZ")+"\"}]}" +
        			"]}";
           String res = createMenu(MENU, accessToken);  
//           String json = getFromUserMess(accessToken, "oEzKg1cpEXKq8ajZCoO2uQ-ZGC9s");
       	   System.out.println(res);
//           String openIDS = "odZnev5oO7-ZD-vIphYnH_ctlIBI,odZnevz3LzTEHO91fuJwf3cIPyS4,odZnev0MwmWXR48Z8E9-JHPV4Wus,odZnevyWjShVIFijmy9mVmFxBs5c,odZnevzUfuUUvjl7RWaoD0ORJq3M,odZnev0q5o4pAk8CuH0eqPPEHJkk,odZnev4DYuS0Y70SVZ2KD-lKIN-A,odZnev2oGV5h_fx5FKl1ExsrV-uI,odZnev4z-hLIkH85CYTLwCa0y2zk,odZnev4Bd80ZXfCEL8MDN8ADcJ10,odZnev5-A3khxLFOiTJUyh3sEuVE,odZnev3Lur0M_rP1SkMR9FpZTvGI,odZnev2DESSoV9FsXRbM6CDnjerM,odZnev11p9FW5xPHFyLtgVwi-NGM,odZnev9MnRfM1CiLmO6Xo87JkLY4,odZnev0dhFIv3vQRLDWpWx-HPhWw,odZnevxIjOZT34ajE9W2tKqC_CRc,odZnev8rUNMQY_IloSjOyQ1CYt2M,odZnev731YWVvTv-DZS11iLATRfw,odZnev8Mp36t7MLiPmT7pSq4KfQo,odZnev4kseHelw2XE6paoGh5QP6k,odZnev6Onpg_G6A8ADfV9jxjbubo,odZnev2IQRWBFUadVI0LUhGUxlVk,odZnev8iFUTrL353PLuVR5QpgFQs,odZnev4Rv3tVM4xGZx2QRfKKTquU,odZnev5ARUPpH5PEb6lPz3nnsXc8,odZnevyh6dcukfr8SfMauKyo3Rmo,odZnev7WS97b-cVFYOwxMHk2cwos,odZnevwatFioc46Oyf90TQErB7Eg,odZnev0YUh8mtvJleIAY8y4cAiZY,odZnev_-R62dbG94gC0hWZYgRuOo,odZnev3yBLijHAaavUvUZB2EudrE,odZnev_bZ1Fdu774brBmezFn4TgM,odZnevwLKTLyDuQFNhcPMRBcjdDc,odZnevxu7IrkHqsWSds6IorejSZU,odZnevyFdgt3K6miy4NpPr8HDdX0,odZnev_o7zPjRoUm3AkiLlpqiIqg,odZnev8YbXfBJVXUvkKO4k61duVg,odZnevw5kutmRPWaNRqZjeeRk93U,odZnev22MkSp4vHuXx6FckmEWU1Y,odZnev9eAwsVajYc7IpI1kfaCLwo,odZnev_rbAOhmnjQK9W-NldeIhNo,odZnev2eX6MfX_W2mj4oBRSE4nZk,odZnev59U5pbEtur-VaPqAXTRfsE,odZnevy1A9MoaxEwGjX3AyzyoLmQ,odZnev6uQJ-uLuWKTv-rj8qcwZO8,odZnev-Bijhe_rCjzrbuptQvxIdg,odZnevy2RvAmeHP6kcrUV_IUqFs8,odZnev1KXMGtCDehci1GUt7-RC4o,odZnevz4Qh3d_0ibPSNTVyLGmt4o,odZnev0SrdQNt1bCl9-m4kW0Vihk,odZnev6Mh0O5cnbPh6AR7favIVvM,odZnev5pk8Nl2idu3lwmRK68NeDI,odZnevz1bkU-Mfd99sEPpUWGLZlE,odZnev8EqItR0bidWG6q6BQ9qAqk,odZnev1JJ-TPGpoWD1ZsuMMXwx3A,odZnev6zvNFejO0McWImvRk_RBNM,odZnevyaOIgGNyyPB2LLTN82K2jY,odZnev8un5QHLpja9cPoAy4bgejw,odZnevyMz9wdvx9bqGH-2Zr6L6rg,odZnev7ts2jaPc9USTulOaNfACCw,odZnev8CTNDE210Kq06qTXXLeU2w,odZnev8WQ5X-IP28Erb7YQ3Is-n4,odZnev3clYG3VP4n_7-JxfFOSkz8,odZnevxXU3VaRJyOiGchfjgbKQ84,odZnevwmRYw5mtoOFXUm5p_BBX70,odZnev7noKt63JUys66SWY1JaPdo,odZnev8JDyQW_Z4-UK6vHlvWEUsI,odZnevzy3PyxrqQ6L9YAnH3yS7c0,odZnevzy3PyxrqQ6L9YAnH3yS7c0,odZnevzy3PyxrqQ6L9YAnH3yS7c0,odZnevzy3PyxrqQ6L9YAnH3yS7c0,odZnev_nE3z8mQfbi_OiY_o6x4Cs,odZnev8_x8He9GA_H5Ns9vr7MKQY,odZnev3yLYNf9PKrX3C492veeswM,odZnev5Ylpy0nCay7c5EAK2XCxGI,odZnev6s96W9cdShH_F0rsTvg5Bc,odZnev-Ekr-Ipg29Tg6QjMzqO4Zk,odZnevzorsPPqxTe_0FOBjop_A9c,odZnevypDObXXZ3mxaGzw7qqhKTU,odZnev4oMdNHlo7LhrWzvU6kN8IA,odZnevwFo6Ya5B6XL7YvuwyfPu70,odZnev82el9XRjGtRkmCYq-6LpYU,odZnev3AHjPHfPsOZvhTl1avgIXk,odZnevzAV7jVUWK_0IsjlsRLcuvc,odZnev2g2wFC9L8p4vbZdNWytOf4,odZnev6NGI7NNUZQ3W9Su5_HPCdM,odZnev5hLmdlM2Pf08K6o5cgzV5k,odZnev5XDsIlQSlqiUblb4hsMmqw,odZnevxYq4uVB9BN0f8rlOQc_esg,odZnevwsqOmMJsuT_5iS39aZIXKY";
//           writeTxt(accessToken, openIDS);
         } catch (Exception e) {  
             e.printStackTrace();  
         }  
    }
	/**
	 * 带openId的url
	 * @throws UnsupportedEncodingException 
	 */
	public static String getUrl(String state) throws UnsupportedEncodingException{
		String param = URLEncoder.encode(ConfigConstants.URL_PATH+"/weixin/access?appid="+ConfigConstants.APPID, "utf-8");
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ConfigConstants.APPID+"&redirect_uri="+param+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
		System.out.println(url);
		return url;
	}  
      
    /** 
     * 创建菜单 
     */  
    public static String createMenu(String params, String accessToken) throws Exception {  
//        HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken);  
//        httpost.setEntity(new StringEntity(params, "UTF-8"));  
//        HttpResponse response = httpclient.execute(httpost);  
//        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
    	
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken, "POST", params);
        
//    	 HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken);  
//         httpost.setEntity(new StringEntity(params, "UTF-8"));  
//         HttpResponse response = httpclient.execute(httpost);  
//         String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
    	
    	
//        String jsonStr = HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken, params);
        JSONObject json = JSONObject.parseObject(jsonStr);
        return  json.getString("errmsg"); 
    }  
    
    /**
     * 批量获取用户基本信息
     */
    
    public static void getFromUserSMess(String accessToken,String openIDS) throws Exception{
       
        if(StringUtils.isNotBlank(openIDS)){
        	String[] openID = openIDS.split(",");
        	for(int i=0;i<openID.length;i++){
        		String jsonStr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/user/info", "access_token=" + accessToken + "&openid="+openID[i]+"&lang=zh_CN");
        		 JSONObject json = JSONObject.parseObject(jsonStr);
                System.out.println("update manage_admin_user set nickName='"+json.getString("nickname")+"',realName='"+json.getString("nickname")+"',headImg='"+json.getString("headimgurl")+"' where openId = '"+json.getString("openid")+"';");
        	}
        }
        
    }
  
    /** 
     * 获取accessToken 
     */  
    public static String getAccessToken(String appid, String secret) throws Exception {  
        
    	String param = "grant_type=client_credential&appid=" + appid + "&secret=" + secret;
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/token", "GET", param);
    	
//        String jsonStr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid=" + appid + "&secret=" + secret);
//    	HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret);  
//        HttpResponse response = httpclient.execute(get);  
//        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
    	
    	JSONObject json = JSONObject.parseObject(jsonStr);
        return  json.getString("access_token"); 
    }  
      
      
    /** 
     * 删除自定义菜单 
     */  
    public static String deleteMenu(String accessToken) throws Exception {  
        
    	
    	String param = "access_token=" + accessToken;
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/menu/delete", "GET", param);
    	
//        String jsonStr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/menu/delete", "access_token=" + accessToken);
//    	 HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken);  
//         HttpResponse response = httpclient.execute(get);  
//         String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
    	
    	JSONObject json = JSONObject.parseObject(jsonStr);
        return  json.getString("errmsg"); 
    }  
    
    /**
     * 获取用户基本信息
     */
    
    public static String getFromUserMess(String accessToken,String openID) throws Exception{
        
    	
    	String param = "access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN";
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/user/info", "GET", param);
    	
//        String jsonStr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/user/info", "access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN");
        
//    	HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN" );  
//    	HttpResponse response = httpclient.execute(get);  
//        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
    	
    	return  jsonStr;
    }
    
    /**
     * 获取用户基本信息
     */
    
    public static String getFromUserMessBySQ(String accessToken,String openID) throws Exception{
        
    	
    	String param = "access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN";
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/sns/userinfo", "GET", param);
    	
//        String jsonStr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/userinfo", "access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN");
//    	HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN" );  
//    	HttpResponse response = httpclient.execute(get);  
//        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
    	System.out.println("============jsonStr===================="+jsonStr);
        return  jsonStr;
    }
    
    /**
     * 发送客服消息
     */
    
    public static void postMess(String params,String accessToken) throws Exception{
    	
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken, "POST", params);
    	
    	System.out.println("客服消息------------------"+jsonStr);
    }

    
    public static void getLoginMsg(String params)throws Exception{
        String jsonStr = HttpRequest.sendGet("http://203.208.238.200/toeclass/wechat/login.do", "openID=15235sdfs_sssdsfdsf&input_loginName=yijidaifu&input_password=yijidaifu");
        System.out.println(jsonStr);
    }
    
    /** 
     *获取aouth2.0网页认证返回码 
     */  
    public static String getAccessCode(String APPID,String SECRET,String code) throws Exception {
    	String param = "appid="+APPID+"&secret="+SECRET+"&code="+code+"&grant_type=authorization_code";
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/sns/oauth2/access_token", "GET", param);
    	System.out.println("jsonStr------------"+jsonStr);
    	return jsonStr;  
    }  
    
    /* 生成关注二维码
	 * @param appid
	 * @param secret
	 */
	public static String getEWM(String appid, String secret,String userId){
		String imgurl = null;
		try{
			String accessToken = getAccessToken(appid, secret);
			String json = "{\"expire_seconds\": 60,\"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\":\""+userId+"\"}}}";
			
//			URL url = new URL("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken);
//			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//			byte[] buf = json.getBytes();
//
//			httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
//			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//			httpConn.setRequestMethod("POST");
//			httpConn.setDoOutput(true);
//			httpConn.setDoInput(true);
//			OutputStream out = httpConn.getOutputStream();
//			out.write(buf);
//			out.close();
//
//			byte[] datas = readInputStream(httpConn.getInputStream());
//			String jsonStr = new String(datas,"utf-8");
//			
//			
	    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken, "POST", json);
			
//			HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken);  
//	        httpost.setEntity(new StringEntity(json, "UTF-8"));  
//	        HttpResponse response = httpclient.execute(httpost);  
//	        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
			
			
			JSONObject object = JSON.parseObject(jsonStr);
			String ticket = object.getString("ticket");
			if(StringUtils.isNotBlank(ticket)){
				imgurl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+URLEncoder.encode(ticket);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return imgurl;
	}
	
	/**
	 * 从输入流中读取数据
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 网页的二进制数据
		outStream.close();
		inStream.close();
		return data;
	}
	
	/**
	 * 发送模板信息
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static String sendTempltMsg(String appid, String secret, String template_id, String toOPENID){
		try{
			String accessToken = getAccessToken(appid, secret);
			toOPENID = "ogJyvuC4eTh_jSkW-JrFgpGZeKBY";
			template_id = "Y1tUY-szw7FGbzOBeiSarO2YDjheyjechMWZor_PHp4";
			String json = "{\"touser\": \""+toOPENID+"\","
					+ "\"template_id\": \""+template_id+"\","
					+ "\"url\": \"http://weixin.qq.com/download\","
					+ "\"topcolor\": \"#FF0000\","
					+ "\"data\": {\"first\": {\"value\": \"您好，欢迎使用！\",\"color\": \"#173177\"},"
					+ "\"product\": {\"value\": \"推荐抵用券\",\"color\": \"#173177\"},"
					+ "\"price\": {\"value\": \"2\",\"color\": \"#173177\"},"
					+ "\"time\": {\"value\": \"2015-09-01\",\"color\": \"#173177\"},"
					+ "\"remark\": {\"value\": \"感谢\",\"color\": \"#173177\"}}}";
			
//			URL url = new URL("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken);
//			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//			byte[] buf = json.getBytes();
//
//			httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
//			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//			httpConn.setRequestMethod("POST");
//			httpConn.setDoOutput(true);
//			httpConn.setDoInput(true);
//			OutputStream out = httpConn.getOutputStream();
//			out.write(buf);
//			out.close();
//
//			byte[] datas = readInputStream(httpConn.getInputStream());
//			String jsonStr = new String(datas,"utf-8");
			
			String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken, "POST", json);
			
//			HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken);  
//	        httpost.setEntity(new StringEntity(json, "UTF-8"));  
//	        HttpResponse response = httpclient.execute(httpost);  
//	        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
			System.out.println(jsonStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * 生成关注二维码(永久)
     * @param appid   公众号ＩＤ
     * @param secret  公众号密码
     * @param scene_str  场景编码
     * @return imgurl  二维码地址
     */
	public static String getEWMYj(String appid, String secret,String scene_str){
		String imgurl = null;
		try{
			String accessToken = getAccessToken(appid, secret);
			String json = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\":\""+scene_str+"\"}}}";
			
//			URL url = new URL("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken);
//			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//			byte[] buf = json.getBytes();
//
//			httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
//			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//			httpConn.setRequestMethod("POST");
//			httpConn.setDoOutput(true);
//			httpConn.setDoInput(true);
//			OutputStream out = httpConn.getOutputStream();
//			out.write(buf);
//			out.close();
//
//			byte[] datas = readInputStream(httpConn.getInputStream());
//			String jsonStr = new String(datas,"utf-8");
			String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken, "POST", json);
//			HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken);  
//	        httpost.setEntity(new StringEntity(json, "UTF-8"));  
//	        HttpResponse response = httpclient.execute(httpost);  
//	        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
			
			JSONObject object = JSON.parseObject(jsonStr);
			String ticket = object.getString("ticket");
			if(StringUtils.isNotBlank(ticket)){
				imgurl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+URLEncoder.encode(ticket,"UTF-8");
			}
			System.out.println(imgurl);
		}catch(Exception e){
			e.printStackTrace();
		}
		return imgurl;
	}
	
}
