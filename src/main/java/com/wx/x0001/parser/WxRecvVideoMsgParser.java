package com.wx.x0001.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.wx.x0001.vo.recv.WxRecvMsg;
import com.wx.x0001.vo.recv.WxRecvVideoMsg;


public class WxRecvVideoMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvVideoMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		
		String thumbMediaId = getElementText(root, "ThumbMediaId");
		String mediaId = getElementText(root, "MediaId");
		
		return new WxRecvVideoMsg(msg, thumbMediaId,mediaId);
	}

}
