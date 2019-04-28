package com.example.demo1.execafterservicestarted;

import com.example.demo1.DemoThirdApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author houxuebo on 2019-04-21 16:06
 **/
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static Logger LOGGER = LoggerFactory.getLogger(DemoThirdApplication.class);

    @Override
    @Order(1)
    public void run(String... args) throws Exception {
        LOGGER.info("通过实现CommandLineRunner接口，在spring boot项目启动后打印参数");
        for (String arg : args) {
            System.out.print(arg + " ");
        }
        System.out.println();
    }
}
