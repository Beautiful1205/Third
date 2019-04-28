package com.example.demo1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


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
    @RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
    public String hello(@PathVariable  int id) {
        return "Hello, " +  id + "!";
    }

    @ResponseBody
    @ApiOperation(value="say bye", notes="说'再见'")
    @RequestMapping(value = "/bye", method = RequestMethod.GET)
    public String bye() {
        return "Bye!";
    }

    @ResponseBody
    @ApiOperation(value="say Thanks", notes="说'谢谢'")
    @RequestMapping(value = "/thanks", method = RequestMethod.GET)
    public String thanks() {
        return "Thanks!";
    }

}
