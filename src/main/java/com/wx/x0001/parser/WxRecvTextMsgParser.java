package com.wx.x0001.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.wx.x0001.vo.recv.WxRecvMsg;
import com.wx.x0001.vo.recv.WxRecvTextMsg;


public class WxRecvTextMsgParser extends WxRecvMsgBaseParser{

	@Override
	protected WxRecvTextMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		return new WxRecvTextMsg(msg, getElementText(root, "Content"));
	}

	
}
