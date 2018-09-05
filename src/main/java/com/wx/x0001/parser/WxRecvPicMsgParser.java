package com.wx.x0001.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.wx.x0001.vo.recv.WxRecvMsg;
import com.wx.x0001.vo.recv.WxRecvPicMsg;


public class WxRecvPicMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvPicMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		return new WxRecvPicMsg(msg, getElementText(root, "PicUrl"));
	}

}
