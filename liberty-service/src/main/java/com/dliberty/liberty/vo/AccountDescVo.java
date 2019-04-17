package com.dliberty.liberty.vo;

import java.util.Date;
import java.util.List;

import com.dliberty.liberty.entity.AccountRecord;

public class AccountDescVo {

	//当日支出金额
	private Integer accountMoney;
	//当日收入金额
	private Integer incomeMoney;
	//日期
	private Date time;
	//当日交易明细
	private List<AccountRecord> recordList;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public List<AccountRecord> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<AccountRecord> recordList) {
		this.recordList = recordList;
	}
	
	
	
}
