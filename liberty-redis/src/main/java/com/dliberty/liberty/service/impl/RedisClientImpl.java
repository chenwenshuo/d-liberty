package com.dliberty.liberty.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.dliberty.liberty.service.RedisClient;

@Service
public class RedisClientImpl implements RedisClient {

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	public boolean set(String key, String value,long expire) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				connection.set(serializer.serialize(key), serializer.serialize(value));
				connection.expire(serializer.serialize(key), expire);
				return true;
			}
		});
		return result;
	}

	@Override
	public String get(String key) {
		
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] bs = connection.get(serializer.serialize(key));
				return serializer.deserialize(bs);
			}
		});
		return result;
	}

	@Override
	public Long del(String key) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				Long del = connection.del(serializer.serialize(key));
				return del;
			}
		});
		return result;
	}

	@Override
	public boolean setNx(String key, String value, long expire) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				Boolean setNX = connection.setNX(serializer.serialize(key), serializer.serialize(value));
				connection.expire(serializer.serialize(key), expire);
				return setNX;
			}
		});
		return result;
	}

}
