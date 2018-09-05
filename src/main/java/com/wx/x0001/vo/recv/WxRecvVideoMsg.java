package com.wx.x0001.vo.recv;


public class WxRecvVideoMsg extends WxRecvMsg {
	private String thumbMediaId;
	private String mediaId;
	
	public WxRecvVideoMsg(WxRecvMsg msg,String thumbMediaId,String mediaId) {
		super(msg);
		this.thumbMediaId = thumbMediaId;
		this.mediaId = mediaId;
	}
	
	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}
