package com.wx.utils.pay.wx;

public class WXconfig {
	/**
	 * 统一下单接口
	 * */
	public static final String unifiedOrder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/**
	 * 订单查询接口
	 * */
	public static final String orderquery = "https://api.mch.weixin.qq.com/pay/orderquery";
	/**
	 * 退费
	 * */
	public static final String orderrefund = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	/**
	 * 下载对账单
	 * */
	public static final String downloadbill = "https://api.mch.weixin.qq.com/pay/downloadbill";
	
	/** 企业付款 **/
	public static final String transfers = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	
}
