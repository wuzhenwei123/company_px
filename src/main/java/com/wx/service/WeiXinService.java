package com.wx.service;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.utils.ConfigConstants;
import com.base.utils.MemcacheUtil;
import com.base.utils.SessionName;
import com.base.utils.https.HttpUtils;
import com.sys.manageAdminUser.dao.ManageAdminUserDao;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.wx.utils.EncryptUtil;
import com.wx.utils.WxApiURL;
import com.wx.utils.https.HttpRequest;
import com.wx.utils.pay.PayUtils;

@Component
@Transactional
public class WeiXinService {
	
	Logger log = Logger.getLogger(WeiXinService.class);
	
	@Resource
    private ManageAdminUserDao manageAdminUserDao;
	
	

	public static Map<String, JSONObject> TOKEN_MAP = null;
	public static Map<String, JSONObject> TICKET_MAP = null;
	public static Integer LOGINNAME_COUNT = null;


	/**
	 * 获取accessToken
	 */
	public String getAccessToken(String appid, String secret) throws Exception {
		if (TOKEN_MAP == null) {
			TOKEN_MAP = new HashMap<String, JSONObject>();
		} else {
			JSONObject tokenJson = TOKEN_MAP.get(appid);
			if (tokenJson != null) {
				Long expires_in = (Long) tokenJson.getLong("expires_in");// token时间
				Date dateOld = new Date(expires_in);
				if (dateOld.after(new Date())) {
					return tokenJson.getString("access_token");
				}
			}
		}
		
		
		String param = "grant_type=client_credential&appid=" + appid + "&secret=" + secret;
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/token", "GET", param);
		
		
		JSONObject object = JSON.parseObject(jsonStr);
		
		Calendar c = Calendar.getInstance();  
        c.setTime(new Date());
        c.add(Calendar.MINUTE, 110);
		
        object.put("expires_in", c.getTime().getTime());
		TOKEN_MAP.put(appid, object);
        
		return object.getString("access_token");
	}

	/**
	 * 获取jsapi_ticket
	 * 
	 * @param accessToken
	 * @return
	 */
	public String getTicket(String appid, String secret) throws Exception {
		if (TICKET_MAP == null) {
			TICKET_MAP = new HashMap<String, JSONObject>();
		} else {
			JSONObject ticketJson = TICKET_MAP.get(appid);
			if (ticketJson != null) {
				Long expires_in = (Long) ticketJson.getLong("expires_in");// token时间
				Date dateOld = new Date(expires_in);
				if (dateOld.after(new Date())) {
					return ticketJson.getString("ticket");
				}
			}
		}
		String accessToken = this.getAccessToken(appid, secret);
		System.out.println("accessToken========================="+accessToken);
		
		
		String param = "access_token=" + accessToken + "&type=jsapi";
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/ticket/getticket", "GET", param);
    	
		
		System.out.println("jsonStr========================="+jsonStr);
		JSONObject object = JSON.parseObject(jsonStr);
		
		Calendar c = Calendar.getInstance();  
        c.setTime(new Date());
        c.add(Calendar.MINUTE, 110);
		
        object.put("expires_in", c.getTime().getTime());
		TICKET_MAP.put(appid, object);
		
		return object.getString("ticket");
	}

	/**
	 * 获取请求js的凭据
	 * 
	 * @param appid
	 * @param secret
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getJsticket(String appid, String secret, String url) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String noncestr = UUID.randomUUID().toString();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		String jsapi_ticket = this.getTicket(appid, secret);
		map.put("noncestr", noncestr);
		map.put("timestamp", timestamp);
		map.put("jsapi_ticket", jsapi_ticket);
		map.put("url", url);
		System.out.println("jsapi_ticket==============="+jsapi_ticket);
		String str = this.paramLinks(map);
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(str.getBytes("UTF-8"));
		String signature = byteToHex(crypt.digest());
		map.put("signature", signature);
		return map;
	}
	
	/**
	 * 获取用户基本信息
	 */

	public JSONObject getFromUserMess(String accessToken, String openID) throws Exception {
		String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/sns/userinfo", "GET", "access_token=" + accessToken + "&openid=" + openID + "&lang=zh_CN");
		JSONObject rootNode = JSONObject.parseObject(jsonStr);
	    return rootNode;
	}


	/**
     * 生成关注二维码(永久)
     * @param appid   公众号ＩＤ
     * @param secret  公众号密码
     * @param scene_str  场景编码
     * @return imgurl  二维码地址
     */
	public Map<String,String> getEWMYj(String appid, String secret,String scene_str){
		Map<String,String> map = new HashMap<String,String>();
		try{
			String accessToken = getAccessToken(appid, secret);
			String json = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\":\""+scene_str+"\"}}}";
			
			String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken, "POST", json);
			
			JSONObject object = JSON.parseObject(jsonStr);
			String ticket = object.getString("ticket");
			if(StringUtils.isNotBlank(ticket)){
				String imgurl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+URLEncoder.encode(ticket,"UTF-8");
				map.put("imgurl", imgurl);
				map.put("ticket", ticket);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	
	/* 生成关注二维码(临时)
	 * @param appid
	 * @param secret
	 */
	public Map<String,String> getEWM(String appid, String secret,String scene_id){
		String imgurl = null;
		Map<String,String> map = new HashMap<String,String>();
		try{
			String accessToken = getAccessToken(appid, secret);
			String json = "{\"expire_seconds\": 259200, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+scene_id+"\"}}}";
			System.out.println(json);
			String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken, "POST", json);
			
			JSONObject object = JSON.parseObject(jsonStr);
			String ticket = object.getString("ticket");
			if(StringUtils.isNotBlank(ticket)){
				imgurl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+URLEncoder.encode(ticket,"UTF-8");
				map.put("ticket", ticket);
				map.put("imgurl", imgurl);
				System.out.println("================================="+imgurl);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 参数拼接
	 * */
	public String paramLinks(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	
	
	
	/**
     * 获取UUID
     * @return
     */
    public String getUUID(){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        // 去掉"-"符号
        String temp = str.replace("-","");
        return temp;
    }
    
   
	
	/**
	 * 从输入流中读取数据
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public byte[] readInputStream(InputStream inStream) throws Exception {
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
	 * 获取微信坐标，转为百度坐标
	 * @param x
	 * @param y
	 * @return
	 */
	public String getBaiDuLocationXY(String x, String y) {
		
		String adcode = null;
		try{
			String url1 = "http://api.map.baidu.com/geocoder/v2/";
        	String jsonStr = HttpRequest.sendGet(url1, "ak=kqbMjjFSG8p3vvAcUNNB8558y5hyRXZD&callback=renderReverse&location="+x+","+y+"&output=json&pois=0");
        	if(StringUtils.isNotBlank(jsonStr)){
        		jsonStr = jsonStr.replaceAll("renderReverse&&renderReverse", "").replaceAll("\\(", "").replaceAll("\\)", "");
        	}
        	JSONObject object = JSON.parseObject(jsonStr);
            String jsonStr1 = object.getString("result");
            JSONObject object1 = JSON.parseObject(jsonStr1);
            String jsonStr2 = object1.getString("addressComponent");
            JSONObject object2 = JSON.parseObject(jsonStr2);
            adcode = object2.getString("adcode");
		}catch(Exception e){
			e.printStackTrace();
		}
        	
		return adcode;
    }
	
	
	
	public static InputStream getInputStream(String accessToken, String mediaId) {  
        InputStream is = null;  
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="  
                + accessToken + "&media_id=" + mediaId;  
        try {  
            URL urlGet = new URL(url);  
            HttpURLConnection http = (HttpURLConnection) urlGet  
                    .openConnection();  
            http.setRequestMethod("GET"); // 必须是get方式请求  
            http.setRequestProperty("Content-Type",  
                    "application/x-www-form-urlencoded");  
            http.setDoOutput(true);  
            http.setDoInput(true);  
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒  
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒  
            http.connect();  
            // 获取文件转化为byte流  
            is = http.getInputStream();  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return is;  
  
    }  
  
    /** 
     *  
     * 获取下载图片信息（jpg） 
     *  
     *  
     *  
     * @param mediaId 
     *  
     *            文件的id 
     *  
     * @throws Exception 
     */  
  
    public static void saveImageToDisk(String accessToken, String mediaId, String picName, String picPath)  
            throws Exception {  
        InputStream inputStream = getInputStream(accessToken, mediaId);  
        byte[] data = new byte[10240];  
        int len = 0;  
        FileOutputStream fileOutputStream = null;  
        try {  
            fileOutputStream = new FileOutputStream(picPath+picName+".jpg");  
            while ((len = inputStream.read(data)) != -1) {  
                fileOutputStream.write(data, 0, len);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (fileOutputStream != null) {  
                try {  
                    fileOutputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    } 
    
    
    /**
	 * 生成订单接口
	 * @param money  订单金额
	 * @param openId openId
	 * @param request
	 * @return 订单生成 message:ok成功/message:fail失败
	 */
	public Map<String, Object> createOrder(Integer money,String openId,String remotIp,String orderNO){
		Map<String, Object> mapresult = new HashMap<String, Object>();
		try{
			Map<String,String> map = PayUtils.getMap(money, orderNO, openId,remotIp);
			 if("1".equals(map.get("code"))){
				 mapresult.put("message", "ok");
				 mapresult.put("prepay_id", map.get("prepay_id"));
				 Map<String, String> mapSing = this.getSing(map.get("prepay_id"));
				 mapresult.put("paySign", mapSing.get("signature"));
				 mapresult.put("nonceStr", mapSing.get("nonceStr"));
				 mapresult.put("appId", ConfigConstants.APPID);
				 mapresult.put("timeStamp",mapSing.get("timeStamp"));
				 mapresult.put("orderId", orderNO);
				 mapresult.put("money", money);
			 }else{
				 mapresult.put("message", "fail");
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapresult;
	}
	
	/**
	 * 获取签名
	 * 
	 * @param appid
	 * @param secret
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getSing(String prepay_id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String noncestr = this.getUUID();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		map.put("nonceStr", noncestr);
		map.put("timeStamp", timestamp);
		map.put("appId", ConfigConstants.APPID);
		map.put("package","prepay_id="+prepay_id);
		map.put("signType", "MD5");
		String str = this.paramLinks(map);
		str = str + "&key="+ConfigConstants.APIKEY;
		System.out.println("----------str------------->"+str);
		map.put("signature", EncryptUtil.md5(str).toUpperCase());
		System.out.println("---------------signature-------------------->"+EncryptUtil.md5(str).toUpperCase());
		return map;
	}
    
    
    
    /**
     * 获取整数和小数部分
     * @param d
     * @return
     */
    public Map<String,String> getNumberPart(double dInput){
    	Map<String,String> map = new HashMap<String,String>();
        long longPart = (long) dInput;
        BigDecimal bigDecimal = new BigDecimal(Double.toString(dInput));
        BigDecimal bigDecimalLongPart = new BigDecimal(Double.toString(longPart));
        double dPoint = bigDecimal.subtract(bigDecimalLongPart).doubleValue();
        String a = String.valueOf(dPoint);
        a = a.substring(a.indexOf(".")+1, a.length());
        if(a.length()==1){
        	a = a + "0";
        }
        map.put("1", longPart+"");
        map.put("2", a);
        return map;
    }
    
    
    /**
     * 移除cookie
     * @param d
     * @return
     */
    public void removeGoodsFromCart(String goodsId,Cookie[] cookies,HttpServletResponse response){
    	try{
    		if(StringUtils.isNotBlank(goodsId)){
    			String[] goodsIds = goodsId.split(",");
    			if(null==cookies) {  
    				
    			}else{
    				for(Cookie cookie : cookies){
    					if(cookie.getName().equals("mv_goods")){
    						JSONObject json = JSONObject.parseObject(cookie.getValue());
    						Iterator<String> iterator = json.keySet().iterator();  
    						while(iterator.hasNext()){
    							String key = iterator.next();//机构信息
    							ManageAdminUser manageAdminUser = new ManageAdminUser();
    							manageAdminUser.setAdminId(Integer.valueOf(key));
    							manageAdminUser = manageAdminUserDao.getManageAdminUser(manageAdminUser);
    							JSONArray array = json.getJSONArray(key);
    							for(int i=0;i<array.size();i++){
                    				JSONObject jsonObj = array.getJSONObject(i);
                    				for(String str:goodsIds){
                    					if(Integer.valueOf(str).intValue()==jsonObj.getInteger("id").intValue()){
                    						array.remove(i);
    	                				}
                    				}
    							}
    							if(array==null||(array!=null&&array.size()==0)){
    								json.remove(key);
    							}
    						}
    						cookie.setValue(json.toJSONString()); 
                    		cookie.setPath("/");  
                            cookie.setMaxAge(60 * 60 * 24 * 365);
                            response.addCookie(cookie);  
    						break;
    					}
    				}
    			}
    			
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
  
  	public void bindUser(ManageAdminUser manageAdminUser,HttpServletRequest request){
  		try{
			manageAdminUserDao.updateManageAdminUser(manageAdminUser);
			ThreadWxMsgExtends th = new ThreadWxMsgExtends(manageAdminUser,this.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET));
			th.start();
			request.getSession().setAttribute(SessionName.ADMIN_USER_NAME, manageAdminUser.getAdminName());
			request.getSession().setAttribute(SessionName.ADMIN_USER_ID, manageAdminUser.getAdminId());
			request.getSession().setAttribute(SessionName.ADMIN_USER, manageAdminUser);
  		}catch(Exception e){
  			e.printStackTrace();
  		}
  	}
  	
  	
  	public String getChinese(String str) {
		if (str == null || "".equals(str.trim())) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		char[] aa = str.toCharArray();
		for (int i = 0; i < aa.length; i++) {
			char c = aa[i];
			if (Pattern.matches("^[a-zA-Z0-9\u4E00-\u9FA5]+$", String.valueOf(c))) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
  	
  	public String chineseName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }
  	
  	/**
  	 * 检查用户是否绑定
  	 * @param openId
  	 * @return
  	 */
  	public boolean checkBind(String openId,HttpServletRequest request){
  		try{
  			ManageAdminUser manageAdminUser = new ManageAdminUser();
  			manageAdminUser.setOpenId(openId);
  			manageAdminUser.setState(1);
  			manageAdminUser = manageAdminUserDao.getManageAdminUser(manageAdminUser);
  			if(manageAdminUser!=null&&manageAdminUser.getAdminId().intValue()>0){
  				request.getSession().removeAttribute(SessionName.ADMIN_USER);
  				request.getSession().removeAttribute(SessionName.ADMIN_USER_ID);
  				request.getSession().removeAttribute(SessionName.ADMIN_USER_NAME);
  				request.getSession().setAttribute(SessionName.ADMIN_USER, manageAdminUser);
  				request.getSession().setAttribute(SessionName.ADMIN_USER_ID, manageAdminUser.getAdminId());
  				request.getSession().setAttribute(SessionName.ADMIN_USER_NAME, manageAdminUser.getAdminName());
  				return true;
  			}else{
  				return false;
  			}
  		}catch(Exception e){
  			e.printStackTrace();
  		}
  		return false;
  	}
	
  	 class ThreadWxMsgExtends extends Thread {
 		
 		private ManageAdminUser manageAdminUser;
 		private String accessToken;
 		
 		public ThreadWxMsgExtends(ManageAdminUser manageAdminUser,String accessToken){
 			this.manageAdminUser = manageAdminUser;
 			this.accessToken = accessToken;
 		}
 		
 		 public void run(){
 			 try {
 				 String jsonInfo = HttpUtils.httpsRequest(WxApiURL.GET_USERINFO_URL, "GET", "access_token=" + accessToken + "&openid=" + manageAdminUser.getOpenId() + "&lang=zh_CN");
 				 JSONObject json = JSONObject.parseObject(jsonInfo);
 				 manageAdminUser.setNickName(json.getString("nickname"));
 				 manageAdminUser.setHeadImg(json.getString("headimgurl"));
 				 manageAdminUserDao.updateManageAdminUser(manageAdminUser);
 			} catch (Exception e) {
 				e.printStackTrace();
 			}
 		 }
 	}
}
