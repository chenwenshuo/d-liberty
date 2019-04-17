package com.dliberty.liberty.weixin.service.impl;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dliberty.liberty.weixin.service.WeixinBaseSupportService;
import com.dliberty.liberty.weixin.service.WeixinTemplateMessageService;
import com.dliberty.liberty.weixin.util.WeixinHttpClientUtils;
import com.dliberty.liberty.weixin.vo.TemplateMessageVo;

@Service
public class WeixinTemplateMessageServiceImpl implements WeixinTemplateMessageService {

	private static final Logger logger = LoggerFactory.getLogger(WeixinTemplateMessageServiceImpl.class);
	
	private String SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
	
	@Autowired
	private WeixinBaseSupportService weixinBaseSupportService;
	
	@Override
	public boolean sendTemplateMessage(String openId, String templateId,String page,String formId,
			Map<String, Map<String, String>> data) {
		
		TemplateMessageVo vo = new TemplateMessageVo();
		vo.setTouser(openId);
		vo.setTemplate_id(templateId);
		vo.setPage(page);
		vo.setForm_id(formId);
		vo.setData(data);

		String accessToken = weixinBaseSupportService.accessToken();
		String sendurl = String.format("%s?access_token=%s",SEND_URL,accessToken);
		
		String responseContent = WeixinHttpClientUtils.responsePost(sendurl, JSONObject.toJSONString(vo));
		
		JSONObject json = JSONObject.parseObject(responseContent);
		if (json != null) {
			String errmsg = json.getString("errmsg");
			logger.debug("模板消息发送返回结果{}",errmsg);
			if ("ok".equals(errmsg)) {
				logger.debug("消息发送成功");
				return true;
				
			}
		}
		return false;
	}

}
