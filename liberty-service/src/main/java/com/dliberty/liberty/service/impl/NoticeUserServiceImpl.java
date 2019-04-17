package com.dliberty.liberty.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dliberty.liberty.entity.AccountBudget;
import com.dliberty.liberty.entity.AccountRecord;
import com.dliberty.liberty.entity.WeixinUser;
import com.dliberty.liberty.lang.data.IntUtils;
import com.dliberty.liberty.lang.data.StringUtils;
import com.dliberty.liberty.service.AccountBudgetService;
import com.dliberty.liberty.service.AccountRecordService;
import com.dliberty.liberty.service.EmailService;
import com.dliberty.liberty.service.NoticeUserService;
import com.dliberty.liberty.service.RedisClient;
import com.dliberty.liberty.service.WeixinUserService;
import com.dliberty.liberty.weixin.service.WeixinTemplateMessageService;

@Service("noticeUserService")
@Transactional
public class NoticeUserServiceImpl implements NoticeUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeUserServiceImpl.class);
	
	@Autowired
	WeixinTemplateMessageService weixinTemplateMessageService;
	@Autowired
	WeixinUserService weixinUserService;
	@Autowired
	AccountRecordService accountRecordService;
	@Autowired
	RedisClient redisClient;
	@Autowired
	AccountBudgetService accountBudgetService;
	@Autowired
	EmailService emailService;

	@Override
	public void noticeUserJob() {
		
		boolean setNx = redisClient.setNx("notice_user_job", "1", 10);
		if (!setNx) {
			logger.info("job防重复执行执行成功");
			return;
		}
		List<WeixinUser> users = weixinUserService.selectAll();
		sendNotice(users);
	}
	
	@Override
	public void noticeUserForMinuteJob() {
		boolean setNx = redisClient.setNx("notice_user_for_minute_job", "1", 10);
		if (!setNx) {
			logger.info("job防重复执行执行成功");
			return;
		}
		Date currentDate = new Date();
		Calendar calender = Calendar.getInstance();
		calender.setTime(currentDate);
		int hour = calender.get(Calendar.HOUR);
		int minute = calender.get(Calendar.MINUTE);
		logger.info("查询{}:{}通知的用户",hour,minute);
		List<WeixinUser> users = weixinUserService.selectRemind(hour, minute);
		logger.info("通知{}:{}的用户开始",hour,minute);
		sendNotice(users);
		logger.info("通知{}:{}的用户介结束",hour,minute);
		
	}
	
	
	private void sendNotice(List<WeixinUser> users) {
		if (users == null || users.size() == 0) {
			return;
		}
		for (WeixinUser user : users) {
			AccountRecord record = accountRecordService.findFormId(user.getId());
			if (record == null) {
				logger.info("用户id={}的用户没有有效的formId,无法通知",user.getId());
				continue;
			}
			
				Date currentDate = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(currentDate);
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
			
			
			
			//当月消费
			Integer accountMoney = accountRecordService.findMoneyByDay(user.getId(), "0",year, month, null);
			//当月收入
			Integer incomeMoney = accountRecordService.findMoneyByDay(user.getId(), "1", year, month, null);
			
			AccountRecord lastRecord = accountRecordService.findLastRecord(user.getId());
			
			Map<String,Map<String,String>> data = new HashMap<String, Map<String,String>>();
			
			Map<String,String> mess = new HashMap<String, String>();
			mess.put("value", "每日收支");
			data.put("keyword1", mess);
			
			mess = new HashMap<String, String>();
			if (lastRecord != null) {
				mess.put("value", DateFormatUtils.format(lastRecord.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			} else {
				mess.put("value", "无");
			}
			data.put("keyword2", mess);
			
			mess = new HashMap<String, String>();
			mess.put("value", "支出"+accountMoney/100+"，收入"+incomeMoney/100);
			data.put("keyword3", mess);
			
			mess = new HashMap<String, String>();
			mess.put("value", "无限骄傲");
			mess.put("color", "#FF0000");
			data.put("keyword4", mess);
			
			weixinTemplateMessageService.sendTemplateMessage(user.getOpenId(), "xM8nB3tDrzxqbzpahbHCFzY4ZWo6sTyH1KRi4L8jNFo", "pages/index/index", record.getFormId(), data);
			
			//formId已被使用，设为已使用
			record.setFormIdExpire("2");
			record.setUpdateTime(new Date());
			accountRecordService.update(record);
		}
	}

	@Override
	public void sendRecordForEmail() {
		
		WeixinUser user1 = weixinUserService.selectByUserId(110257);
		//List<WeixinUser> emails = weixinUserService.selectEmail();
		List<WeixinUser> emails = new ArrayList<WeixinUser>();
		emails.add(user1);
		if (emails == null || emails.size() == 0) {
			return;
		}
		for (int i = emails.size(); i > 0 ; i--) {
			WeixinUser user  = emails.get(i-1);
			//查询配置了邮箱的用户
			String email = user.getEmail();
			Integer userId = user.getId();
			
			//查询该用户上个月的账单
			Date currentDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1;
			
			if (month == 1) {
				year -= 1;
				month = 12;
			} else {
				month -=1;
			}
			//当月消费
			Integer accountMoney = accountRecordService.findMoneyByDay(userId, "0",year, month, null);
			//当月收入
			Integer incomeMoney = accountRecordService.findMoneyByDay(userId, "1", year, month, null);
			
			if (accountMoney == null && incomeMoney == null) {
				logger.warn("userId={},year={},month={}没有账单",userId,year,month);
				continue;
			}
			//预算
			AccountBudget budget = accountBudgetService.selectBudget(year, month, userId);
			Integer budgetMoney = null;
			if (budget != null) {
				budgetMoney = budget.getBudgetMoney();
			}
			
			String monthAccount = getMonthAccount(year, month, userId);
			
			String content = "<html><div style='width: 750px;margin: 0 auto;'><h3 style='text-align: center;'>牧屿山歌"+year+"年"+month+"月账单</h3>"+
						"<table style='width: 100%;border-collapse: collapse;border: 1px solid #ddd;'>"+
						"<tr style='height: 36px;line-height: 36px;border-bottom: 1px solid #ddd;text-align: center'>"+
							"<td colspan='2'><h5 style='display: inline-block;'><span style='margin: 0 20px;'>总支出："+IntUtils.defaultInt(accountMoney, 0)/100+"</span><span style='margin: 0 20px;'>总收入："+IntUtils.defaultInt(incomeMoney, 0)/100+"</span><span style='margin: 0 20px;'>预算："+IntUtils.defaultInt(budgetMoney, 0)/100+"</span></h5></td>"+
						"</tr>"+monthAccount+
						"</table></div></html>";
			
			
			
			emailService.sendHtmlMail(new String[]{email}, "牧屿山歌"+year+"年"+month+"月账单", content);
		}
		
		
	}

	public String getMonthAccount(Integer year,Integer month,Integer userId) {
		StringBuilder sb = new StringBuilder("");
		//当月记账日期
		List<Date> accountDateList = accountRecordService.findAccountDate(userId,  year, month);
		Calendar cal2 = Calendar.getInstance();
		for (Date date : accountDateList) {
			cal2.setTime(date);
			Integer yearSearch = cal2.get(Calendar.YEAR);
			Integer monthSearch = cal2.get(Calendar.MONTH)+1;
			Integer daySearch = cal2.get(Calendar.DATE);
			//当日消费情况
			Integer accountMoneyDay = accountRecordService.findMoneyByDay(userId, "0", yearSearch, monthSearch, daySearch);
			//当日收入情况
			Integer incomeMoneyDay = accountRecordService.findMoneyByDay(userId, "1", yearSearch, monthSearch, daySearch);
			//当日记录
			List<AccountRecord> recordList = accountRecordService.findByDate(userId, date);
			
			sb.append("<tr style='height: 36px;line-height: 36px;border-bottom: 1px solid #ddd;text-align: center;background:#ddd;'>"+
						"<td >"+DateFormatUtils.format(date, "yyyy-MM-dd")+"</td>"+
						"<td ><span style='margin-right: 20px;color: #ff6454;'>支出："+IntUtils.defaultInt(accountMoneyDay, 0)/100+"</span><span style='color:#51a284'>收入："+IntUtils.defaultInt(incomeMoneyDay, 0)/100+"</span></td>"+
					"</tr>");
			for (AccountRecord record : recordList) {
				String color = "#fff";
				String remarks = "";
				if (StringUtils.isNoneEmpty(record.getAccountRemarks())) {
					remarks = "("+record.getAccountRemarks()+")";
				}
				String money = "";
				if ("0".equals(record.getCateType())) {
					money = "<td style='color:#ff6454;'>-"+IntUtils.defaultInt(record.getAccountMoney()/100, 0)+"</td>";
				}else {
					money = "<td style='color:#51a284;'>"+IntUtils.defaultInt(record.getAccountMoney()/100, 0)+"</td>";
				}
				sb.append("<tr style='text-align: center;height: 36px;line-height: 36px;border-bottom: 1px solid #ddd;background: "+color+";'>"+
						"<td >"+record.getCateName()+remarks+"</td>"+
						money+
					"</tr>");
			}
			
		}
		
		return sb.toString();
	}


}
