package com.example.demo1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author houxuebo on 2019-04-20 16:15
 **/
@RestController
@RequestMapping("/say")
public class TestController {

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "Hello!";
    }

    @ResponseBody
    @RequestMapping(value = "/bye", method = RequestMethod.GET)
    public String bye(){
        return "Bye!";
    }

    @ResponseBody
    @RequestMapping(value = "/thanks", method = RequestMethod.GET)
    public String thanks(){
        return "Thanks!";
    }


}
