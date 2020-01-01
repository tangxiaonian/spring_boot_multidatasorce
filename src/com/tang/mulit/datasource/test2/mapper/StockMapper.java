package com.tang.mulit.datasource.test2.mapper;

import com.tang.mulit.datasource.test2.domain.Stock;

/**
 * @Classname ${NAME}
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/23 19:26
 * @Created by ASUS
 */
public interface StockMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);
}