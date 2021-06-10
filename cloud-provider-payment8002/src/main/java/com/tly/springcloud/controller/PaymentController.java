package com.tly.springcloud.controller;

import com.tly.springcloud.entities.CommonResult;
import com.tly.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.tly.springcloud.service.PaymentService;

/**
 * @author tly
 * @date 2021/6/6 15:31
 */
@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult InsertPayment(@RequestBody Payment payment){
        String serial = payment.getSerial();
        if(serial == null){
            payment.setSerial("默认值");

        }
        int result = paymentService.insertPayment(payment);

        log.info("***Payment插入结果"+result);
        if(result > 0){
            return new CommonResult(200,"插入Payment数据成功,serverPort "+serverPort,result);
        }else {
            return new CommonResult(444,"插入Payment数据失败,serverPort "+serverPort,null);
        }
    }



    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPayment(@PathVariable("id") Long paymentId){
        Payment payment = paymentService.getPayment(paymentId);
        log.info("***Payment查询结果"+payment);
        if(payment != null ){
            return new CommonResult(200,"查询Payment数据成功,serverPort "+serverPort,payment);
        }else {
            return new CommonResult(444,"按照Id查询Payment数据失败,serverPort "+serverPort,null);
        }
    }

//    @GetMapping(value = "/payment/get2/{id}")
//    public ResponseEntity getPayment2(@PathVariable("id") Long paymentId){
//        Payment payment = paymentService.getPayment(paymentId);
//        log.info("***Payment查询结果"+payment);
//        ResponseEntity.BodyBuilder ok = ResponseEntity.ok();
//        ResponseEntity.BodyBuilder fpo = ok.header("custom-heads", "fpo");
//
//        return ResponseEntity.ok().header("custom-heads","fpo").body("custom bofy");
//    }

}
