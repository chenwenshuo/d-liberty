package com.dliberty.liberty.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dliberty.liberty.entity.AccountCategory;
import com.dliberty.liberty.mapper.AccountCategoryMapper;
import com.dliberty.liberty.service.AccountCategoryService;
import com.dliberty.liberty.vo.JsonBean;

@Service
@Transactional
public class AccountCategoryServiceImpl implements AccountCategoryService {
	
	@Autowired
	AccountCategoryMapper accountCategoryMapper;

	@Override
	public AccountCategory selectById(Integer id) {
		return accountCategoryMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<AccountCategory> selectByUserId(Integer userId,String type) {
		if (userId == null) {
			return new ArrayList<>();
		}
		return accountCategoryMapper.selectByUserId(userId,type);
	}

	@Override
	public AccountCategory save(AccountCategory category) {
		category.setCreateTime(new Date());
		category.setUpdateTime(new Date());
		accountCategoryMapper.insert(category);
		return category;
	}

	@Override
	public AccountCategory update(AccountCategory category) {
		category.setUpdateTime(new Date());
		accountCategoryMapper.updateByPrimaryKey(category);
		return category;
	}

	@Override
	public void initUserCategory(Integer userId) {
		
		//初始化 支出
		List<AccountCategory> cateUserList = selectByUserId(userId,"0"); 
		if (cateUserList != null && cateUserList.size() > 0) {
			return;
		}
		List<AccountCategory> cateParent = selectByUserId(-1,"0");
		if (cateParent != null && cateParent.size() > 0) {
			for (AccountCategory category : cateParent) {
				AccountCategory userCate = new AccountCategory();
				userCate.setCateName(category.getCateName());
				userCate.setIsDeleted("0");
				userCate.setCateUserId(userId);
				userCate.setCateType("0");
				save(userCate);
			}
		}
		
		//初始化 支出
			List<AccountCategory> cateUserShouList = selectByUserId(userId,"1"); 
			if (cateUserShouList != null && cateUserShouList.size() > 0) {
				return;
			}
			List<AccountCategory> cateShouParent = selectByUserId(-1,"1");
			if (cateShouParent != null && cateShouParent.size() > 0) {
				for (AccountCategory category : cateShouParent) {
					AccountCategory userCate = new AccountCategory();
					userCate.setCateName(category.getCateName());
					userCate.setIsDeleted("0");
					userCate.setCateUserId(userId);
					userCate.setCateType("1");
					save(userCate);
				}
			}
	}

	@Override
	public JsonBean addCate(Integer userId, String type, String cateName) {
		JsonBean json = new JsonBean();
		int cateNameNum = accountCategoryMapper.selectByCateName(userId, type, cateName);
		if (cateNameNum > 0) {
			json.setCode("1");
			json.setMessage("你已添加此类别");
			return json;
		}
		AccountCategory cate = new AccountCategory();
		cate.setIsDeleted("0");
		cate.setCateName(cateName);
		cate.setCateType(type);
		cate.setCateUserId(userId);
		save(cate);
		json.setCode("0");
		json.put("cate", cate);
		return json;
	}

	@Override
	public void delete(Integer cateId) {
		if (cateId == null) {
			return;
		}
		AccountCategory cate = accountCategoryMapper.selectByPrimaryKey(cateId);
		if (cate != null) {
			cate.setIsDeleted("1");
			update(cate);
		}
	}

}
