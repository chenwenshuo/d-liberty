package com.dliberty.liberty.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dliberty.liberty.entity.WeixinUser;
import com.dliberty.liberty.lang.data.StringUtils;
import com.dliberty.liberty.mapper.WeixinUserMapper;
import com.dliberty.liberty.service.AccountCategoryService;
import com.dliberty.liberty.service.WeixinUserService;
import com.dliberty.liberty.utils.RegexUtil;
import com.dliberty.liberty.vo.JsonBean;
import com.dliberty.liberty.vo.UserInfo;

@Service("weixinUserService")
@Transactional
public class WeixinUserServiceImpl implements WeixinUserService {
	
	@Autowired
	private WeixinUserMapper weixinUserMapper;
	@Autowired
	private AccountCategoryService accountCategoryService;
	
	@Override
	public List<WeixinUser> selectAll() {
		return weixinUserMapper.selectAll();
	}
	
	@Override
	public WeixinUser selectByUserId(Integer userId){
		return weixinUserMapper.selectByPrimaryKey(userId);
	}

	@Override
	public WeixinUser selectByOpenId(String openId) {
		if (StringUtils.isEmpty(openId)) {
			return null;
		}
		return weixinUserMapper.selectByOpenId(openId);
	}

	@Override
	public WeixinUser saveUser(WeixinUser user) {
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		weixinUserMapper.insert(user);
		return user;
	}

	@Override
	public WeixinUser updateUser(WeixinUser user) {
		user.setUpdateTime(new Date());
		weixinUserMapper.updateByPrimaryKey(user);
		return user;
	}

	@Override
	public void syncUser(String session, String userInfo) {
		if (StringUtils.isEmpty(session)) {
			return;
		}
		UserInfo info  = null;
		if (StringUtils.isNotEmpty(userInfo)) {
			info	= JSONObject.parseObject(userInfo, UserInfo.class);
		}
		
		String openId = session.split(",")[0];
		WeixinUser user = selectByOpenId(openId);
		boolean isSave = false;
		if (user == null) {
			user = new WeixinUser();
			isSave = true;
		}
		user.setOpenId(openId);
		if (info != null) {
			user.setUserName(info.getNickName());
			user.setHeadImg(info.getAvatarUrl());
		}
		if (isSave) {
			saveUser(user);
			//初始化类别
			accountCategoryService.initUserCategory(user.getId());
		} else {
			updateUser(user);
		}
	}

	@Override
	public WeixinUser syncUserBase(String openId) {
		if (StringUtils.isEmpty(openId)) {
			return null;
		}
		WeixinUser user = selectByOpenId(openId);
		if(user == null) {
			user = new WeixinUser();
			user.setOpenId(openId);
			saveUser(user);
			//初始化类别
			accountCategoryService.initUserCategory(user.getId());
		}
		return user;
	}

	@Override
	public List<WeixinUser> selectEmail() {
		return weixinUserMapper.selectEmail();
	}

	@Override
	public JsonBean modifyEmail(Integer userId,String email) {
		JsonBean jsonBean = new JsonBean();
		if (StringUtils.isEmpty(email)) {
			jsonBean.setCode("1");
			jsonBean.setMessage("邮箱信息必填");
			return jsonBean;
		}
		if (!RegexUtil.checkEmail(email)) {
			jsonBean.setCode("1");
			jsonBean.setMessage("邮箱格式不正确");
			return jsonBean;
		}
		WeixinUser user = weixinUserMapper.selectByPrimaryKey(userId);
		if (user != null) {
			user.setEmail(email);
			updateUser(user);
		}
		
		return jsonBean;
	}

	@Override
	public JsonBean modifyRemindTime(Integer userId, Integer remindHour, Integer remindMinute) {
		JsonBean jsonBean = new JsonBean();
		if (remindHour == null || remindMinute == null) {
			jsonBean.setCode("1");
			jsonBean.setMessage("提醒时间不能为空");
			return jsonBean;
		}
		WeixinUser user = weixinUserMapper.selectByPrimaryKey(userId);
		if (user != null) {
			user.setRemindHour(remindHour);
			user.setRemindMinute(remindMinute);
			
			updateUser(user);
		}
		
		return jsonBean;
	}

	@Override
	public List<WeixinUser> selectRemind(Integer remindHour, Integer remindMinute) {
		return weixinUserMapper.selectRemind(remindHour, remindMinute);
	}
	

}
