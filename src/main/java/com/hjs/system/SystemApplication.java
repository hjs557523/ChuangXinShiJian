package com.hjs.system;

//import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableCaching
@Configuration
@EnableTransactionManagement //开启事务
@MapperScan(basePackages = "com.hjs.system.mapper") //mapper 接口类扫描包配置
@EnableScheduling
@SpringBootApplication
public class SystemApplication {
    public static void main(String[] args) {

        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境机器各 Spring 组件
        SpringApplication.run(SystemApplication.class, args);
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t*********************************************************************************************************");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t**                                   Hjs's system start-up success                                     **");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t*********************************************************************************************************");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t**                          ChuangXinShiJian Course Management System v1.0                             **");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t*********************************************************************************************************");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t**          Note: The System supports WeChat applet and web client-side, multi - terminal use          **");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t*********************************************************************************************************");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t**                          Author: Huang JiSheng    Finish Time: 2020/5/1                             **");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t*********************************************************************************************************");

    }

}
