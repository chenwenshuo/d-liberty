package com.dliberty.liberty.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dliberty.liberty.service.EmailService;
import com.dliberty.liberty.service.NoticeUserService;



@RestController
@RequestMapping("/hello")
public class HelloController {

	@Autowired
	EmailService emailService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/home")
	public String home() {
			String[] email={"17864282307@163.com"};
        	emailService.sendSimpleEmail(email,"你好","测试");
		System.out.println("k");
		return "helloword";
	}
	
	@GetMapping("/del/{fileKey}")
	public String del(@PathVariable("fileKey")String fileKey) {
		return "helloword";
	}
	
	
}
