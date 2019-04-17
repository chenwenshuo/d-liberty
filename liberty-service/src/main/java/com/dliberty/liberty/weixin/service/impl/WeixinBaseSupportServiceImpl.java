package com.dliberty.liberty.weixin.service.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dliberty.liberty.weixin.service.WeixinBaseSupportService;
import com.dliberty.liberty.weixin.util.WeixinHttpClientUtils;

@Service("weixinBaseSupportService")
public class WeixinBaseSupportServiceImpl implements WeixinBaseSupportService {

	private static final Logger logger = LoggerFactory.getLogger(WeixinBaseSupportServiceImpl.class);
	private static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";// 获取access
	
	@Value("${app.weixin.appid}")
	private String APP_ID;
	@Value("${app.weixin.secret}")
	private String SECRET;
	
	private static final String ACCESS_TOKEN_CACHE_KEY = "liberty_access_token_cache_jizhang";
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	/**
	 * 获取微信access_token
	 * 先从缓存里获取
	 */
	public String accessToken() {
		String accessToken = StringUtils.EMPTY;
		try {
			ValueOperations<String, String> ops = redisTemplate.opsForValue();
			accessToken = ops.get(ACCESS_TOKEN_CACHE_KEY);
			if (StringUtils.isNotEmpty(accessToken)) {
				return accessToken;
			}
			String turl = String.format("%s?grant_type=client_credential&appid=%s&secret=%s", GET_TOKEN_URL,APP_ID, SECRET);
			String responseContent = WeixinHttpClientUtils.responseGet(turl); // 响应内容
			JSONObject json = JSONObject.parseObject(responseContent);
			if (json != null) {
				if (StringUtils.isNotEmpty(json.getString("access_token"))) {
					accessToken = json.getString("access_token");
					ops.set(ACCESS_TOKEN_CACHE_KEY, accessToken, 5400, TimeUnit.SECONDS);
				} else {
					logger.info("获取微信access_token失败{}",responseContent);
				}
			} else {
				logger.info("获取微信access_token失败,返回为null");
			}
			logger.info("获取微信access_token={}",accessToken);
			System.out.println("获取微信access_token={}"+accessToken);
		} catch(Exception e) {
			logger.warn("获取微信access_token异常{}",e.getCause());
			System.out.println(e.getMessage());
		}
		return accessToken;
	}


}
