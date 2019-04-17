package com.dliberty.liberty.service;

public interface RedisClient {

	boolean set(String key, String value,long expire); 
	
	String get(String key);
	
	Long del(String key);
	
	boolean setNx(String key, String value,long expire); 
}
