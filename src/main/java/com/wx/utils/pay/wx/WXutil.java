package com.wx.utils.pay.wx;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.base.utils.ConfigConstants;
import com.base.utils.https.HttpUtils;
import com.wx.controller.WeiXinController;
import com.wx.utils.MD5;
import com.wx.utils.pay.PayUtils;




public class WXutil {
	static Logger log = Logger.getLogger(WXutil.class);
	
	@SuppressWarnings("finally")
	public static Map<String,String> post(String xml){
		String wxurl = "";
		Map<String,String> reMap = new HashMap<String,String>();
		try {
			
			String responseString = HttpUtils.httpsRequest(WXconfig.unifiedOrder, "POST", xml);
			System.out.println("----------创建订单------"+responseString);
			Map<String,String> map = PayUtils.resolve(responseString);
            String code = map.get("return_code");
            String sign = map.get("sign");
            if("SUCCESS".equals(code)||"SUCCESS"==code){ //创建订单成功
            	wxurl = map.get("code_url");
            	reMap.put("code", "1");
            	reMap.put("url",wxurl);
            	reMap.put("prepay_id", map.get("prepay_id"));
            	reMap.put("trade_type", map.get("trade_type"));
            	reMap.put("paySign", sign);
            	reMap.put("nonce_str", map.get("nonce_str"));
            }else{
            	reMap.put("code", "-2");
            	reMap.put("msg", "微信接口创建订单失败！");
            }
		} catch (Exception e) {
			reMap.put("code", "-3");
			reMap.put("msg", "未知错误");
			e.printStackTrace();
		}finally{
			return reMap;
		}
	}
	
	/**
	 * 退款申请
	 * @param xml
	 * @return
	 */
	public static Map<String,String> postRefund(String xml){
		Map<String,String> reMap = new HashMap<String,String>();
		try {
			String responseString = ClientCustomSSL.doRefund(WXconfig.orderrefund, xml);
			if(StringUtils.isNotBlank(responseString)){
				Map<String,String> map = PayUtils.resolve(responseString);
                String code = map.get("return_code");
                String result_code = map.get("result_code");
                log.info("=====TF====="+responseString);
                if("0".equals(code)||"0"==code){ //创建订单成功
                	if("SUCCESS".equals(result_code)){
                		reMap.put("code", "1");
                    	reMap.put("refund_id", map.get("refund_id"));
                	}else{
                		reMap.put("code", "-2");
                    	reMap.put("msg", "微信接口退费申请失败！");
                	}
                }else{
                	reMap.put("code", "-2");
                	reMap.put("msg", "微信接口退费申请失败！");
                }
			}
		} catch (Exception e) {
			reMap.put("code", "-3");
			reMap.put("msg", "未知错误");
			e.printStackTrace();
		}finally{
			return reMap;
		}
	}
	
	/**
	 * 提现申请
	 * @param xml
	 * @return
	 */
	public static Map<String,String> postTransfers(String xml){
		Map<String,String> reMap = new HashMap<String,String>();
		try {
			String responseString = ClientCustomSSL.doRefund(WXconfig.transfers, xml);
			System.out.println("---------------responseString------->"+responseString);
			if(StringUtils.isNotBlank(responseString)){
				Map<String,String> map = PayUtils.resolve(responseString);
                String return_code = map.get("return_code");
                if("SUCCESS".equals(return_code)||"SUCCESS"==return_code){ //创建订单成功
                	String result_code = map.get("result_code");
                	if("SUCCESS".equals(result_code)||"SUCCESS"==result_code){
                		reMap.put("code", "1");
                    	reMap.put("payment_no", map.get("payment_no"));
                    	reMap.put("payment_time", map.get("payment_time"));
                    	reMap.put("partner_trade_no", map.get("partner_trade_no"));
                	}else{
                		reMap.put("code", "-4");
                		reMap.put("return_msg", map.get("return_msg"));
                	}
                }else{
                	reMap.put("code", "-2");
                	reMap.put("msg", "微信接口充值失败！");
                }
			}
		} catch (Exception e) {
			reMap.put("code", "-3");
			reMap.put("msg", "未知错误");
			e.printStackTrace();
		}finally{
			return reMap;
		}
	}
	
	/**
	 * 订单查询
	 * @param xml
	 * @return
	 */
	public static Map<String,String> postOrderQuery(String xml){
		String wxurl = "";
		Map<String,String> reMap = new HashMap<String,String>();
		try {
			
			String responseString = HttpUtils.httpsRequest(WXconfig.orderquery, "POST", xml);
			Map<String,String> map = PayUtils.resolve(responseString);
            String trade_state = map.get("trade_state");
            if("SUCCESS".equals(trade_state)||"SUCCESS"==trade_state){ //创建订单成功
            	reMap.put("code", "1");
            	reMap.put("out_trade_no",map.get("out_trade_no"));
            	reMap.put("transaction_id",map.get("transaction_id"));
            	reMap.put("time_end",map.get("time_end"));
            }else{
            	reMap.put("code", "-2");
            	reMap.put("msg", "微信接口创建订单失败！");
            }
			
		} catch (Exception e) {
			reMap.put("code", "-3");
			reMap.put("msg", "未知错误");
			e.printStackTrace();
		}finally{
			return reMap;
		}
	}
	
	/**
	 * 微信回调签名验证
	 * */
	public static boolean verify(Map<String,String> params){
		String sign = params.get("sign");
		Map<String,String> param = PayUtils.paraFilter(params);
		String strA = PayUtils.paramLinks(param);
		String strB = strA+"&key="+ConfigConstants.APIKEY;
		String signR = new MD5().getMD5ofStr(strB).toUpperCase();
		log.info("------------sign------------------"+sign);
		log.info("------------signR------------------"+signR);
		log.info("------------strB------------------"+strB);
		if(signR.equals(sign)){
			return true;
		}else{
			return false;
		}
	}
}
