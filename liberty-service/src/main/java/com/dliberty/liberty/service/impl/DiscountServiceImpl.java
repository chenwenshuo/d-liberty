package com.dliberty.liberty.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dliberty.liberty.entity.Discount;
import com.dliberty.liberty.mapper.DiscountMapper;
import com.dliberty.liberty.service.DiscountService;
import com.dliberty.liberty.service.RedisClient;

@Service("discountService")
@Transactional
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private DiscountMapper discountMapper;
	@Autowired
	private RedisClient redisClient;
	
	@Override
	public List<Discount> selectAll() {
		//redisClient.set("discountService", "discountService");
		return discountMapper.selectAll();
	}

}
