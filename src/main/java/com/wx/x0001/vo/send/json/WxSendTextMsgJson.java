package com.wx.x0001.vo.send.json;



public class WxSendTextMsgJson{
	
	// FuncFlag 位0x0001被标志时，星标刚收到的消息。
	private boolean star;
	
	private String touser;
	
	private String msgType;
	
	private String content;
	
	public WxSendTextMsgJson(){
		
	}
	
	public WxSendTextMsgJson(boolean star,String touser,String msgType,String content) {
		this.star = star;
		this.touser = touser;
		this.msgType = msgType;
		this.content = content;
	}
	
	public boolean isStar() {
		return star;
	}
	
	public void setStar(boolean star) {
		this.star = star;
	}
	
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
/*	public JSONObject getJSON(){
		JSONObject jso = new JSONObject();
		jso.put("touser",this.touser);
		jso.put("msgtype",this.msgType);
		JSONObject json = new JSONObject();
		json.put("content",this.content);
		jso.put("text", json);
		return jso;
	}*/
}
