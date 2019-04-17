package com.dliberty.liberty.weixin.service;


import java.util.Map;

public interface WeixinTemplateMessageService {

	/**
	 * 发送模板消息
	 * @param openId  接收者openId
	 * @param templateId 消息id
	 * @param url 查看详情对应链接
	 * @param appUrl 所跳转的小程序链接
	 * @param data 模板数据
	 */
	boolean sendTemplateMessage(String openId,String templateId,String page,String formId,Map<String,Map<String,String>> data);
}
