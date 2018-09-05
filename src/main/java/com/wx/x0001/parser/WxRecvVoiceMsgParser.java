package com.wx.x0001.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.wx.x0001.vo.recv.WxRecvMsg;
import com.wx.x0001.vo.recv.WxRecvVoiceMsg;


public class WxRecvVoiceMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvVoiceMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		
		String format = getElementText(root, "Format");
		String mediaId = getElementText(root, "MediaId");
		String recognition = getElementText(root, "Recognition");
		return new WxRecvVoiceMsg(msg,format,mediaId,recognition);
	}

}
