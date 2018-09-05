package com.wx.x0001.parser;

import org.jdom.Document;
import org.jdom.JDOMException;

import com.wx.x0001.vo.recv.WxRecvMsg;


public interface WxRecvMsgParser {
	WxRecvMsg parser(Document doc) throws JDOMException;
}
