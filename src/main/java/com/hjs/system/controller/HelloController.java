package com.hjs.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/2 19:52
 * @Modified By:
 */

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String sayHello() {
        System.out.println("Hello, user 访问到了这个路径...");
        return "If you can see this page, it comments that the frame is set up successfully...";
    }
}
