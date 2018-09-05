package com.sys.manageAdminUser.model;

import java.util.Date;

import com.base.model.BaseModel;
/**
 * 	
 * @author	keeny
 * @time	2017-03-23 14:16:40
 */
public class ManageAdminUser extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5516677085830533172L;
	/**  **/
	private Integer adminId;
	/** 用户名 **/
	private String adminName;
	/** 昵称 **/
	private String nickName;
	/** 密码 **/
	private String passwd;
	/** 真实姓名 **/
	private String realName;
	/** 手机 **/
	private String mobile;
	/** 电话 **/
	private String phone;
	/** 最后登陆日期 **/
	private Date lastLogin;
	/** 登陆IP **/
	private String loginIP;
	/** 密码修改日期 **/
	private Date pwdModifyTime;
	/** 状态1：正常，0：禁用 **/
	private Integer state;
	/** 注册时间 **/
	private Date createTime;
	/** 创建人 **/
	private Integer createrId;
	/** 头像 **/
	private String headImg;
	/** 角色ID **/
	private Integer role_id;
	/** 性别1男0女 **/
	private Integer sex;
	private String createAdminName;//创建人名称
	private String roleName;//角色名称
	
	/** 微信openId **/
	private String openId;
	
	private Date startTime;//用于查询
	private Date endTime;//用于查询
	
	private String departmentId;
	private String departmentIds;
	private String jobNumber;
	private String departmentName;
	private String departmentParentId;
	private String sexStr;
	
	
	
	
	
	public String getSexStr() {
		return sexStr;
	}
	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}
	public String getDepartmentParentId() {
		return departmentParentId;
	}
	public void setDepartmentParentId(String departmentParentId) {
		this.departmentParentId = departmentParentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentIds() {
		return departmentIds;
	}
	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getCreateAdminName() {
		return createAdminName;
	}
	public void setCreateAdminName(String createAdminName) {
		this.createAdminName = createAdminName;
	}
	/**
	 * 
	 * @return adminId
	 */
	public Integer getAdminId() {
		return adminId;
	}
	/**
	 * 
	 */
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	/**
	 * 用户名
	 * @return adminName
	 */
	public String getAdminName() {
		return adminName;
	}
	/**
	 * 用户名
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	/**
	 * 昵称
	 * @return nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * 昵称
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * 密码
	 * @return passwd
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * 密码
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	/**
	 * 真实姓名
	 * @return realName
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 手机
	 * @return mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 电话
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 最后登陆日期
	 * @return lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}
	/**
	 * 最后登陆日期
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	/**
	 * 登陆IP
	 * @return loginIP
	 */
	public String getLoginIP() {
		return loginIP;
	}
	/**
	 * 登陆IP
	 */
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	/**
	 * 密码修改日期
	 * @return pwdModifyTime
	 */
	public Date getPwdModifyTime() {
		return pwdModifyTime;
	}
	/**
	 * 密码修改日期
	 */
	public void setPwdModifyTime(Date pwdModifyTime) {
		this.pwdModifyTime = pwdModifyTime;
	}
	/**
	 * 状态1：正常，0：禁用
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 状态1：正常，0：禁用
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 注册时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 注册时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 创建人
	 * @return createrId
	 */
	public Integer getCreaterId() {
		return createrId;
	}
	/**
	 * 创建人
	 */
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	/**
	 * 头像
	 * @return headImg
	 */
	public String getHeadImg() {
		return headImg;
	}
	/**
	 * 头像
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	/**
	 * 角色ID
	 * @return role_id
	 */
	public Integer getRole_id() {
		return role_id;
	}
	/**
	 * 角色ID
	 */
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	/**
	 * 性别1男0女
	 * @return sex
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 性别1男0女
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
}