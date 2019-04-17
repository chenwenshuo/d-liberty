package com.dliberty.liberty.service;

import java.util.List;

import com.dliberty.liberty.entity.AccountCategory;
import com.dliberty.liberty.vo.JsonBean;

/**
 * 记账类别
 * @author LG
 *
 */
public interface AccountCategoryService {
	
	/**
	 * 根据主键id查询
	 * @param id
	 * @return
	 */
	AccountCategory selectById(Integer id);

	/**
	 * 查询用户得采购类别
	 * @return
	 */
	List<AccountCategory> selectByUserId(Integer userId,String type);
	
	/**
	 * 用户新增采购类别
	 * @param category
	 * @return
	 */
	AccountCategory save(AccountCategory category);
	
	/**
	 * 修改用户采购类别
	 * @param category
	 * @return
	 */
	AccountCategory update(AccountCategory category);
	
	/**
	 * 初始化用户采购类别
	 * @param userId
	 */
	void initUserCategory(Integer userId);
	
	/**
	 * 添加采购类别
	 * @param userId
	 * @param type
	 * @param cateName
	 * @return
	 */
	JsonBean addCate(Integer userId,String type,String cateName);
	
	/**
	 * 删除采购类别
	 */
	void delete(Integer cateId);
}
