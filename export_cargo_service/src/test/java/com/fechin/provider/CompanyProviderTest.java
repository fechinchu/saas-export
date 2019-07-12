package com.fechin.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CompanyProviderTest {
    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        context.start();//启动服务
        System.in.read();//任意键退出
    }
}
