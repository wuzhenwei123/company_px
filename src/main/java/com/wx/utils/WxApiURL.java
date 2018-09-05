package com.wx.utils;

public class WxApiURL {

	/** 获取accessToken,需要参数appid，secret **/
	public static final String ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	
	/** 创建菜单,需要参数access_token **/
	public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create";
	
	/** 删除菜单,需要参数access_token **/
	public static final String DEL_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete";
	
	/** 查询菜单,需要参数access_token **/
	public static final String GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get";
	
	/** 获取用户基本信息,需要参数access_token **/
	public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	/** 获取aouth2.0网页认证返回码 **/
	public static final String GET_AOUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	/** 发送客服消息 **/
	public static final String SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
	
	/** 获取jsapi_ticket **/
	public static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	 
}
