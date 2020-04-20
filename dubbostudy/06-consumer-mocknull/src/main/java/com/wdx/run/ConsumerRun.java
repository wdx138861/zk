package com.wdx.run;

import com.wdx.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerRun {

    public static void main(String[] args) {
        for (int i = 0; i <10 ; i++) {
            ApplicationContext ac = new ClassPathXmlApplicationContext("spring-consumer.xml");
            UserService service = (UserService) ac.getBean("userService");
            //有返回值的结果，返回值降级为null
            String userNameById = service.getUserNameById(3);
            System.out.println("userNameById = " + userNameById);

            //无返回值的方法降级结果时无任何显示
            service.addUser("china");
        }
    }
}
