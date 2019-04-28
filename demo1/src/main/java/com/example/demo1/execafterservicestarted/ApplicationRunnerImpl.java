package com.example.demo1.execafterservicestarted;

import com.example.demo1.DemoThirdApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author houxuebo on 2019-04-21 16:05
 **/

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    private static Logger LOGGER = LoggerFactory.getLogger(DemoThirdApplication.class);


    @Override
    @Order(2)
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("通过实现ApplicationRunner接口，在spring boot项目启动后打印参数");
        String[] sourceArgs = args.getSourceArgs();
        for (String arg : sourceArgs) {
            System.out.print(arg + " ");
        }
        System.out.println();
    }

}
