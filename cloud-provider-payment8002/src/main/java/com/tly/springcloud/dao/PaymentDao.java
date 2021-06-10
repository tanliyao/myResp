package com.tly.springcloud.dao;

import com.tly.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author tly
 * @date 2021/6/6 15:01
 */
@Mapper
public interface PaymentDao {
    /**
     * 插入Payment
     * @param payment
     * @return payment
     */
    int create (Payment payment);

    /**
     * 安装Payment的Id查询Payment
     * @param id
     * @return Payment
     */
    Payment getPaymentById(@Param("id") Long id);
}
