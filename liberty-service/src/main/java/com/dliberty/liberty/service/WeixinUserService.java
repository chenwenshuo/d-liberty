package com.dliberty.liberty.service;

import java.util.List;

import com.dliberty.liberty.entity.WeixinUser;
import com.dliberty.liberty.vo.JsonBean;

/**
 * 微信用户相关信息
 * 
 * @author LG
 *
 */
public interface WeixinUserService {

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	List<WeixinUser> selectAll();
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	WeixinUser selectByUserId(Integer userId);

	/**
	 * 根据openId查找用户信息
	 * 
	 * @param openId
	 * @return
	 */
	WeixinUser selectByOpenId(String openId);

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 */
	WeixinUser saveUser(WeixinUser user);

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	WeixinUser updateUser(WeixinUser user);

	/**
	 * 同步用户信息
	 * 
	 * @param session
	 * @param userInfo
	 */
	void syncUser(String session, String userInfo);

	/**
	 * 同步用户信息
	 * 
	 * @param session
	 * @param userInfo
	 */
	WeixinUser syncUserBase(String openId);
	
	/**
	 * 查詢有邮箱的账户
	 * @return
	 */
	List<WeixinUser> selectEmail();
	
	/**
	 * 修改用户邮箱
	 * @param email
	 */
	JsonBean modifyEmail(Integer userId,String email);
	
	/**
	 * 修改提醒时间
	 * @param userId
	 * @param remindHour
	 * @param remindMinute
	 * @return
	 */
	JsonBean modifyRemindTime(Integer userId,Integer remindHour,Integer remindMinute);
	
	/**
	 * 根据时间查询需要通知的用户
	 * @param remindHour
	 * @param remindMinute
	 * @return
	 */
	List<WeixinUser> selectRemind(Integer remindHour,Integer remindMinute);
}
