package com.wx.x0001.vo.recv;


public class WxRecvVoiceMsg extends WxRecvMsg {
	private String format;
	private String recognition;
	private String mediaId;
	
	public WxRecvVoiceMsg(WxRecvMsg msg,String format,String mediaId) {
		super(msg);
		this.format = format;
		this.mediaId = mediaId;
	}
	
	public WxRecvVoiceMsg(WxRecvMsg msg,String format,String mediaId,String recognition) {
		super(msg);
		this.format = format;
		this.recognition = recognition;
		this.mediaId = mediaId;
	}
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}
