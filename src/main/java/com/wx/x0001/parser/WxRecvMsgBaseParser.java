package com.wx.x0001.parser;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.wx.x0001.vo.recv.WxRecvMsg;


public abstract class WxRecvMsgBaseParser implements WxRecvMsgParser {

	public WxRecvMsg parser(Document doc) throws JDOMException {
		try {
			new XMLOutputter().output(doc, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Element root = doc.getRootElement();
		String toUserName = getElementText(root, "ToUserName");
		String fromUserName = getElementText(root, "FromUserName");
		String createTime = getElementText(root, "CreateTime");
		String msgType = getElementText(root, "MsgType");
		String msgId = getElementText(root, "MsgId");
		String Latitude = getElementText(root, "Latitude");
		String Longitude = getElementText(root, "Longitude");
		String Precision = getElementText(root, "Precision");
		
		return parser(root,new WxRecvMsg(toUserName,fromUserName,createTime,msgType,msgId,Latitude,Longitude,Precision));
	}
	
	protected abstract WxRecvMsg parser(Element root,WxRecvMsg msg) throws JDOMException;
	
	protected String getElementText(Element elem,String xpath) throws JDOMException {
		Text text = ((Text)XPath.selectSingleNode(elem, xpath+"/text()"));
		if(null == text) {
			return "";
		}
		return text.getText();
	}

}
