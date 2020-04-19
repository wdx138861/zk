package com.wdx.run;

import com.wdx.service.SomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerRun {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-consumer.xml");

        SomeService weixinService = (SomeService) ac.getBean("weixin");
        String weixin = weixinService.hello("china");
        System.out.println(weixin);

        SomeService zhifubaoService = (SomeService) ac.getBean("zhifubao");
        String zhifubao = zhifubaoService.hello("china");
        System.out.println(zhifubao);
    }
}
