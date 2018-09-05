package com.wx.x0001.vo.recv;

import com.wx.x0001.vo.WxMsg;


public class WxRecvMsg extends WxMsg {
private String msgId;
	
	// Location_x
	private String latitude;
	// Location_y
	private String longitude;
	
	private String precision;
	
	public WxRecvMsg(String toUser,String fromUser,String createDt,String msgType,String msgId,String latitude,String longitude,String precision) {
		super(toUser, fromUser, createDt, msgType);
		this.msgId= msgId;
		this.latitude= latitude;
		this.longitude= longitude;
		this.precision= precision;
	}
	
	public WxRecvMsg(WxRecvMsg msg) {
		this(msg.getToUser(),msg.getFromUser(),msg.getCreateDt(),msg.getMsgType(),msg.getMsgId(),msg.getLatitude(),msg.getLongitude(),msg.getPrecision());
	}
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}
}
