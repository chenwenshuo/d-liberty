package com.dliberty.liberty.service;

import com.dliberty.liberty.entity.AccountBudget;

/**
 * 预算
 * @author LG
 *
 */
public interface AccountBudgetService {

	/**
	 * 设置预算
	 * @param year
	 * @param month
	 * @param userId
	 * @return
	 */
	AccountBudget setBudget(Integer year,Integer month,Integer userId,Integer money);
	
	/**
	 * 查询预算
	 * @param year
	 * @param month
	 * @param userId
	 * @return
	 */
	AccountBudget selectBudget(Integer year,Integer month,Integer userId);
}
