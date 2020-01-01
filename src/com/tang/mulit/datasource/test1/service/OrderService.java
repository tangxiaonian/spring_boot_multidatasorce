package com.tang.mulit.datasource.test1.service;

import com.tang.mulit.datasource.test1.domain.Order;
import com.tang.mulit.datasource.test1.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Classname OrderService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/24 17:00
 * @Created by ASUS
 */
@Service
public class OrderService {

    @Resource
    public OrderMapper orderMapper;

    @Transactional
    public void add(Order order, Integer i) {

        orderMapper.insert(order);

        int a = 1 / i;

    }

}