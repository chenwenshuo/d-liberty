package com.dliberty.liberty.task;

import com.dliberty.liberty.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dliberty.liberty.service.AccountRecordService;
import com.dliberty.liberty.service.NoticeUserService;
import com.dliberty.liberty.utils.SpringUtils;

@Component
public class ScheduledTask {

	@Scheduled(cron="0 0 0/1 *  * ?")//每1小时执行一次执行
	public void syncFormIdExpire() {
		AccountRecordService recordService = SpringUtils.getBean("accountRecordService",AccountRecordService.class);
		recordService.syncFormIdExpire();
	}
	
	@Scheduled(cron="0 30 20 *  * ?")//定时通知用户 每天晚上22：00执行
	public void noticeUser() {
		NoticeUserService noticeUserService = SpringUtils.getBean("noticeUserService",NoticeUserService.class);
		noticeUserService.noticeUserJob();
	}
	
	//@Scheduled(cron="0 0/30 * *  * ?")//每1分钟执行一次执行
	public void noticeUserForMinute() {
		NoticeUserService noticeUserService = SpringUtils.getBean("noticeUserService",NoticeUserService.class);
		noticeUserService.noticeUserForMinuteJob();
	}
	
	@Scheduled(cron="0 0 20 * * ?")
	public void sendRecordForEmail() {
		NoticeUserService noticeUserService = SpringUtils.getBean("noticeUserService",NoticeUserService.class);
		noticeUserService.sendRecordForEmail();
	}
	@Scheduled(cron="0 0/30 * *  * ?")
	public void sendRecordForEmail1() {
		/*String[] email={"17864282307@163.com"};
		EmailService emailService = SpringUtils.getBean("emil",EmailService.class);
		emailService.sendSimpleEmail(email,"你好","测试");*/
		System.out.println("jjj");
	}

}
