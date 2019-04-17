package com.dliberty.liberty;


import com.dliberty.liberty.service.EmailService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class Test {

	/*public static void main(String[] args) {
		*//*List<String> numbers = Arrays.asList("3", "2", "2", "3", "7", "3", "5");

		long count = numbers.parallelStream().filter(string -> !numbers.isEmpty()).count();
		
		System.out.println(count);
		
		long count2 = numbers.stream().filter(string -> !numbers.isEmpty()).count();
		
		System.out.println(count2);
		
		Map<String,String> map = new HashMap<>();
		
		map.put("123", "123");*//*
		
		
		int b = -10;
		System.out.println(b >> 1);*/

     /*   @Autowired
		EmailService emailService;*/
        @org.junit.Test
        public  void  test(){
        	/*String[] email={"17864282307@163.com"};
        	emailService.sendSimpleEmail(email,"你好","测试");*/
			System.out.println("k");
		}

}
