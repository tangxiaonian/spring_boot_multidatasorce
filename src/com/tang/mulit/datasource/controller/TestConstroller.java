package com.tang.mulit.datasource.controller;

import com.tang.mulit.datasource.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname TestConstroller
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/1/1 21:57
 * @Created by ASUS
 */
@RestController
public class TestConstroller {

    @Resource
    private TestService testService;

    @GetMapping("/test")
    public String testController(int i) {

        testService.testService(i);

        return "success";
    }

}