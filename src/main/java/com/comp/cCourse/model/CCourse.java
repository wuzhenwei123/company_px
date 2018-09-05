package com.comp.cCourse.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 视频表	
 * @author	wzw
 * @time	2018-04-03 09:47:08
 */
public class CCourse extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Long id;
	/** 名称 **/
	private String name;
	/** 视频播放地址 **/
	private String videoPlay;
	/** 转码状态 0 转码中 1转码完成 **/
	private Integer videoState;
	/** 视频id **/
	private String videoFileId;
	/** 封面地址 **/
	private String imgUrl;
	/** 服务器存放地址 **/
	private String videoUrl;
	/** 发布状态 0 未发布 1 已发布 **/
	private Integer state;
	/** 上传时间 **/
	private Date createTime;
	/** 描述 **/
	private String info;
		
	/**
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 名称
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 视频播放地址
	 * @return videoPlay
	 */
	public String getVideoPlay() {
		return videoPlay;
	}
	/**
	 * 视频播放地址
	 */
	public void setVideoPlay(String videoPlay) {
		this.videoPlay = videoPlay;
	}
	/**
	 * 转码状态 0 转码中 1转码完成
	 * @return videoState
	 */
	public Integer getVideoState() {
		return videoState;
	}
	/**
	 * 转码状态 0 转码中 1转码完成
	 */
	public void setVideoState(Integer videoState) {
		this.videoState = videoState;
	}
	/**
	 * 视频id
	 * @return videoFileId
	 */
	public String getVideoFileId() {
		return videoFileId;
	}
	/**
	 * 视频id
	 */
	public void setVideoFileId(String videoFileId) {
		this.videoFileId = videoFileId;
	}
	/**
	 * 封面地址
	 * @return imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * 封面地址
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 服务器存放地址
	 * @return videoUrl
	 */
	public String getVideoUrl() {
		return videoUrl;
	}
	/**
	 * 服务器存放地址
	 */
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	/**
	 * 发布状态 0 未发布 1 已发布
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 发布状态 0 未发布 1 已发布
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 上传时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 上传时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 描述
	 * @return info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * 描述
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}