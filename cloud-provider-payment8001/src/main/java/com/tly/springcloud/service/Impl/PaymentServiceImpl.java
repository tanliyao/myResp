package com.tly.springcloud.service.Impl;

import com.tly.springcloud.dao.PaymentDao;
import com.tly.springcloud.entities.Payment;
import com.tly.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tly
 * @date 2021/6/6 15:22
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentDao paymentDao;

    public int insertPayment(Payment payment){
        return paymentDao.create(payment);
    }

    public Payment getPayment(Long id){
        return paymentDao.getPaymentById(id);
    }
}
