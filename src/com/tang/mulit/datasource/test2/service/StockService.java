package com.tang.mulit.datasource.test2.service;


import com.tang.mulit.datasource.test2.domain.Stock;
import com.tang.mulit.datasource.test2.mapper.StockMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Classname StockService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/24 18:24
 * @Created by ASUS
 */
@Service
public class StockService {

    @Resource
    private StockMapper stockMapper;

    @Transactional(value = "test02TransactionManager",rollbackFor = RuntimeException.class)
    public void update() {

        Stock stock = stockMapper.selectByPrimaryKey(1);
        // 更新记录
        stock.setStock( stock.getStock() - 1 );

        stockMapper.updateByPrimaryKey(stock);

    }

}