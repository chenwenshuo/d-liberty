package com.dliberty.liberty.service.impl;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.dliberty.liberty.lang.data.StringUtils;
import com.dliberty.liberty.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JavaMailSender jms;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Override
	public Boolean sendSimpleEmail(String[] receiveEmails, String title, String content) {
		if (receiveEmails == null || receiveEmails.length == 0) {
			logger.warn("邮件发送失败,邮箱接收人不能为空");
			return false;
		}
		if (StringUtils.isEmpty(title)) {
			logger.warn("邮件发送失败,邮件内容不能为空");
			return false;
		}
		if (StringUtils.isEmpty(content)) {
			logger.warn("邮件发送失败,邮件内容不能为空");
			return false;
		}
		try {
			//建立邮件消息
			SimpleMailMessage mainMessage = new SimpleMailMessage();
			//发送者
			mainMessage.setFrom(fromEmail);
			//接收者
			mainMessage.setTo(receiveEmails);
			//发送的标题
			mainMessage.setSubject(title);
			//发送的内容
			mainMessage.setText(content);
			jms.send(mainMessage);
			return true;
		} catch (Exception e) {
			logger.info("邮件发送异常{}",e.getMessage());
			e.printStackTrace();
			return false;
		}
		

		
	}

	@Override
	public Boolean sendHtmlMail(String[] receiveEmails, String title, String content) {
		if (receiveEmails == null || receiveEmails.length == 0) {
			logger.warn("邮件发送失败,邮箱接收人不能为空");
			return false;
		}
		if (StringUtils.isEmpty(title)) {
			logger.warn("邮件发送失败,邮件内容不能为空");
			return false;
		}
		if (StringUtils.isEmpty(content)) {
			logger.warn("邮件发送失败,邮件内容不能为空");
			return false;
		}
		try {
			MimeMessage message=jms.createMimeMessage();
			//建立邮件消息
			MimeMessageHelper  mainMessage = new MimeMessageHelper(message,true);
			//发送者
			mainMessage.setFrom(fromEmail);
			//接收者
			mainMessage.setTo(receiveEmails);
			//发送的标题
			mainMessage.setSubject(title);
			//发送的内容
			mainMessage.setText(content,true);
			jms.send(message);
			return true;
		} catch (Exception e) {
			logger.info("{}邮件发送异常{}",receiveEmails,e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

}
