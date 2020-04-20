package com.wdx.run;

import com.wdx.service.SomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerRun {

    public static void main(String[] args) {
        for (int i = 0; i <10 ; i++) {
            ApplicationContext ac = new ClassPathXmlApplicationContext("spring-consumer.xml");
            SomeService someService = (SomeService) ac.getBean("someService");
            String hello = someService.hello("china");
            System.out.println(hello);
        }
    }
}
