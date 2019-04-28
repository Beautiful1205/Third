package com.example.demo3.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo3.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author houxuebo on 2019-04-28 23:01
 **/
public class ServiceTest {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        User user = new User();
        user.setName("LiMing");
        user.setPwd("123");
        user.setAge(25);

        String json = JSON.toJSONString(user);

        JSONObject jsonObject = JSONObject.parseObject(json);

        User user1 = JSONObject.parseObject(json, User.class);

        Map<String, String> map = JSONObject.parseObject(json, Map.class);

        System.out.println(jsonObject);

        System.out.println(jsonObject.getString("name"));
        System.out.println(json);
        System.out.println(user1);
        System.out.println(map);

        System.out.println("--------------------------------------------------");
        User user2 = new User();
        user2.setName("HanMeiMei");
        user2.setPwd("103");
        user2.setAge(20);

        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user2);

        String listString = JSON.toJSONString(list);
        JSONArray jsonArray = JSONArray.parseArray(listString);

        String s0 = jsonArray.getString(0);

        String name = JSONObject.parseObject(jsonArray.getString(1)).getString("name");

        System.out.println(listString);
        System.out.println(name);
        System.out.println(jsonArray);
        System.out.println(s0);


    }

}
