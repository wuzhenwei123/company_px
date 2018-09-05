package com.base.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigConstants {
	
	/******上传根目录******/
	public static String UPLOAD_FILE_ROOT = null;
	/** 图片服务 **/
	public static String FILE_SERVER = null;
	/** 图片存储 **/
	public static String SYS_PIC = null;
	/******系统日志记录开关******/
	public static Boolean SYS_LOG_STATE = null;
	/** appid**/
	public static String APPID = null;
	/** appsecret**/
	public static String APPSECRET = null;
	/** 访问地址**/
	public static String URL_PATH = "";
	/** 微信token**/
	public static String TOKEN = "";
	/** 微信消息类型 **/
	public static String RECV_TYPE_TEXT = null;
	public static String RECV_TYPE_LINK = null;
	public static String RECV_TYPE_LOCATION = null;
	public static String RECV_TYPE_IMAGE = null;
	public static String RECV_TYPE_EVENT = null;
	public static String RECV_TYPE_VOICE = null;
	public static String RECV_TYPE_VIDEO = null;
	
	/** 拓展业务员角色id **/
	public static String DB_ROLE_ID = null;
	/** 批发商角色id **/
	public static String PF_ROLE_ID = null;
	/** 零售商角色id **/
	public static String LS_ROLE_ID = null;
	/** 客服角色id **/
	public static String KF_ROLE_ID = null;
	/** 电商代理角色id **/
	public static String DL_ROLE_ID = null;
	/** 支付秘钥 **/
	public static String APIKEY = null;
	/** 支付商户号 **/
	public static String MCH_ID = null;
	/** 支付IP **/
	public static String LOCAL_IP = null;
	/** 支付回调地址 **/
	public static String NOTIFY_URL = null;
	
	/** 批发商支付金额 **/
	public static String WHOLESALER_PAY = null;
	/** 零售商支付金额 **/
	public static String DEALER_PAY = null;
	/** 代理商支付金额 **/
	public static String AGENT_PAY = null;
	/** 代理商支付金额 **/
	public static String REDIRECT_URI = null;
	/** 接口key **/
	public static String PUBLIC_KEY = null;
	/** 代理商id **/
	public static String AGENT_ID = null;
	/** 支付接口地址 **/
	public static String HXT_URL = null;
	public static String MER_ID = null;
	public static String FRONTTRANSURL = null;
	public static String BACKTRANSTOKENURL = null;
	public static String TRANS = null;
	public static String REDIRECT_BACKURLTRANS = null;
	public static String REDIRECT_BACKURLDF = null;
	public static String RATE = null;
	public static String UPDATETRANSTOKENURL = null;
	public static String HXT_UPDATE_URL = null;
	public static String TRID = null;
	public static String TWO_DB_ROLE_ID = null;
	public static String SUPER_MANAGE_ROLE = null;
	
	
	private static Properties prop = null;
	public static void init(){
		String path = "/config/system.properties";
		InputStream in = ConfigConstants.class.getResourceAsStream(path);
		if (in != null) {
			prop = new Properties();
			try {
				prop.load(in);
				KF_ROLE_ID = (String) prop.get("kf_role_id");
				SYS_PIC = (String) prop.get("sys_pic");
				FILE_SERVER = (String) prop.get("file_server");
				UPLOAD_FILE_ROOT = (String) prop.get("upload_file_root");
				SYS_LOG_STATE = Boolean.valueOf((String) prop.get("sys_log_state"));
				APPID = (String) prop.get("appid");
				APPSECRET = (String) prop.get("appsecret");
				URL_PATH = (String) prop.get("urlPath");
				TOKEN = (String) prop.get("token");
				RECV_TYPE_TEXT = (String) prop.get("recv_type_text");
				RECV_TYPE_LINK = (String) prop.get("recv_type_link");
				RECV_TYPE_LOCATION = (String) prop.get("recv_type_location");
				RECV_TYPE_IMAGE = (String) prop.get("recv_type_image");
				RECV_TYPE_EVENT = (String) prop.get("recv_type_event");
				RECV_TYPE_VOICE = (String) prop.get("recv_type_voice");
				RECV_TYPE_VIDEO = (String) prop.get("recv_type_video");
				DB_ROLE_ID = (String) prop.get("db_role_id");
				PF_ROLE_ID = (String) prop.get("pf_role_id");
				LS_ROLE_ID = (String) prop.get("ls_role_id");
				DL_ROLE_ID = (String) prop.get("dl_role_id");
				APIKEY = (String) prop.get("apikey");
				MCH_ID = (String) prop.get("mch_id");
				LOCAL_IP = (String) prop.get("local_ip");
				NOTIFY_URL = (String) prop.get("notify_url");
				WHOLESALER_PAY = (String) prop.get("wholesaler_pay");
				DEALER_PAY = (String) prop.get("dealer_pay");
				AGENT_PAY = (String) prop.get("agent_pay");
				REDIRECT_URI = (String) prop.get("redirect_uri");
				PUBLIC_KEY = (String) prop.get("publicKey");
				AGENT_ID = (String) prop.get("agentId");
				HXT_URL = (String) prop.get("hxt_url");
				MER_ID = (String) prop.get("mer_id");
				FRONTTRANSURL = (String) prop.get("frontTransUrl");
				BACKTRANSTOKENURL = (String) prop.get("backTransTokenURL");
				TRANS = (String) prop.get("trans");
				REDIRECT_BACKURLTRANS = (String) prop.get("redirect_backUrlTrans");
				REDIRECT_BACKURLDF = (String) prop.get("redirect_backUrlDF");
				RATE = (String) prop.get("rate");
				UPDATETRANSTOKENURL = (String) prop.get("UpdateTransTokenURL");
				HXT_UPDATE_URL = (String) prop.get("hxt_update_url");
				TRID = (String) prop.get("trId");
				TWO_DB_ROLE_ID = (String) prop.get("two_db_role_id");
				SUPER_MANAGE_ROLE = (String) prop.get("super_manage_role");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static String getPropValue(String key) {
		String val = null;
		try {
			val = prop.getProperty(key);
		} catch (Exception e) {
			System.out.println("=====================get property file error!");
			throw new RuntimeException(e);
		}
		return val;
	}

	public static boolean isLinux() {
		String osType = System.getProperties().getProperty("os.name").toLowerCase();
		if (osType.startsWith("windows")) {
			return false;
		} else {
			return true;
		}
	}
}
