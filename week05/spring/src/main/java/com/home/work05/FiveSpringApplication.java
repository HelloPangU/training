package com.home.work05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class FiveSpringApplication {



    public static void main(String[] args) {
        SpringApplication.run(FiveSpringApplication.class, args);
        String xmlPath = "bean5.xml";
        //ClassPathXmlApplicationContext在加载配置文件时，对Bean进行实例化
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        System.out.println(applicationContext.getBean("user1"));
        System.out.println(applicationContext.getBean("user2"));
        System.out.println();
    }

}
