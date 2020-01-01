package com.tang.mulit.datasource.test1.controller;

import com.tang.mulit.datasource.test1.domain.Order;
import com.tang.mulit.datasource.test1.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Classname OrderController
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/23 19:15
 * @Created by ASUS
 */
@RestController
public class OrderController {


    @Resource
    private OrderService orderService;

    @GetMapping("/order")
    public String order(Integer i) {

        Order order = new Order();

        order.setName("new xxx");
        order.setOrderCreatetime(new Date());
        order.setOrderState(1);
        order.setOrderMoney(10.0);
        order.setCommodityId(30);

        orderService.add(order,i);

        return "success";
    }

}