package com.wx.utils.pay.wx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.base.utils.ConfigConstants;
import com.wx.utils.EncryptUtil;
import com.wx.utils.MD5;
import com.wx.utils.pay.PayUtils;



public class WXmodel {
	
	private static Logger logger = Logger.getLogger(WXmodel.class);
	//非必需参数
	private String device_info;//设备号
	private String detail;//商品详情
	private String attach;//附加数据
	private String fee_type;//货币类型
	private String time_start;//交易起始时间
	private String time_expire;//交易结束时间
	private String goods_tag;//商品标记
	
	//必需参数
	private String appid;//公众账号ID
	private String mch_id;//商户号
	private String nonce_str;//随机字符串
	private String sign;//签名
	private String body;//商品描述
	private String out_trade_no;//商户订单号
	private String product_id;//商品ID
	private String total_fee;//总金额
	private String spbill_create_ip;//终端IP
	private String notify_url;//通知地址
	private String trade_type="JSAPI";//交易类型
	private String openid;//用户标识
	private String op_user_id;//操作员id，默认商户号
	private String refund_fee;//退款总金额
	private String out_refund_no;//商户系统内部的退款单号
	//参数集合
	private Map<String,String> param ;
	
	public WXmodel(String out_trade_no){
		this.param = new HashMap<String,String>();
		this.appid = ConfigConstants.APPID;
		this.param.put("appid", this.appid);
		this.mch_id = ConfigConstants.MCH_ID;
		this.param.put("mch_id",this.mch_id);
		this.out_trade_no = out_trade_no;
		this.param.put("out_trade_no",this.out_trade_no);
		this.nonce_str = EncryptUtil.create_nonce_str(32);;
		this.param.put("nonce_str",this.nonce_str);
		this.sign = sign();
		this.param.put("sign", this.sign);
	}
	
	public WXmodel(String out_trade_no, String total_fee,String out_refund_no) {
		this.param = new HashMap<String,String>();
		this.appid = ConfigConstants.APPID;
		this.param.put("appid", this.appid);
		this.mch_id = ConfigConstants.MCH_ID;
		this.param.put("mch_id",this.mch_id);
		this.nonce_str = new MD5().create_nonce_str(32);;
		this.param.put("nonce_str",this.nonce_str);
		this.out_trade_no = out_trade_no;
		this.param.put("out_trade_no",this.out_trade_no);
		this.total_fee = total_fee;
		this.param.put("total_fee",this.total_fee);
		this.out_refund_no = out_refund_no;
		this.param.put("out_refund_no",this.out_refund_no);
		this.refund_fee = total_fee;
		this.param.put("refund_fee",this.refund_fee);
		this.op_user_id = ConfigConstants.MCH_ID;
		this.param.put("op_user_id",this.op_user_id);
		this.sign = sign();
		this.param.put("sign", this.sign);
	}
	
	public WXmodel(String body, String out_trade_no, String total_fee,String product_id,String openid,String ip) {
		this.param = new HashMap<String,String>();
		this.appid = ConfigConstants.APPID;
		this.param.put("appid", this.appid);
		this.mch_id = ConfigConstants.MCH_ID;
		this.param.put("mch_id",this.mch_id);
		this.nonce_str = new MD5().create_nonce_str(32);
		this.param.put("nonce_str",this.nonce_str);
		this.body = body;
		this.param.put("body",this.body);
		this.out_trade_no = out_trade_no;
		this.param.put("out_trade_no",this.out_trade_no);
		this.total_fee = total_fee;
		this.param.put("total_fee",this.total_fee);
		this.spbill_create_ip = ConfigConstants.LOCAL_IP;
		this.param.put("spbill_create_ip",this.spbill_create_ip);
		this.notify_url = ConfigConstants.NOTIFY_URL;
		this.param.put("notify_url",this.notify_url);
		this.product_id = product_id;
		this.param.put("trade_type", this.trade_type);
		this.openid = openid;
		this.param.put("openid", this.openid);
		this.param.put("device_info", "WEB");
		this.sign = sign();
		this.param.put("sign", this.sign);
	}
	
	
	/**
	 * 企业支付
	 * @param body
	 * @param out_trade_no
	 * @param total_fee
	 * @param openid
	 * @param ip
	 */
	public WXmodel(String body, String out_trade_no, String total_fee,String openid,String ip) {
		this.param = new HashMap<String,String>();
		this.appid = ConfigConstants.APPID;
		this.param.put("mch_appid", this.appid);
		this.mch_id = ConfigConstants.MCH_ID;
		this.param.put("mchid",this.mch_id);
		this.nonce_str = new MD5().create_nonce_str(32);
		this.param.put("nonce_str",this.nonce_str);
		this.body = body;
		this.param.put("desc",this.body);
		this.out_trade_no = out_trade_no;
		this.param.put("partner_trade_no",this.out_trade_no);
		this.total_fee = total_fee;
		this.param.put("amount",this.total_fee);
		this.spbill_create_ip = ConfigConstants.LOCAL_IP;
		this.param.put("spbill_create_ip",this.spbill_create_ip);
		this.param.put("check_name", "NO_CHECK");
		this.openid = openid;
		this.param.put("openid", this.openid);
		this.sign = sign();
		this.param.put("sign", this.sign);
	}
	
	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public Map<String,String> getParam() {
		return param;
	}

	public void setParam(Map<String,String> param) {
		this.param = param;
	}

	public String sign() {
		String signs="";
		try{
			String strA = PayUtils.paramLinks(this.param);
			logger.info("StringA:=========>           "+strA);
			String strB = strA+"&key="+ConfigConstants.APIKEY;
			logger.info("StringB:=========>           "+strB);
			signs = EncryptUtil.md5(strB).toUpperCase();
		}catch(Exception e){
			
		}
		return signs;
	}
	
	public String getXml(Map param){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		for(Iterator iter = param.keySet().iterator(); iter.hasNext();){
			String name = (String)iter.next();
			sb.append("<").append(name).append("><![CDATA[");
			sb.append(param.get(name));
			sb.append("]]></").append(name).append(">");
		}
		sb.append("</xml>");
		return sb.toString();
	}
	
	
}
