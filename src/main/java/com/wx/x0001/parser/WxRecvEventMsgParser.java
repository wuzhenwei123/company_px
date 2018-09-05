package com.wx.x0001.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.wx.x0001.vo.recv.WxRecvEventMsg;
import com.wx.x0001.vo.recv.WxRecvMsg;


public class WxRecvEventMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvEventMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		String event = getElementText(root, "Event");
		String eventKey = getElementText(root, "EventKey");
		String Ticket = getElementText(root, "Ticket");
		
		return new WxRecvEventMsg(msg, event,eventKey,Ticket);
	}

}
