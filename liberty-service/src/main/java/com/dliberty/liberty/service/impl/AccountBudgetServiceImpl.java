package com.dliberty.liberty.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dliberty.liberty.entity.AccountBudget;
import com.dliberty.liberty.mapper.AccountBudgetMapper;
import com.dliberty.liberty.service.AccountBudgetService;
import com.dliberty.liberty.service.RedisClient;

@Service
@Transactional
public class AccountBudgetServiceImpl implements AccountBudgetService {

	@Autowired
	AccountBudgetMapper accountBudgetMapper;
	@Autowired
	private RedisClient redisClient;
	
	@Override
	public AccountBudget setBudget(Integer year, Integer month, Integer userId,Integer money) {
		AccountBudget budget = selectBudget(year, month, userId);
		if (budget != null) {
			budget.setUpdateTime(new Date());
			budget.setBudgetMoney(money*100);
			accountBudgetMapper.updateByPrimaryKey(budget);
		} else {
			budget = new AccountBudget();
			budget.setBudgetYear(year);
			budget.setBudgetMonth(month);
			budget.setBudgetMoney(money*100);
			budget.setCreateTime(new Date());
			budget.setUserId(userId);
			budget.setUpdateTime(new Date());
			accountBudgetMapper.insert(budget);
		}
		String totalKey = "totalkey"+userId+year+month;
		redisClient.del(totalKey);
		return budget;
	}

	@Override
	public AccountBudget selectBudget(Integer year, Integer month, Integer userId) {
		return accountBudgetMapper.selectBudget(year, month, userId);
	}

}
