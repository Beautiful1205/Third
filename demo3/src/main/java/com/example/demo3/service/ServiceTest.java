package com.example.demo3.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.example.demo3.Model.User;
import com.google.common.base.Strings;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author houxuebo on 2019-04-28 23:01
 **/
public class ServiceTest {


    public static void main(String[] args) {
//        test0();
//        test();
        String[] strings = {
                "00000000-0000-0000-0000-000000000000_DB3E7306-E22F-4D59-A6C5-E89B357A8012",
                "2000000040133424",
                "C5FA7901-3FAB-4845-BE5D-BBC892A3272E",
                "7DA00810-55E0-4901-99DB-92B62378926E",
                "olvA55GsMFzYsMSgCg6a5RAB8uPM"
        };
        for (String arg : strings) {
            testBytes(arg);
        }

    }


    public static void testBytes(String str) {
        System.out.println(str + "字节大小为: " + str.getBytes().length);
    }

    public static void test0() {
        Date dayNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayNow);
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        Date dayBefore = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
        String beforeDay = simpleDateFormat.format(dayBefore);

        String preStr = "o-" + beforeDay;
        String string = preStr + "*";
        System.out.println(string);
        System.out.printf("");
        System.out.println("ServiceTest.test0");
        System.out.println("string = " + string);

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

        //过滤某些属性
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(User.class, "name", "pwd");
        String jsonStu = JSON.toJSONString(user, filter);

        System.out.println(jsonStu);

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

        String name = JSONObject.parseObject(jsonArray.getString(1)).getString("nam");

        System.out.println(listString);
        System.out.println(name);
        System.out.println(jsonArray);
        System.out.println(s0);


        System.out.println("--------------------------------------------------");


    }

}
