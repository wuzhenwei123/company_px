package com.wx.x0001.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.base.utils.ConfigConstants;
import com.wx.x0001.vo.recv.WxRecvMsg;
import com.wx.x0001.vo.send.WxSendMsg;


public final class WxMsgKit {
	
	private static final Map<String, WxRecvMsgParser> recvParserMap = new HashMap<String, WxRecvMsgParser>();
	
	static {
		// 文本消息解析程序
		recvParserMap.put(ConfigConstants.RECV_TYPE_TEXT, new WxRecvTextMsgParser());
		// 链接消息解析程序
		recvParserMap.put(ConfigConstants.RECV_TYPE_LINK, new WxRecvLinkMsgParser());
		// 地址消息解析程序
		recvParserMap.put(ConfigConstants.RECV_TYPE_LOCATION, new WxRecvGeoMsgParser());
		// 图片消息解析程序
		recvParserMap.put(ConfigConstants.RECV_TYPE_IMAGE, new WxRecvPicMsgParser());
		// 事件消息解析程序
		recvParserMap.put(ConfigConstants.RECV_TYPE_EVENT, new WxRecvEventMsgParser());
		// 语音消息解析程序
		recvParserMap.put(ConfigConstants.RECV_TYPE_VOICE, new WxRecvVoiceMsgParser());
		// 视频消息解析程序
		recvParserMap.put(ConfigConstants.RECV_TYPE_VIDEO, new WxRecvVideoMsgParser());
	}
	
	public static WxRecvMsg parse(InputStream in) throws JDOMException, IOException {
		Document dom = new SAXBuilder().build(in);
		Element msgType = dom.getRootElement().getChild("MsgType");
		if(null != msgType) {
			String txt = msgType.getText().toLowerCase();
			WxRecvMsgParser parser = recvParserMap.get(txt);
			if(null != parser) {
				return parser.parser(dom);
			} else {
				System.out.println(txt);
			}
		}
		return null;
	}
	
	public static Document parse(WxSendMsg msg) throws JDOMException {
		return msg.toDocument();
	}
}
