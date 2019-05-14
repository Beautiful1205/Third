package com.example.demo1.controller;

import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


/**
 * @author houxuebo on 2019-04-20 16:15
 *
 * 访问  http://localhost:8080/swagger-ui.html
 **/
@RestController
@RequestMapping("/say")
@Api(value="TestController",tags={"打招呼操作接口"})
public class TestController {

    @ResponseBody
    @ApiOperation(value="say hello", notes="说'你好'")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello( int id) {
        try {
            TimeUnit.MILLISECONDS.sleep(4000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
//        return "Hello, " +  id + "!";
        return "{\"productId\":\""+ id + "\",\"productName\":\"goods\"}";
    }

    @ResponseBody
    @ApiOperation(value="say bye", notes="说'再见'")
    @RequestMapping(value = "/bye", method = RequestMethod.GET)
    public String bye(String id,String name) {
        Preconditions.checkNotNull(name, name + "为空！");
        return "Bye! " + id + name;
    }

    @ResponseBody
    @ApiOperation(value="say Thanks", notes="说'谢谢'")
    @RequestMapping(value = "/thanks", method = RequestMethod.GET)
    public String thanks() {
        return "Thanks!";
    }

}
