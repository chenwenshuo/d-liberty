package com.dliberty.liberty.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dliberty.liberty.entity.AccountBudget;
import com.dliberty.liberty.entity.AccountCategory;
import com.dliberty.liberty.entity.AccountRecord;
import com.dliberty.liberty.entity.DocFile;
import com.dliberty.liberty.entity.WeixinUser;
import com.dliberty.liberty.entity.ext.ReportExtVo;
import com.dliberty.liberty.lang.data.IntUtils;
import com.dliberty.liberty.lang.data.StringUtils;
import com.dliberty.liberty.mapper.AccountRecordMapper;
import com.dliberty.liberty.service.AccountBudgetService;
import com.dliberty.liberty.service.AccountCategoryService;
import com.dliberty.liberty.service.AccountRecordService;
import com.dliberty.liberty.service.DocFileService;
import com.dliberty.liberty.service.RedisClient;
import com.dliberty.liberty.service.WeixinUserService;
import com.dliberty.liberty.vo.AccountDescVo;
import com.dliberty.liberty.vo.AccountVo;
import com.dliberty.liberty.vo.JsonBean;
import com.dliberty.liberty.vo.ReportVo;

@Service("accountRecordService")
@Transactional
public class AccountRecordServiceImpl implements AccountRecordService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountRecordServiceImpl.class);
	
	@Autowired
	private AccountRecordMapper accountRecordMapper;
	@Autowired
	private AccountCategoryService accountCategoryService;
	@Autowired
	private AccountBudgetService accountBudgetService;
	@Autowired
	private DocFileService docFileService;
	@Autowired
	private WeixinUserService weixinUserService;
	@Autowired
	private RedisClient redisClient;

	@Override
	public JsonBean save(Integer userId,Integer recordId, String cateType, Integer cateId, Integer accountMoney, Date accountTime,
			String accountRemarks,String formId) {
		JsonBean jsonBean = new JsonBean();
		if (userId == null) {
			jsonBean.setCode("1");
			jsonBean.setMessage("用户信息错误");
			return jsonBean;
		}
		if (StringUtils.isEmpty(cateType) || cateId == null) {
			jsonBean.setCode("1");
			jsonBean.setMessage("请选择分类");
			return jsonBean;
		}
		if (accountMoney == null) {
			jsonBean.setCode("1");
			jsonBean.setMessage("请输入金额");
			return jsonBean;
		}
		if (accountTime == null) {
			jsonBean.setCode("1");
			jsonBean.setMessage("请选择日期");
			return jsonBean;
		}
		
		AccountRecord record = null;
		Date oldTime = null;
		if (recordId != null) {
			record = findById(recordId);
			oldTime = record.getAccountTime();
		}
		if (record == null) {
			record = new AccountRecord();
			record.setCreateTime(new Date());
		}
		record.setUserId(userId);
		record.setCateType(cateType);
		record.setCateId(cateId);
		AccountCategory category = accountCategoryService.selectById(cateId);
		if (category != null) {
			record.setCateName(category.getCateName());
		}
		record.setAccountMoney(accountMoney);
		record.setAccountTime(accountTime);
		record.setAccountRemarks(accountRemarks);
		record.setUpdateTime(new Date());
		record.setDeleted("0");
		record.setFormId(formId);
		if (StringUtils.isEmpty(formId) || formId.indexOf("formId") > -1) {
			record.setFormIdExpire("1");
		} else {
			record.setFormIdExpire("0");
		}
		
		
		if (record.getId() == null) {
			accountRecordMapper.insert(record);
		} else {
			accountRecordMapper.updateByPrimaryKey(record);
			if (oldTime != null && oldTime.getTime() != accountTime.getTime()) {
				//修改了日期，删除修改前的缓存
				Calendar cal = Calendar.getInstance();
				cal.setTime(oldTime);
				Integer yearSearch = cal.get(Calendar.YEAR);
				Integer monthSearch = cal.get(Calendar.MONTH)+1;
				Integer daySearch = cal.get(Calendar.DATE);
				String cacheKey =  "cache.record"+userId+yearSearch+monthSearch+daySearch;
				redisClient.del(cacheKey);
			}
		}
		
		//删除当天缓存
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(record.getAccountTime());
		Integer yearSearch = cal2.get(Calendar.YEAR);
		Integer monthSearch = cal2.get(Calendar.MONTH)+1;
		Integer daySearch = cal2.get(Calendar.DATE);
		String cacheKey =  "cache.record"+userId+yearSearch+monthSearch+daySearch;
		redisClient.del(cacheKey);
		
		String totalKey = "totalkey"+userId+yearSearch+monthSearch;
		redisClient.del(totalKey);
		
		jsonBean.setCode("0");
		jsonBean.put("record", record);
		return jsonBean;
	}
	
	/**
	 * 修改
	 * @param record
	 * @return
	 */
	public AccountRecord update(AccountRecord record) {
		record.setUpdateTime(new Date());
		accountRecordMapper.updateByPrimaryKey(record);
		return record;
	}

	@Override
	public AccountVo accountIndex(Integer userId,Integer year,Integer month) {
		if (userId == null) {
			return null;
		}
		AccountVo vo = null;
		
		
		String totalKey = "totalkey"+userId+year+month;
		String totalValue = redisClient.get(totalKey);
		if (StringUtils.isNotEmpty(totalValue)) {
			redisClient.set(totalKey,totalValue,60*60*24*10);
			vo = JSONObject.parseObject(totalValue, AccountVo.class);
			WeixinUser user = weixinUserService.selectByUserId(userId);
			if (user != null) {
				vo.setEmail(user.getEmail());
				vo.setRemindHour(user.getRemindHour());
				vo.setRemindMinute(user.getRemindMinute());
			}
			return vo;
		}
		vo = new AccountVo();
		
		
		if (year == null || month == null) {
			Date currentDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH)+1;
		}
		
		//当月消费
		Integer accountMoney = findMoneyByDay(userId, "0",year, month, null);
		vo.setAccountMoney(accountMoney);
		//当月收入
		Integer incomeMoney = findMoneyByDay(userId, "1", year, month, null);
		vo.setIncomeMoney(incomeMoney);
		
		//预算
		AccountBudget budget = accountBudgetService.selectBudget(year, month, userId);
		if (budget != null) {
			vo.setBudgetMoney(budget.getBudgetMoney());
		}
		
		//当月记账日期
		List<Date> accountDateList = findAccountDate(userId,  year, month);
		
		List<AccountDescVo> descVoList = new ArrayList<>();
		Calendar cal2 = Calendar.getInstance();
		for (Date date : accountDateList) {
			
			AccountDescVo descVo = null;
			cal2.setTime(date);
			
			
			Integer yearSearch = cal2.get(Calendar.YEAR);
			Integer monthSearch = cal2.get(Calendar.MONTH)+1;
			Integer daySearch = cal2.get(Calendar.DATE);
			
			String cacheKey =  "cache.record"+userId+yearSearch+monthSearch+daySearch;
			String value = redisClient.get(cacheKey);
			if (StringUtils.isNotEmpty(value)) {
				redisClient.set(cacheKey, value, 60*60*24*10);
				descVo = JSONObject.parseObject(value, AccountDescVo.class);
			}
			if (descVo == null) {
				descVo = new AccountDescVo();
				//当日消费情况
				Integer accountMoneyDay = findMoneyByDay(userId, "0", yearSearch, monthSearch, daySearch);
				descVo.setAccountMoney(accountMoneyDay);
				//当日消费情况
				Integer incomeMoneyDay = findMoneyByDay(userId, "1", yearSearch, monthSearch, daySearch);
				descVo.setIncomeMoney(incomeMoneyDay);
				//当日记录
				List<AccountRecord> recordList = findByDate(userId, date);
				descVo.setRecordList(recordList);
				descVo.setTime(date);
				redisClient.set(cacheKey, JSONObject.toJSONString(descVo), 60*60*24*10);
			}
			
			descVoList.add(descVo);
		}
		vo.setDescVoList(descVoList);
		
		//背景图
		DocFile docFile = docFileService.selectById(1); 
		if (docFile != null) {
			vo.setBackUrl("http://www.dliberty.com/images/" + docFile.getFileKey());
		}
		redisClient.set(totalKey,JSONObject.toJSONString(vo),60*60);
		WeixinUser user = weixinUserService.selectByUserId(userId);
		if (user != null) {
			vo.setEmail(user.getEmail());
			vo.setRemindHour(user.getRemindHour());
			vo.setRemindMinute(user.getRemindMinute());
		}
		return vo;
	}

	@Override
	public Integer findMoneyByDay(Integer userId,String cateType, Integer year, Integer month, Integer day) {
		return accountRecordMapper.findMoneyByDay(userId, cateType, year, month, day);
	}

	@Override
	public List<Date> findAccountDate(Integer userId, Integer year, Integer month) {
		return accountRecordMapper.findAccountDate(userId, year, month);
	}

	@Override
	public List<AccountRecord> findByDate(Integer userId, Date accountTime) {
		return accountRecordMapper.findByDate(userId, accountTime);
	}

	@Override
	public AccountRecord findById(Integer id) {
		return accountRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteAccount(Integer userId, Integer recordId) {
		AccountRecord record = findById(recordId);
		if (record != null && IntUtils.valueEquals(record.getUserId(), userId)) {
			
			//删除当天缓存
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(record.getAccountTime());
			Integer yearSearch = cal2.get(Calendar.YEAR);
			Integer monthSearch = cal2.get(Calendar.MONTH)+1;
			Integer daySearch = cal2.get(Calendar.DATE);
			String cacheKey =  "cache.record"+userId+yearSearch+monthSearch+daySearch;
			redisClient.del(cacheKey);
			
			String totalKey = "totalkey"+userId+yearSearch+monthSearch;
			redisClient.del(totalKey);
			
			
			record.setUpdateTime(new Date());
			record.setDeleted("1");
			accountRecordMapper.updateByPrimaryKey(record);
		}
	}

	@Override
	public ReportVo selectReport(Integer userId, Integer year, Integer month,String cateType) {
		ReportVo vo = new ReportVo();
		Integer money = findMoneyByDay(userId, cateType, year, month, null);
		vo.setMoney(money);
		List<ReportExtVo> extVoList = accountRecordMapper.findReportExt(userId, cateType, year, month, null);
		vo.setReportList(extVoList);
		/*List<AccountRecord> recordList = accountRecordMapper.findAccountMax(userId, cateType, 3, year, month);
		vo.setRecordList(recordList);*/
		return vo;
	}
	
	@Override
	public ReportVo selectReport(Integer userId, Date beginTime, Date endTime, String cateType) {
		ReportVo vo = new ReportVo();
		Integer money = findMoneyByDate(userId, cateType, beginTime, endTime);
		vo.setMoney(money);
		List<ReportExtVo> extVoList = accountRecordMapper.findReportExtByDate(userId, cateType, beginTime, endTime);
		vo.setReportList(extVoList);
		/*List<AccountRecord> recordList = accountRecordMapper.findAccountMax(userId, cateType, 3, year, month);
		vo.setRecordList(recordList);*/
		return vo;
	}

	@Override
	public List<AccountRecord> reportList(Integer userId, String cateName, String cateType, Integer year,
			Integer month) {
		List<AccountRecord> reportList = accountRecordMapper.reportList(userId, cateName, cateType, year, month);
		return reportList;
	}

	@Override
	public void syncFormIdExpire() {
		//查询出未过期的
		logger.info("开始更新formId有效期数据");
		List<AccountRecord> recordList = accountRecordMapper.findFormIdExpire();
		if (recordList != null && recordList.size() > 0) {
			for (AccountRecord record : recordList) {
				String formId = record.getFormId();
				if (StringUtils.isEmpty(formId) || formId.indexOf("formId") > -1) {
					record.setFormIdExpire("1");
					record.setUpdateTime(new Date());
					accountRecordMapper.updateByPrimaryKey(record);
				} else {
					Date createDate = record.getCreateTime();
					Date newDate = new Date();
					if ((newDate.getTime()-createDate.getTime()) > (7*24*60*60*1000)) {
						record.setFormIdExpire("1");
						record.setUpdateTime(new Date());
						accountRecordMapper.updateByPrimaryKey(record);
					}
				}
			}
		}
		logger.info("更新formId有效期数据结束");
	}

	@Override
	public AccountRecord findFormId(Integer userId) {
		if (userId == null) {
			return null;
		}
		return accountRecordMapper.findFormId(userId);
	}

	@Override
	public AccountRecord findLastRecord(Integer userId) {
		if (userId == null) {
			return null;
		}
		return accountRecordMapper.findLastRecord(userId);
	}

	@Override
	public Integer findMoneyByDate(Integer userId, String cateType, Date beginTime, Date endTime) {
		return accountRecordMapper.findMoneyByDate(userId, cateType, beginTime, endTime);
	}

	@Override
	public List<AccountRecord> reportListDate(Integer userId, String cateName, String cateType, Date beginTime,
			Date endTime) {
		List<AccountRecord> reportList = accountRecordMapper.reportListDate(userId, cateName, cateType, beginTime, endTime);
		return reportList;
	}


}
