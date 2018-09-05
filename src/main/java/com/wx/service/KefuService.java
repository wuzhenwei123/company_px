package com.wx.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.utils.ConfigConstants;
import com.base.utils.https.HttpUtils;

@Component
@Transactional
public class KefuService {
	
	@Autowired
	private WeiXinService weiXinService;
	
	/**
	 * 创建一个客服回话
	 * @param openId
	 */
	public boolean CreateSession(String openId){
		try {
			JSONArray arrayAll = new  JSONArray();
			
			String jsonStr1 = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist", "GET", "access_token=" + weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET));
   		 	System.out.println("----------jsonStr1---------"+jsonStr1);
			JSONObject json = JSONObject.parseObject(jsonStr1);
			JSONArray array = json.getJSONArray("kf_online_list");
			if(array!=null&&array.size()>0){
				for(Object obj:array){
					JSONObject json1 = JSONObject.parseObject(obj.toString());
					if(json1.getInteger("status")==1){
//						if("xlts@gh_130df63251ac".equals(json1.getString("kf_account"))){
							arrayAll.add(json1);
//						}
					}
				}
			}
			if(arrayAll!=null&&arrayAll.size()>0){
				arrayAll = this.getSortArray(arrayAll);
				JSONObject param = arrayAll.getJSONObject(0);
				param.put("kf_account", param.get("kf_account"));
		    	param.put("openid", openId);
				String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/customservice/kfsession/create?access_token="+weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET), "POST", param.toString());
				System.out.println("----------jsonStr---------"+jsonStr);
				JSONObject jsonSession = JSONObject.parseObject(jsonStr);
				if("ok".equals(jsonSession.get("errmsg"))){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public JSONArray getSortArray(JSONArray jsonArr){

	    JSONArray sortedJsonArray = new JSONArray();

	    List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	    for (int i = 0; i < jsonArr.size(); i++) {
	        jsonValues.add(jsonArr.getJSONObject(i));
	    }
	    Collections.sort( jsonValues, new Comparator<JSONObject>() {
	        private static final String KEY_NAME = "accepted_case";
	        @Override
	        public int compare(JSONObject a, JSONObject b) {
	            String valA = new String();
	            String valB = new String();
	            try {
	                valA = (String) a.get(KEY_NAME);
	                valB = (String) b.get(KEY_NAME);
	            }  catch (Exception e) {
	            }
	            return valA.compareTo(valB);
	        }
	    });

	    for (int i = 0; i < jsonArr.size(); i++) {
	        sortedJsonArray.add(jsonValues.get(i));
	    }
	    return sortedJsonArray;
	}
	
	/**
     * 添加客服账号
     */
    
    public String addKfAccount(String params,String accessToken) throws Exception{
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/customservice/kfaccount/add?access_token="+accessToken, "POST", params);
    	System.out.println(jsonStr);
    	return jsonStr;
    }

}
