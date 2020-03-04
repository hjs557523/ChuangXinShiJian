package com.hjs.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 开启事务
@EnableTransactionManagement
// mapper 接口类扫描包配置
@MapperScan(basePackages = "com.hjs.system.mapper")
@SpringBootApplication
public class SystemApplication {
    public static void main(String[] args) {

        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境机器各 Spring 组件
        SpringApplication.run(SystemApplication.class, args);
        System.out.println("**************************************************");
        System.out.println("*          Hjs's system start-up success         *");
        System.out.println("**************************************************");
        System.out.println("*    ChuangXinShiJian Course Management System   *");
    }

}
