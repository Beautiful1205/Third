package com.example.demo3.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo3.constant.Constant;
import org.apache.avro.generic.GenericData;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author houxuebo on 2019-05-14 10:33
 */
public class TestRuleMapping {

    public static void main(String[] args) {
        System.out.println(ruleMapping("23.90", "990001001", "active"));
        System.out.println(new BigDecimal("0.10").compareTo(Constant.FIRST_DISTRIBUTE) >= 0 && new BigDecimal("0.09").compareTo(Constant.FIRST_DISTRIBUTE) < 0);
    }

    public static double ruleMapping(String variable, String bizTypeCode, String action) {

        String redisValue = "";/*"{\"0.01\":0.236,\"0.02\":0.481,\"0.03\":0.721,\"0.04\":1.008," +
                "\"0.05\":1.243,\"0.06\":1.478,\"0.07\":1.708,\"0.08\":1.984,\"0.09\":" +
                "2.209,\"0.10\":2.479," +
                "\"0.11\":2.699,\"0.12\":2.963,\"0.13\":3.221,\"0.14\"" +
                ":3.436,\"0.15\":3.689,\"0.16\":3.941,\"0.17\":4.188,\"0.18\":4.434,\"0.19\":" +
                "4.675,\"0.20\":4.915," +
                "\"0.21\":5.191,\"0.22\":5.425,\"0.23\":5.656,\"0.24\":5.922," +
                "\"0.25\":6.146,\"0.26\":6.408,\"0.27\":6.665,\"0.28\":6.92,\"0.29\":7.171,\"0.30\":7.418," +
                "\"0.31\":7.663,\"0.32\":7.907,\"0.33\":8.185,\"0.34\":8.427,\"0.35\":8.709,\"0.36\":" +
                "8.953,\"0.37\":9.238,\"0.38\":9.526,\"0.39\":9.88,\"0.40\":10.209," +
                "\"0.41\":10.48," +
                "\"0.42\":10.715,\"0.43\":10.978,\"0.44\":11.239,\"0.45\":11.498,\"0.46\":11.752,\"0.47\":12.003,\"0.48\":12.253," +
                "\"0.49\":12.537,\"0.50\":12.815,\"0.51\":13.104,\"0.52\":13.373,\"0.53\":13.676,\"0.54\":13.952,\"0.55\":14.26," +
                "\"0.56\":14.573,\"0.57\":14.887,\"0.58\":15.183,\"0.59\":15.511,\"0.60\":15.82,\"0.61\":16.18,\"0.62\":16.524," +
                "\"0.63\":16.879,\"0.64\":17.27,\"0.65\":17.614,\"0.66\":18.023,\"0.67\":18.422,\"0.68\":18.861,\"0.69\":19.254," +
                "\"0.70\":19.735,\"0.71\":20.185,\"0.72\":20.683,\"0.73\":21.14,\"0.74\":21.66,\"0.75\":22.15,\"0.76\":22.688,\"0.77" +
                "\":23.306,\"0.78\":23.909,\"0.79\":24.521,\"0.80\":25.104,\"0.81\":25.837,\"0.82\":26.552,\"0.83\":27.302," +
                "\"0.84\":28.132,\"0.85\":28.958,\"0.86\":29.893,\"0.87\":30.786,\"0.88\":31.808,\"0.89\":33.024," +
                "\"0.90\":34.35,\"0.91\":35.833,\"0.92\":37.447,\"0.93\":39.479,\"0.94\":41.835,\"0.95\":44.494,\"0.96\":47.526," +
                "\"0.97\":51.719,\"0.98\":57.619,\"0.99\":70.278,\"1.00\":246.886}";
*/

            BigDecimal a1 = new BigDecimal(0);
            BigDecimal a2 = new BigDecimal(0);
            BigDecimal a3 = new BigDecimal(0);
            BigDecimal a4 = new BigDecimal(0);

            switch (action) {
                case Constant.SHOW_ACTION:
                    a1 = new BigDecimal("0.123");
                    a2 = new BigDecimal("0.145");
                    a3 = new BigDecimal("0.188");
                    a4 = new BigDecimal("0.323");
                    break;
                case Constant.ACTIVE_ACTION:
                    a1 = new BigDecimal("1.208");
                    a2 = new BigDecimal("8.552");
                    a3 = new BigDecimal("17.576");
                    a4 = new BigDecimal("44.303");
                    break;
                case Constant.DEL_ACTION:
                    a1 = new BigDecimal("0.123");
                    a2 = new BigDecimal("0.145");
                    a3 = new BigDecimal("0.188");
                    a4 = new BigDecimal("0.323");
                    break;
            }


        BigDecimal score = new BigDecimal(0);

        Map<String, BigDecimal> treeMap = null;
        List<String> list = null;
        if (StringUtils.isNotEmpty(redisValue)) {
            JSONObject jsonObject = JSONObject.parseObject(redisValue);
            Map<String, BigDecimal> hashMap = jsonObject.toJavaObject(Map.class);

            treeMap = new TreeMap<>(hashMap);
            //log.info("规则数据treeMap为{}.", treeMap.toString());

            list = new ArrayList<>(treeMap.keySet());


            for (int i = 1; i < list.size(); ++i) {
                BigDecimal current = new BigDecimal(list.get(i));
                BigDecimal before = new BigDecimal(list.get(i - 1));

                if (current.compareTo(Constant.FIRST_DISTRIBUTE) >= 0 && before.compareTo(Constant.FIRST_DISTRIBUTE) < 0) {
                    a1 = treeMap.get(list.get(i));
                } else if (current.compareTo(Constant.SECOND_DISTRIBUTE) >= 0 && before.compareTo(Constant.SECOND_DISTRIBUTE) < 0) {
                    a2 = treeMap.get(list.get(i));
                } else if (current.compareTo(Constant.THIRD_DISTRIBUTE) >= 0 && before.compareTo(Constant.THIRD_DISTRIBUTE) < 0) {
                    a3 = treeMap.get(list.get(i));
                } else if (current.compareTo(Constant.FOURTH_DISTRIBUTE) >= 0 && before.compareTo(Constant.FOURTH_DISTRIBUTE) < 0) {
                    a4 = treeMap.get(list.get(i));
                }
            }
        }

        System.out.println(a1 + " - " + a2 + " - " + a3 + " - " + a4);

        BigDecimal val = new BigDecimal(variable);
        if (val.compareTo(a1) <= 0) {
            score = Constant.FIRST_STAGE;
        } else if (val.compareTo(a1) > 0 && val.compareTo(a2) <= 0) {
//            对应的函数为((val-a1)/(a2-a1))*(6-4)+4
            score = (val.subtract(a1)).divide((a2.subtract(a1)), 4, BigDecimal.ROUND_HALF_UP).multiply(Constant.GAP_ONE).add(Constant.FIRST_STAGE);
        } else if (val.compareTo(a2) > 0 && val.compareTo(a3) <= 0) {
//            对应的函数为((val-a2)/(a3-a2))*(8-6)+6
            score = (val.subtract(a2)).divide((a3.subtract(a2)), 4, BigDecimal.ROUND_HALF_UP).multiply(Constant.GAP_TWO).add(Constant.SECOND_STAGE);
        } else if (val.compareTo(a3) > 0 && val.compareTo(a4) <= 0) {
//            对应的函数为((val-a3)/(a4-a3))*(9.9-8)+8
            score = (val.subtract(a3)).divide((a4.subtract(a3)), 4, BigDecimal.ROUND_HALF_UP).multiply(Constant.GAP_THREE).add(Constant.THIRD_STAGE);
        } else if (val.compareTo(a4) > 0) {
            score = Constant.FOURTH_STAGE;
        }
        System.out.println("score = " + score);

        return score.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
