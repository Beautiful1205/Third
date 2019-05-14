package com.example.demo4.controller;

import com.example.demo4.commed.GetProductInfoCommand;
import com.example.demo4.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * @author houxuebo on 2019-05-13 12:20
 */

@RestController
public class getcontroller {
/*
    @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand(fallbackMethod = "onError",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")},
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "5"),
                    @HystrixProperty(name = "maximumSize", value = "5"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })*/
    @ResponseBody
    @RequestMapping(value = "/getProductInfo", method = RequestMethod.GET)
    public String getProductInfo(Long productId) {
        HystrixCommand<ProductInfo> getProductInfoCommand = new GetProductInfoCommand(productId);

        // 通过command执行，获取最新商品数据
        ProductInfo productInfo = getProductInfoCommand.execute();
        System.out.println(productInfo);
        //return "success";
        return productInfo.toString();
    }


}
