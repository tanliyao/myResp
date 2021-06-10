package com.tly.springcloud.service;

import com.tly.springcloud.entities.Payment;
import org.springframework.stereotype.Service;

/**
 * @author tly
 * @date 2021/6/6 15:21
 */
public interface PaymentService {
    /**
     * 插入Payment
     * @param payment
     * @return 影响行数
     */
    int insertPayment(Payment payment);

    /**
     * 按照PaymentID查询Payment
     * @param id
     * @return Payment
     */
    Payment getPayment(Long id);
}
