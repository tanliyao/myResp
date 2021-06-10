package com.tly.springcloud.controller;

import com.tly.springcloud.entities.CommonResult;
import com.tly.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



/**
 * @author tly
 * @date 2021/6/6 18:47
 */
@RestController
@Slf4j
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> insertPayment(Payment payment) {
//        postForObject
//        return restTemplate.postForObject("http://localhost:8001/payment/create", payment, CommonResult.class);

//        postForEntity
//        ResponseEntity<CommonResult> forEntity = restTemplate.postForEntity("http://localhost:8001/payment/create",payment, CommonResult.class);
//        CommonResult body = forEntity.getBody();
//        return body;

        HttpHeaders httpHeaders = new HttpHeaders();
//        The HTTP header line [tlyè°­: 1234] does not conform to RFC 7230 and has been ignored.另一个服务会报错
//        httpHeaders.add("tly谭","1234");
        httpHeaders.add("tly","1234");
        HttpEntity<Payment> httpEntity = new HttpEntity<Payment>(payment,httpHeaders);

        log.info("***httpEntity.getHeaders***  "+httpEntity.getHeaders());
        log.info("***httpEntity.getBody***  "+httpEntity.getBody());
        CommonResult commonResult = restTemplate.postForObject("http://CLOUD-PAYMENT-SERVICE/payment/create", httpEntity, CommonResult.class);
        log.info("***commonResult***  "+commonResult);
        return commonResult;

    }

    //    @GetMapping("/consumer/payment/get/{id}")
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
//        想要传入map但是没成功
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", id);

//        原始getForObject
        return restTemplate.getForObject("http://CLOUD-PAYMENT-SERVICE/payment/get/"+id,CommonResult.class);

//        CommonResult变map也可以
//        Map map = restTemplate.getForObject("http://localhost:8001/payment/get/"+id, Map.class);
//        System.out.println(map);

//        使用ResponseEntity，除了获得响应体之外，还能获得响应头和状态码
//        return restTemplate.getForObject("http://localhost:8001/payment/get/" + id, CommonResult.class);
//        ResponseEntity<CommonResult> forEntity = restTemplate.getForEntity("http://localhost:8001/payment/get/" + id, CommonResult.class);
//        log.info("***StatusCode***  "+forEntity.getStatusCode());
//        HttpHeaders headers = forEntity.getHeaders();
//        List<String> strings = headers.get("Content-Type");
//        log.info("***Content-Type***  "+strings);
//        CommonResult body = forEntity.getBody();
//        return body;
    }
}
