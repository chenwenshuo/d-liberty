package com.dliberty.liberty.vo;

import java.util.List;

public class AccountVo {

	//当月支出金额
	private Integer accountMoney;
	//当月收入金额
	private Integer incomeMoney;
	//预算
	private Integer budgetMoney;
	//每日明细
	private List<AccountDescVo> descVoList ;
	//背景图片
	private String backUrl;
	
	private String email;
	
	private Integer remindHour;
	    
	private Integer remindMinute;
	
	public Integer getAccountMoney() {
		return accountMoney;
	}
	public void setAccountMoney(Integer accountMoney) {
		this.accountMoney = accountMoney;
	}
	public Integer getIncomeMoney() {
		return incomeMoney;
	}
	public void setIncomeMoney(Integer incomeMoney) {
		this.incomeMoney = incomeMoney;
	}
	public List<AccountDescVo> getDescVoList() {
		return descVoList;
	}
	public void setDescVoList(List<AccountDescVo> descVoList) {
		this.descVoList = descVoList;
	}
	public Integer getBudgetMoney() {
		return budgetMoney;
	}
	public void setBudgetMoney(Integer budgetMoney) {
		this.budgetMoney = budgetMoney;
	}
	public String getBackUrl() {
		return backUrl;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getRemindHour() {
		return remindHour;
	}
	public void setRemindHour(Integer remindHour) {
		this.remindHour = remindHour;
	}
	public Integer getRemindMinute() {
		return remindMinute;
	}
	public void setRemindMinute(Integer remindMinute) {
		this.remindMinute = remindMinute;
	}
	
	
	
}
