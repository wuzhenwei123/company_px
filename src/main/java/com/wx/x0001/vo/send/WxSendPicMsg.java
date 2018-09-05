package com.wx.x0001.vo.send;

import org.jdom.Document;
import org.jdom.Element;

public class WxSendPicMsg extends WxSendMsg {
	private String mediaId;
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public WxSendPicMsg(WxSendMsg msg,String mediaId) {
		super(msg);
		setMsgType("image");
		this.mediaId = mediaId;
	}
	
	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		Element root = doc.getRootElement();
		Element Image = createElement(root, "Image", "");
		createElement(Image, "MediaId", getMediaId());
		return doc;
	}
}
