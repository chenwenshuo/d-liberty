package com.dliberty.liberty.vo;

import java.util.List;

import com.dliberty.liberty.entity.AccountRecord;
import com.dliberty.liberty.entity.ext.ReportExtVo;

public class ReportVo {

	//当月支出金额
	private Integer money;
	
	private List<AccountRecord> recordList;
	
	private List<ReportExtVo> reportList;

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public List<AccountRecord> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<AccountRecord> recordList) {
		this.recordList = recordList;
	}

	public List<ReportExtVo> getReportList() {
		return reportList;
	}

	public void setReportList(List<ReportExtVo> reportList) {
		this.reportList = reportList;
	}
	
	
	
	
}
