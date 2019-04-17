package com.dliberty.liberty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dliberty.liberty.entity.WeixinUser;
import com.dliberty.liberty.lang.data.StringUtils;
import com.dliberty.liberty.service.WeixinUserService;

@Controller
public class BaseController {
	
	@Autowired
	private WeixinUserService weixinUserService;

	
	public String getOpenId(String session) {
		if (StringUtils.isEmpty(session)) {
			return null;
		}
		return session.split(",")[0];
	}
	
	public Integer getUserId(String session) {
		if (StringUtils.isEmpty(session)) {
			return null;
		}
		String openId = getOpenId(session);
		WeixinUser user = weixinUserService.selectByOpenId(openId);
		if (user != null) {
			return user.getId();
		} else {
			//如果用户信息为空，将用户信息维护进去
			user = weixinUserService.syncUserBase(openId);
			return user.getId();
		}
	}
}
