package com.dliberty.liberty.service;

public interface NoticeUserService {

	/**
	 * 定时任务，每天晚上通知用户
	 */
	void noticeUserJob();
	
	/**
	 * 定时任务，每天晚上通知用户
	 */
	void noticeUserForMinuteJob();
	
	/**
	 * 定时任务，每月推送上月的记录到邮箱
	 */
	void sendRecordForEmail();
	
}
