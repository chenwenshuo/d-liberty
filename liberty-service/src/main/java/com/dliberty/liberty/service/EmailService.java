package com.dliberty.liberty.service;

/**
 * 邮件服务
 * @author LG
 *
 */
public interface EmailService {

	/**
	 * 发送邮件服务
	 * @param receiveEmails
	 * @param title
	 * @param content
	 * @return
	 */
	Boolean sendSimpleEmail(String[] receiveEmails,String title,String content);
	
	/**
	 * 
	 * @param receiveEmails
	 * @param title
	 * @param content
	 * @return
	 */
	Boolean sendHtmlMail(String[] receiveEmails,String title,String content);
}
