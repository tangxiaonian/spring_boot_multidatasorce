package com.tang.mulit.datasource.test1.mapper;


import com.tang.mulit.datasource.test1.domain.Order;

/**
 * @Classname ${NAME}
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/30 20:30
 * @Created by ASUS
 */

public interface OrderMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}