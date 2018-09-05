package com.wx.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.utils.ConfigConstants;
import com.base.utils.https.HttpUtils;

@Service
public class WxTemplateMsg {
	
	Logger log = Logger.getLogger(WxTemplateMsg.class);
	
	@Autowired
	private WeiXinService weiXinService;
	
	/**
	 * 商户注册支付消息
	 * @param toOPENID
	 * @param money 订单金额
	 * @param orderNo 订单编号
	 * @param contact_name 单位联系人姓名
	 * @param contact_phone 单位联系人电话
	 * @param companyId 单位ID
	 * @param companyName 单位名称
	 * @return
	 */
	public String sendRegTempltMsg(String toOPENID,String dbName,String name,String roleName){
		log.info("-------商户注册支付消息---toOPENID-------->"+toOPENID);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			String accessToken = weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET);
			String content = "{\"touser\": \""+toOPENID+"\","
					+ "\"template_id\": \"UxJxGzL2nDBuzrqTTaC5MHjmoEBaJ1YFEPlgVzhc-YM\","
//					+ "\"template_id\": \"SpK2HBhYq5j5spswTqyzJrMqzJTeRDhkIgcHiRMADzM\","
					+ "\"topcolor\": \"#FF0000\","
					+ "\"data\": {\"first\": {\"value\": \""+dbName+"您好，您有新的"+roleName+"注册成功（并支付成功），信息如下:\",\"color\": \"#173177\"},"
					+ "\"keyword1\": {\"value\": \""+name+"\",\"color\": \"#173177\"},"
					+ "\"keyword2\": {\"value\": \""+sf.format(new Date())+"\",\"color\": \"#173177\"},"
					+ "\"remark\": {\"value\": \"请您尽快审核，谢谢！\",\"color\": \"#173177\"}}}";
			String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken, "POST", content);
			log.info("-------商户注册支付消息发送消息--------->"+jsonStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 商户注册支付消息
	 * @param toOPENID
	 * @param money 订单金额
	 * @param orderNo 订单编号
	 * @param contact_name 单位联系人姓名
	 * @param contact_phone 单位联系人电话
	 * @param companyId 单位ID
	 * @param companyName 单位名称
	 * @return
	 */
	public String sendUpdateMchTempltMsg(String toOPENID,String dbName,String name,String roleName){
		log.info("-------商户注册支付消息---toOPENID-------->"+toOPENID);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			String accessToken = weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET);
			String content = "{\"touser\": \""+toOPENID+"\","
					+ "\"template_id\": \"UxJxGzL2nDBuzrqTTaC5MHjmoEBaJ1YFEPlgVzhc-YM\","
					+ "\"topcolor\": \"#FF0000\","
					+ "\"data\": {\"first\": {\"value\": \""+dbName+"您好，您的客户"+roleName+"修改了信息，信息如下:\",\"color\": \"#173177\"},"
					+ "\"keyword1\": {\"value\": \""+name+"\",\"color\": \"#173177\"},"
					+ "\"keyword2\": {\"value\": \""+sf.format(new Date())+"\",\"color\": \"#173177\"},"
					+ "\"remark\": {\"value\": \"请您尽快审核，谢谢！\",\"color\": \"#173177\"}}}";
			String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken, "POST", content);
			log.info("-------商户注册支付消息发送消息--------->"+jsonStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 审核成功通知
	 * @param toOPENID
	 * @param money 订单金额
	 * @param orderNo 订单编号
	 * @param contact_name 单位联系人姓名
	 * @param contact_phone 单位联系人电话
	 * @param companyId 单位ID
	 * @param companyName 单位名称
	 * @return
	 */
	public String sendCheckTempltMsg(String toOPENID,String name){
		log.info("-------商户审核成功消息---toOPENID-------->"+toOPENID);
		try{
			String accessToken = weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET);
			String content = "{\"touser\": \""+toOPENID+"\","
					+ "\"template_id\": \"gWxGDxA0x30SBpxTXDzW8MNSuxI1-cOQqemH5hWjs5M\","
					+ "\"topcolor\": \"#FF0000\","
					+ "\"data\": {\"first\": {\"value\": \""+name+"您好，您注册资质已被审核通过，信息如下:\",\"color\": \"#173177\"},"
//					+ "\"keyword1\": {\"value\": \""+phone+"\",\"color\": \"#173177\"},"
//					+ "\"keyword2\": {\"value\": \""+pwd+"\",\"color\": \"#173177\"},"
//					+ "\"keyword3\": {\"value\": \""+phone+"\",\"color\": \"#173177\"},"
					+ "\"remark\": {\"value\": \"感谢您的注册。\",\"color\": \"#173177\"}}}";
			String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken, "POST", content);
			log.info("-------商户审核成功消息--------->"+jsonStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 发送佣金模板信息
	 * @param appid
	 * @param secret
	 * @return
	 */
	public String sendYJTempltMsg(String toOPENID,Long money){
		System.out.println("----------toOPENID-------->"+toOPENID);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		try{
			String accessToken = weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET);
			String content = "{\"touser\": \""+toOPENID+"\","
					+ "\"template_id\": \"qSHnjkcNbwD6QJt_i-hl0JeG8AQD_C_wj-FpjYhhbDk\","
					+ "\"topcolor\": \"#FF0000\","
					+ "\"data\": {\"first\": {\"value\": \"您好，您的佣金已存入账户\",\"color\": \"#173177\"},"
					+ "\"keyword1\": {\"value\": \""+this.getMoney(money)+"元\",\"color\": \"#173177\"},"
					+ "\"keyword2\": {\"value\": \""+sf.format(new Date())+"\",\"color\": \"#173177\"},"
					+ "\"remark\": {\"value\": \"感谢有您，请继续分享您的专有二维码哦。\",\"color\": \"#173177\"}}}";
			
			String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken, "POST", content);
			System.out.println("-------发送消息--------->"+jsonStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String getMoney(Long money){
		String totalFeeStr1 = null;
		if(money!=null&&money>0){
			if(String.valueOf(money).length()>2){
				Long fen = money%100;
				if(fen>0&&fen<10){
					totalFeeStr1 = money/100 + ".0" + fen;
				}else if(fen>=10){
					totalFeeStr1 = money/100 + "." + fen;
				}else{
					totalFeeStr1 = money/100 + ".00";
				}
			}else if(String.valueOf(money).length()==1){
				totalFeeStr1 = "0.0"+money;
			}else{
				totalFeeStr1 = "0."+money;
			}
		}else{
			totalFeeStr1 = "0.00";
		}
		return totalFeeStr1;
	}

}
