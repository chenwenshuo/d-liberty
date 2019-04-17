package com.dliberty.liberty.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dliberty.liberty.entity.AccountCategory;
import com.dliberty.liberty.entity.AccountRecord;
import com.dliberty.liberty.service.AccountBudgetService;
import com.dliberty.liberty.service.AccountCategoryService;
import com.dliberty.liberty.service.AccountRecordService;
import com.dliberty.liberty.service.NoticeUserService;
import com.dliberty.liberty.vo.AccountVo;
import com.dliberty.liberty.vo.JsonBean;
import com.dliberty.liberty.vo.ReportVo;

@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AccountCategoryService accountCategoryService;
	@Autowired
	AccountRecordService accountRecordService;
	@Autowired
	AccountBudgetService accountBudgetService; 
	@Autowired
	NoticeUserService noticeUserService;

	@RequestMapping("/category")
	public JsonBean selectCategory(String session,String type,Integer recordId){
		Integer userId = getUserId(session);
		JsonBean json = new JsonBean();
		if (recordId != null) {
			AccountRecord record = accountRecordService.findById(recordId);
			if (record != null) {
				json.put("recordId",recordId);
				json.put("record", record);
				type = record.getCateType();
			}
		}
		List<AccountCategory> cateList = accountCategoryService.selectByUserId(userId, type);
		
		json.put("cateList", cateList);
		json.put("nowDate", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
		return json;
	}
	
	@RequestMapping("/addCate")
	public JsonBean addCate(String session,String type,String cateName) {
		Integer userId = getUserId(session);
		JsonBean json = accountCategoryService.addCate(userId, type, cateName);
		return json;
	}
	
	@RequestMapping("/delCate")
	public JsonBean addCate(String session,Integer cateId) {
		JsonBean json = new JsonBean();
		try {
			accountCategoryService.delete(cateId);
			json.setCode("0");
		} catch (Exception e) {
			e.printStackTrace();
			json.setCode("1");
		}
		
		return json;
	}
	
	@RequestMapping(value = "/saveRecord", method=RequestMethod.POST)
	public JsonBean saveRecord(String session,Integer recordId,String type,Integer cateId,Integer accountMoney,String accountTime,String accountRemarks,String formId) {
		Integer userId = getUserId(session);
		JsonBean json = null;
		try {
			json = accountRecordService.save(userId, recordId,type, cateId, accountMoney, DateUtils.parseDate(accountTime, "yyyy-MM-dd"), accountRemarks,formId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/accountIndex")
	public AccountVo accountIndex(String session,Integer year,Integer month){
		Integer userId = getUserId(session);
		try {
			AccountVo vo = accountRecordService.accountIndex(userId,year,month);
			return vo;
		} catch(Exception e) {
			logger.warn("查询首页发生异常{}",e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/setBudget")
	public JsonBean setBudget(String session,Integer year,Integer month,Integer money) {
		JsonBean json = new JsonBean();
		try {
			Integer userId = getUserId(session);
			if (year == null || month == null || money == null || userId == null) {
				json.setCode("1");
				json.setMessage("参数错误");
				return json;
			}
			accountBudgetService.setBudget(year, month, userId, money);
			json.setMessage("操作成功");
			json.setCode("0");
		} catch (Exception e) {
			json.setCode("1");
			json.setMessage("操作失败");
			e.getStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping("/deleteAccount")
	public JsonBean deleteAccount(String session,Integer recordId) {
		JsonBean json = new JsonBean();
		try {
			Integer userId = getUserId(session);
			accountRecordService.deleteAccount(userId, recordId);
			json.setCode("0");
		} catch (Exception e) {
			json.setCode("1");
			json.setMessage("删除失败");
			e.getStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping("/findReport")
	public ReportVo findReport(String cateType,String session,Integer year,Integer month) {
		Integer userId = getUserId(session);
		ReportVo vo = accountRecordService.selectReport(userId, year, month, cateType);
		return vo;
	}
	
	@RequestMapping("/findRe")
	public ReportVo findRe(String cateType,String session,Date beginTime,Date endTime) {
		Integer userId = getUserId(session);
		ReportVo vo = accountRecordService.selectReport(userId, beginTime, endTime, cateType);
		return vo;
	}
	
	@RequestMapping("/reportList")
	public List<AccountRecord> reportList(String session,String cateName,String cateType,Integer year,Integer month) {
		Integer userId = getUserId(session);
		List<AccountRecord> reportList = accountRecordService.reportList(userId, cateName, cateType, year, month);
		return reportList;
	}
	
	@RequestMapping("/reportListDate")
	public List<AccountRecord> reportListDate(String session,String cateName,String cateType,Date beginTime,Date endTime) {
		Integer userId = getUserId(session);
		List<AccountRecord> reportList = accountRecordService.reportListDate(userId, cateName, cateType, beginTime, endTime);
		return reportList;
	}
	
}
