package com.tang.mulit.datasource.service;

import com.tang.mulit.datasource.test1.domain.Order;
import com.tang.mulit.datasource.test1.service.OrderService;
import com.tang.mulit.datasource.test2.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Classname TestService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/1/1 22:00
 * @Created by ASUS
 */
@Service
public class TestService {

    @Resource
    private StockService stockService;

    @Resource
    private OrderService orderService;

    @Transactional
    public void testService(int i) {

        Order order = new Order();

        order.setName("order1");
        order.setOrderCreatetime(new Date());
        order.setOrderMoney(100.0);
        order.setOrderState(1);

        stockService.update();
        orderService.add(order,1);

        int n = 1 / i;
    }

}