package com.wx.x0001.vo.recv;

public class WxRecvEventMsg extends WxRecvMsg {
	
	private String event;
	private String eventKey;
	private String Ticket;
	
	public WxRecvEventMsg(WxRecvMsg msg,String event,String eventKey,String Ticket) {
		super(msg);
		this.event = event;
		this.eventKey = eventKey;
		this.Ticket = Ticket;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		this.Ticket = ticket;
	}
}
