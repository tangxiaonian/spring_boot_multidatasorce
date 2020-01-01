package com.tang.mulit.datasource.test2.domain;

import lombok.Data;

/**
 * @Classname ${NAME}
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/23 19:26
 * @Created by ASUS
 */
@Data
public class Stock {
    private Integer id;

    /**
    * 商品ID
    */
    private Integer commodityId;

    /**
    * 库存余额
    */
    private Integer stock;
}