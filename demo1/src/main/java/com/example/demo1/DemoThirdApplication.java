package com.example.demo1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoThirdApplication {
    private static Logger LOGGER = LoggerFactory.getLogger(DemoThirdApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoThirdApplication.class, args);
        LOGGER.info("应用启动成功...");
    }
}
