package com.example.demo4.commed;

import com.alibaba.fastjson.JSONObject;
import com.example.demo4.model.ProductInfo;
import com.example.demo4.util.HttpClientUtil;
import com.netflix.hystrix.*;

import java.util.concurrent.TimeUnit;

/**
 * @author houxuebo on 2019-05-13 12:22
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

    private Long productId;

    public GetProductInfoCommand(Long productId) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("testCommandGroupKey"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("testCommandKey"))
                /* 使用HystrixThreadPoolKey工厂定义线程池名称*/
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("testThreadPool"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//                		.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)	// 信号量隔离
                        .withExecutionTimeoutInMilliseconds(5000)));
//		HystrixCommandProperties.Setter().withCircuitBreakerEnabled(true);
//		HystrixCollapserProperties.Setter()
//		HystrixThreadPoolProperties.Setter().withCoreSize(1);
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() {

        String url = "http://localhost:8081/say/hello?id=" + productId;
        // 调用商品服务接口
        String response = HttpClientUtil.sendGetRequest(url, null);
        return JSONObject.parseObject(response, ProductInfo.class);
    }

    @Override
    protected ProductInfo getFallback() {
        System.out.println("服务降级！");
        //return super.getFallback();
        return null;
    }
}