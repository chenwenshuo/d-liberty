package com.dliberty.liberty.service;

import java.util.List;

import com.dliberty.liberty.entity.Discount;

public interface DiscountService {

	/**
	 * 查询所有的优惠券信息
	 * @return
	 */
	List<Discount> selectAll();
}
