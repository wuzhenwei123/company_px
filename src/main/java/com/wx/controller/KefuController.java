package com.wx.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.base.controller.BaseController;
import com.base.utils.ConfigConstants;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.base.utils.https.HttpUtils;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.sys.manageAdminUser.service.ManageAdminUserService;
import com.wx.service.KefuService;
import com.wx.service.WeiXinService;
import com.wx.utils.WxMenuUtils;
import com.wx.x0001.vo.send.WxSendTextMsg;

@Controller
@RequestMapping("/kefu")
public class KefuController extends BaseController{
	
	@Autowired
	private KefuService kefuService;
	@Autowired
	private WeiXinService weiXinService;
	@Autowired
	private ManageAdminUserService manageadminuserService = null;// 用户
	
	/**
	 * 打开客户会话
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/openKF")
	public void openKF(HttpServletRequest request, HttpServletResponse response, Model model){
		Object obj = request.getSession().getAttribute(SessionName.SESSION_OPENID);
		System.out.println("---------------------"+obj);
		try{
			if(obj!=null){
				boolean b = kefuService.CreateSession(obj.toString());
				String  str1 = "客服不在线";
				if(b){
					str1 = "您好！很高兴为您服务，有什么可以帮您的吗？";
				}
				String json_str = "{\"touser\":\""+obj.toString()+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+str1+"\"}}";
				System.out.println(json_str);
				WxMenuUtils.postMess(json_str, weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 添加客服
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addKF")
	public String addKF(HttpServletRequest request, HttpServletResponse response, Model model){
		String kf_account = RequestHandler.getString(request, "kf_account");
		String nickname = RequestHandler.getString(request, "nickname");
		JSONObject json = new JSONObject();
        json.put("kf_account", kf_account);
        json.put("nickname", nickname);
        try {
        	String res = kefuService.addKfAccount(json.toJSONString(), weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET));
			System.out.println("---------添加客服-------------"+res);
        } catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
