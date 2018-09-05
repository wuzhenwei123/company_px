package com.wx.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;































import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;






























import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.controller.BaseController;
import com.base.utils.ConfigConstants;
import com.base.utils.GetOpenIdUntil;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.comp.cPaper.model.CPaper;
import com.comp.cPaper.service.CPaperService;
import com.comp.cPaperDepartment.model.CPaperDepartment;
import com.comp.cPaperDepartment.service.CPaperDepartmentService;
import com.comp.cPaperQuestion.model.CPaperQuestion;
import com.comp.cPaperQuestion.service.CPaperQuestionService;
import com.comp.cUserPaper.model.CUserPaper;
import com.comp.cUserPaper.service.CUserPaperService;
import com.comp.cUserPaperQuestion.model.CUserPaperQuestion;
import com.comp.cUserPaperQuestion.service.CUserPaperQuestionService;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.sys.manageAdminUser.service.ManageAdminUserService;
import com.wx.service.WeiXinService;
import com.wx.service.WxTemplateMsg;
import com.wx.utils.WxMenuUtils;
import com.wx.x0001.WeiXin;
import com.wx.x0001.vo.recv.WxRecvEventMsg;
import com.wx.x0001.vo.recv.WxRecvMsg;
import com.wx.x0001.vo.recv.WxRecvTextMsg;
import com.wx.x0001.vo.send.WxSendMsg;
import com.wx.x0001.vo.send.WxSendNewsMsg;
import com.wx.x0001.vo.send.WxSendTextMsg;

@Controller
@RequestMapping("/weixin")
public class WeiXinController extends BaseController{
	
	Logger log = Logger.getLogger(WeiXinController.class);
	
	public static Map<String,String> maporderNo = new HashMap<String,String>();
	
	@Autowired
	private WeiXinService weiXinService;
	@Autowired
	private ManageAdminUserService manageadminuserService = null;// 用户
	@Autowired
	private WxTemplateMsg wxTemplateMsg = null;
	@Autowired
	private CPaperDepartmentService cPaperDepartmentService = null;
	@Autowired
	private CPaperQuestionService cPaperQuestionService = null;
	@Autowired
	private CPaperService cPaperService = null;
	@Autowired
	private CUserPaperQuestionService cUserPaperQuestionService = null;
	@Autowired
	private CUserPaperService cUserPaperService = null;
	
	
	@RequestMapping("/access")
	public String weixin(HttpServletResponse response,
			HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String appid = request.getParameter("appid");
		// 验证接口配置消息的时候会调用
		if (null != timestamp && null != nonce && null != echostr
				&& null != signature) {
			if (WeiXin.access(ConfigConstants.TOKEN, signature, timestamp, nonce)) {
				try {
					PrintWriter writer = response.getWriter();
					writer.print(echostr);
					writer.flush();
					writer.close();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("--------code------->"+code);
		if (StringUtils.isNotBlank(code)) {
			try {
				String access_token = WxMenuUtils.getAccessCode(ConfigConstants.APPID, ConfigConstants.APPSECRET, code);
				JSONObject json = JSONObject.parseObject(access_token);
				String openId = json.getString("openid");
				System.out.println("------------openId--------------"+openId);
				request.setAttribute("openId", openId);
				String states[] = state.split("_");
				//判断是否绑定
				boolean b = weiXinService.checkBind(openId,request);
				if(b){
					if("BD".equals(states[0])){//绑定
						return  "redirect:/weixin/toUnBind";
					}else if("DC".equals(states[0])){//在线测评
						return  "redirect:/weixin/toMyPaperList";
					}else if("myorder".equals(states[0])){//我的订单
						return  "redirect:/weixin/toMyOrder?openId="+ openId;
					}else if("card".equals(states[0])){//银行卡
						return  "redirect:/weixin/myCardList?openId="+ openId;
					}
				}else{
					super.getJsticket(request);
					return  "/wx/bind";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null != timestamp && null != nonce && null != signature) {
			if (WeiXin.access(ConfigConstants.TOKEN, signature, timestamp, nonce)) {// 验证消息的真实性
				try {
					WxRecvMsg msg = WeiXin.recv(request.getInputStream());
					WxSendMsg sendMsg = WeiXin.builderSendByRecv(msg);
					String openId = msg.getFromUser();
					if (msg instanceof WxRecvEventMsg) {
						WxRecvEventMsg m = (WxRecvEventMsg) msg;
						String event = m.getEvent();
						if ("subscribe".equals(event)) {
							if(StringUtils.isNotBlank(m.getTicket())){
								if(m.getEventKey().indexOf("Friend_")!=-1){
									String content = "恭喜您已经成为生活购电业务员，请输入【合作】，完善资料";
									sendMsg = new WxSendTextMsg(sendMsg, content);
									//将业二级务拓展员和拓展员绑定
								}else{
									String content = "请点击开通服务：<a href='"+ConfigConstants.URL_PATH+"/weixin/toReg?openId="+openId+"'>开通服务</a>";
									sendMsg = new WxSendTextMsg(sendMsg, content);
									String ticket = m.getTicket();
									//将推荐人和被推荐人绑定。
								}
							}else{
								String content = "欢迎关注";
								sendMsg = new WxSendTextMsg(sendMsg, content);
							}
						}else if("unsubscribe".equals(event)){//取消关注
							//解除绑定
//							weiXinService.unBindWx(openId);
						}else if("CLICK".equals(event)){
							System.out.println("---------------"+m.getEventKey());
							if("BM".equals(m.getEventKey())){
								String content = "即将开通此服务，敬请期待。";
								sendMsg = new WxSendTextMsg(sendMsg, content);
							}else if("SC".equals(m.getEventKey())){
								String content = "即将开通此服务，敬请期待。";
								sendMsg = new WxSendTextMsg(sendMsg, content);
							}
						}else if("SCAN".equals(event)){
							if(m.getEventKey().indexOf("Friend_")!=-1){
								String content = "恭喜您已经成为生活购电业务员，请输入【合作】，完善资料";
								sendMsg = new WxSendTextMsg(sendMsg, content);
								//将业二级务拓展员和拓展员绑定
							}else{
								String ticket = m.getTicket();
								String content = "请点击开通服务：<a href='"+ConfigConstants.URL_PATH+"/weixin/toReg?openId="+openId+"'>开通服务</a>";
								sendMsg = new WxSendTextMsg(sendMsg, content);
								//将业务拓展员和注册的商户绑定
							}
							
						}else if("LOCATION".equals(event)){
//							String str = weiXinService.getBaiDuLocationXY(m.getLatitude(),m.getLongitude());
						}
						WeiXin.send(sendMsg, response.getOutputStream());
					}else if(msg instanceof WxRecvTextMsg){
						WxRecvTextMsg m = (WxRecvTextMsg) msg;
						String content =  m.getContent();
						if(content.contains("合作")){//机构验证
							sendMsg = new WxSendNewsMsg(sendMsg).addItem(
									"合作",
									"感谢您关注微信公众服务号，点击此消息进入",
									ConfigConstants.URL_PATH + "/images/subscribe.png",
									ConfigConstants.URL_PATH + "/weixin/dbIndex?openId=" + openId);
							WeiXin.send(sendMsg, response.getOutputStream());
						}
					}
				} catch (Exception e) {
					log.info(e.getLocalizedMessage());
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 测试
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		String openId = RequestHandler.getString(request, "openId");
		return  "redirect:/weixin/toPay";
	}
	/**
	 * 测试
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUnBind", method = RequestMethod.GET)
	public String toUnBind(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		return  "/wx/unBind";
	}
	/**
	 * 在线测评
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMyPaperList", method = RequestMethod.GET)
	public String toMyPaperList(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		Integer pageNo = RequestHandler.getPageNo(request, "pageNo");
		try{
			ManageAdminUser manageAdminUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			//获取部门下试卷列表
			int offset = RequestHandler.getFromByPage(pageNo, 10);
			CPaperDepartment cPaperDepartment = new CPaperDepartment();
			cPaperDepartment.setState(1);
			cPaperDepartment.setDepartmentId(manageAdminUser.getDepartmentId());
			cPaperDepartment.setLimit(10);
			cPaperDepartment.setOffset(offset);
			cPaperDepartment.setUserId(manageAdminUser.getAdminId());
			cPaperDepartment.setOrder("desc");
			cPaperDepartment.setSort("createTime");
			List<CPaperDepartment> list = cPaperDepartmentService.getCPaperDepartmentListByPage(cPaperDepartment);
			
			model.addAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/paperList";
	}
	
	/**
	 * 在线测评
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myPaperListJson", method = RequestMethod.GET)
	public String myPaperListJson(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		Integer pageNo = RequestHandler.getPageNo(request, "pageNo");
		JSONObject json = new JSONObject();
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			ManageAdminUser manageAdminUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			//获取部门下试卷列表
			int offset = RequestHandler.getFromByPage(pageNo, 10);
			CPaperDepartment cPaperDepartment = new CPaperDepartment();
			cPaperDepartment.setState(1);
			cPaperDepartment.setDepartmentId(manageAdminUser.getDepartmentId());
			cPaperDepartment.setLimit(10);
			cPaperDepartment.setOffset(offset);
			cPaperDepartment.setUserId(manageAdminUser.getAdminId());
			cPaperDepartment.setOrder("desc");
			cPaperDepartment.setSort("createTime");
			List<CPaperDepartment> list = cPaperDepartmentService.getCPaperDepartmentListByPage(cPaperDepartment);
			if(list!=null&&list.size()>0){
				JSONArray array = new JSONArray();
				for(CPaperDepartment pd:list){
					JSONObject subjson = new JSONObject();
					subjson.put("id", pd.getPaperId());
					subjson.put("answerStatus", pd.getUserAnserPaperId()!=null?true:false);
					subjson.put("name", pd.getTitle());
					subjson.put("createDate", sf.format(pd.getCreateTime()));
					array.add(subjson);
				}
				json.put("items", array);
				json.put("message", "ok");
			}else{
				json.put("message", "end");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json.toString());
		return  null;
	}
	
	
	/**
	 * 验证绑定
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bindUser", method = RequestMethod.POST)
	public String bindUser(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		String openId = RequestHandler.getString(request, "openId");
		String phone = RequestHandler.getString(request, "userName");
		String pwd = RequestHandler.getString(request, "password");
		try{
			if(!StringUtils.isNotBlank(phone)){
				writeSuccessMsg("-1", null, response);
				return null;
			}else if(!StringUtils.isNotBlank(pwd)){
				writeSuccessMsg("-2", null, response);
				return null;
			}else if(!StringUtils.isNotBlank(openId)){
				writeSuccessMsg("-3", null, response);
				return null;
			}
			ManageAdminUser manageAdminUser = new ManageAdminUser();
			manageAdminUser.setAdminName(phone);
			manageAdminUser.setPasswd(MD5.getMD5ofStr(pwd));
			int count = manageadminuserService.getManageAdminUserListCount(manageAdminUser);
			if(count ==0){
				writeSuccessMsg("-4", null, response);
				return null;
			}else if(count==1){
				manageAdminUser = manageadminuserService.getManageAdminUser(manageAdminUser);
				if(manageAdminUser.getState()==1){
					if(StringUtils.isNotBlank(manageAdminUser.getOpenId())){
						writeSuccessMsg("-7", null, response);
					}else{
						manageAdminUser.setOpenId(openId);
						weiXinService.bindUser(manageAdminUser,request);
						writeSuccessMsg("1", null, response);
					}
				}else{
					writeSuccessMsg("-6", null, response);
				}
			}else{
				writeSuccessMsg("-5", null, response);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  null;
	}
	
	
	/**
	 * 解绑
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/unBind")
	public String unBind(HttpServletRequest request, HttpServletResponse response, Model model){
		ManageAdminUser adminUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			manageadminuserService.unBindWx(adminUser);
			request.getSession().removeAttribute(SessionName.ADMIN_USER);
			request.getSession().removeAttribute(SessionName.ADMIN_USER_ID);
			request.getSession().removeAttribute(SessionName.ADMIN_USER_NAME);
			writeSuccessMsg("success", null, response);
		}catch(Exception e){
			writeSuccessMsg("error", null, response);
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 保存图片
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/saveImageToDisk", method = RequestMethod.GET)
	public String saveImageToDisk(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		String mediaId = RequestHandler.getString(request, "mediaId");
		String accessToken = weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET);
		String saveFilePath = ConfigConstants.UPLOAD_FILE_ROOT;
		String fileToday = DateFormatUtils.format(new Date(), "yyyy/MM/dd");
		File file = new File(saveFilePath + File.separator + fileToday + File.separator);
		if(!file.exists()){
			file.mkdirs();
		}
		JSONObject json = new JSONObject();
		try{
			weiXinService.saveImageToDisk(accessToken, mediaId, mediaId, saveFilePath + File.separator + fileToday + File.separator);
			json.put("c", 0);
			json.put("d", File.separator + fileToday + File.separator + mediaId + ".jpg");
			json.put("m", "上传成功");
		}catch(Exception e){
			json.put("c", -1);
			json.put("d", new JSONObject());
			json.put("m", "上传失败，系统异常");
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control","no-cache");
        response.getWriter().write(json.toString());
		return  null;
	}
	
	
	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downFile", method = RequestMethod.GET)
	public String downFile(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		String name = RequestHandler.getString(request, "name");
		String link = RequestHandler.getString(request, "link");
		model.addAttribute("name", name);
		model.addAttribute("link", link);
		return  "/common/excelup";
	}

	@RequestMapping("/backUri")
	public String backUri(HttpServletResponse response,HttpServletRequest request) {
		String weixinCode = RequestHandler.getString(request, "code");
		String state = RequestHandler.getString(request, "state");
		System.out.println("----------------state------------------"+state);
		try{
			JSONObject json = GetOpenIdUntil.getOpenId(ConfigConstants.APPID,weixinCode,ConfigConstants.APPSECRET);
			System.out.println("----------------json------------------"+json.toString());
			if(StringUtils.isNotBlank(json.getString("openid"))){
				String[] states = state.split(",");
				if("hezuo".equals(states[0])){//银行卡
					return  "redirect:/weixin/dbIndex?openId="+ json.getString("openid");
				}
			}else{
				return  "/wx/tip";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取客户openid
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getToken")
	public String getToken(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String state = RequestHandler.getString(request, "state");
		model.addAttribute("state", state);
		return "/common/index";
	}
	
	/**
	 * 进入答题页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAnswerPaper", method = RequestMethod.GET)
	public String toAnswerPaper(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		try{
			String paperId = RequestHandler.getString(request, "paperId");
			CPaperQuestion cPaperQuestion = new CPaperQuestion();
			cPaperQuestion.setPaperId(paperId);
			cPaperQuestion.setOrder("asc");
			cPaperQuestion.setSort("sortId");
			List<CPaperQuestion> listQuestion = cPaperQuestionService.getCPaperQuestionListBySortId(cPaperQuestion);
			model.addAttribute("listQuestion", listQuestion);
			CPaper cPaper = cPaperService.getCPaperById(paperId);
			model.addAttribute("cPaper", cPaper);
			long from = new Date().getTime();  
			long to = cPaper.getEndTime().getTime();  
			int minutes = (int) ((to - from)/(1000 * 60));  
			model.addAttribute("minutes", minutes);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/answerPaper";
	}
	
	/**
	 * 进入答题页面（已作答）
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAnswerPapered", method = RequestMethod.GET)
	public String toAnswerPapered(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		ManageAdminUser adminUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			String paperId = RequestHandler.getString(request, "paperId");
			CPaperQuestion cPaperQuestion = new CPaperQuestion();
			cPaperQuestion.setPaperId(paperId);
			cPaperQuestion.setOrder("asc");
			cPaperQuestion.setSort("sortId");
			List<CPaperQuestion> listQuestion = cPaperQuestionService.getCPaperQuestionListBySortId(cPaperQuestion);
			
			
			
			CUserPaperQuestion cUserPaperQuestion = new CUserPaperQuestion();
			cUserPaperQuestion.setUserId(adminUser.getAdminId());
			cUserPaperQuestion.setPaperId(paperId);
			List<CUserPaperQuestion> listUserPaper = cUserPaperQuestionService.getCUserPaperQuestionList(cUserPaperQuestion);
			if(listQuestion!=null&&listQuestion.size()>0){
				for(CPaperQuestion pq:listQuestion){
					if(listUserPaper!=null&&listUserPaper.size()>0){
						for(CUserPaperQuestion upq:listUserPaper){
							if(pq.getQuestionId().equals(upq.getQuestionId())){
								pq.setUserAnswer(upq.getAnswer());
								break;
							}
						}
					}
				}
			}
			model.addAttribute("listQuestion", listQuestion);
			CPaper cPaper = cPaperService.getCPaperById(paperId);
			model.addAttribute("cPaper", cPaper);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/answerPapered";
	}
	
	/**
	 * 答题
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/submitAnswerPaper", method = RequestMethod.POST)
	public String submitAnswerPaper(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		try{
			String paperId = RequestHandler.getString(request, "paperId");
			Integer adminId = RequestHandler.getInteger(request, "adminId");
			CPaperQuestion cPaperQuestion = new CPaperQuestion();
			cPaperQuestion.setPaperId(paperId);
			cPaperQuestion.setOrder("asc");
			cPaperQuestion.setSort("sortId");
			List<CPaperQuestion> listQuestion = cPaperQuestionService.getCPaperQuestionListBySortId(cPaperQuestion);
			int score = 0;
			for(CPaperQuestion pq:listQuestion){
				CUserPaperQuestion cUserPaperQuestion = new CUserPaperQuestion();
				cUserPaperQuestion.setUserId(adminId);
				cUserPaperQuestion.setPaperId(paperId);
				cUserPaperQuestion.setRightAnswer(pq.getRightAnswer());
				cUserPaperQuestion.setQuestionId(pq.getQuestionId());
				String answer = null;
				if(pq.getStyle().intValue()==2){
					String[] answers = request.getParameterValues(pq.getQuestionId());
					if(answers!=null){
						answer = "";
						Arrays.sort(answers);
						for(String str:answers){
							answer = answer + str;
						}
					}
				}else{
					answer = RequestHandler.getString(request, pq.getQuestionId());
				}
				cUserPaperQuestion.setAnswer(answer);
				if(StringUtils.isNotBlank(answer)&&answer.equals(pq.getRightAnswer())){
					cUserPaperQuestion.setIsRight(1);
					cUserPaperQuestion.setScore(pq.getScore());
					score = score + pq.getScore();
				}
				cUserPaperQuestionService.insertCUserPaperQuestion(cUserPaperQuestion);
			}
			CUserPaper cUserPaper = new CUserPaper();
			cUserPaper.setCreateTime(new Date());
			cUserPaper.setPaperId(paperId);
			cUserPaper.setUserId(adminId);
			cUserPaper.setScore(score);
			cUserPaperService.insertCUserPaper(cUserPaper);
			writeSuccessMsg("OK",null, response);
		}catch(Exception e){
			writeErrorMsg("error", null, response);
			e.printStackTrace();
		}
		return  null;
	}
	
}
