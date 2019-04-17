package com.dliberty.liberty.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dliberty.liberty.service.NoticeUserService;
import com.dliberty.liberty.service.WeixinUserService;
import com.dliberty.liberty.utils.HttpClientUtils;
import com.dliberty.liberty.vo.JsonBean;

@RestController
@RequestMapping("/weixin")
public class WeixinController extends BaseController {

	@Autowired
	private WeixinUserService weixinUserService; 
	
	@Autowired
	private NoticeUserService noticeUserService;
	
	@Value("${app.weixin.appid}")
	private String appId;
	
	@Value("${app.weixin.secret}")
	private String secret;
	
	/**
	 * 获取小程序的session_id和openId
	 */
	@RequestMapping("/getSessionKeyOropenid")
	public Map<String,String> getSessionKeyOropenid(String code) {
		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
		String turl = String.format(requestUrl, appId, secret, code);
		String responseContent = HttpClientUtils.responseGet(turl);
		JSONObject json = JSONObject.parseObject(responseContent);
		Map<String,String> map = new HashMap<>();
		if (json != null) {
			String openId = json.getString("openid");
			//String unionid = json.getString("unionid");
			String sessionKey = json.getString("session_key");
			map.put("key_3rd_session", openId+","+sessionKey);
		}
		return map;
	}
	
	@RequestMapping("/syncUser")
	public void syncUser(String session,String userInfo) {
		weixinUserService.syncUser(session, userInfo);
	}
	
	@RequestMapping("/notice")
	public String notice() {
		//noticeUserService.noticeUserJob();
		noticeUserService.sendRecordForEmail();
		return "success";
	}
	
	@RequestMapping("/modifyEmail")
	public JsonBean modifyEmail(String session,String email) {
		try {
			Integer userId = getUserId(session);
			JsonBean json = weixinUserService.modifyEmail(userId, email);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			JsonBean json = new JsonBean();
			json.setCode("1");
			json.setMessage("操作失败");
			return json;
		}
		
	}
	
	@RequestMapping("/modifyRemindTime")
	public JsonBean modifyRemindTime(String session,Integer remindHour,Integer remindMinute) {
		try {
			Integer userId = getUserId(session);
			JsonBean json = weixinUserService.modifyRemindTime(userId, remindHour,remindMinute);
			return json;
		} catch (Exception e) {
			JsonBean json = new JsonBean();
			json.setCode("1");
			json.setMessage("操作失败");
			return json;
		}
		
	}
}
