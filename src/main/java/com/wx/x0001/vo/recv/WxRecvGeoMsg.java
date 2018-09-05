package com.wx.x0001.vo.recv;


public class WxRecvGeoMsg extends WxRecvMsg {
	private int scale;
	private String label;
	
	public WxRecvGeoMsg(WxRecvMsg msg,int scale,String label) {
		super(msg);
		this.scale = scale;
		this.label = label;
	}
	
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
