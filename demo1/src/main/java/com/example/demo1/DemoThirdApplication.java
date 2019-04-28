package com.example.demo1;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

@SpringBootApplication
@EnableSwagger2
public class DemoThirdApplication {
    private static Logger LOGGER = LoggerFactory.getLogger(DemoThirdApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoThirdApplication.class, args);
        LOGGER.info("应用启动成功...");

        final Jedis jedis = new Jedis("10.26.21.3", 11111, 10000, 60000);
        //jedis.auth("0");
        jedis.select(3);

        String tablesValue = jedis.get("evtid_weight");
        JSONObject jsonObject = JSONObject.parseObject(tablesValue);
        Map<String, Double> tables = jsonObject.toJavaObject(jsonObject, Map.class);

        System.out.println("evt_id and weight = " + tables);

    }
}
