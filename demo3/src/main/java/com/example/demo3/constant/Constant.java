package com.example.demo3.constant;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class Constant {
    public final static String BIGC_TABLE = "logs_bigc_all";
    public final static String LIANJIA_TABLE = "logs_lianjia_all";
    public final static String LINK_TABLE = "logs_link_all";
    public final static String COMMON_TABLE = "logs_common_all";
    public final static String BIGC_APP_CODE= "bigc";
    public final static String LIAN_APP_CODE= "lianjia";
    public final static String ALLIANCE_APP_CODE= "alliance";
    public final static String LINK_APP_CODE= "link";

    //hbase
    public static final String ZKQUOEUM = "hbase.zookeeper.quorum";
    public static final String CLIRETNUM = "hbase.client.retries.number";
    public static final String CLIPAUS = "hbase.client.pause";

    public static final BigDecimal FIRST_STAGE = new BigDecimal("4.00");
    public static final BigDecimal SECOND_STAGE = new BigDecimal("6.00");
    public static final BigDecimal THIRD_STAGE = new BigDecimal("8.00");
    public static final BigDecimal FOURTH_STAGE = new BigDecimal("9.90");

    public static final BigDecimal GAP_ONE = SECOND_STAGE.subtract(FIRST_STAGE);
    public static final BigDecimal GAP_TWO = SECOND_STAGE.subtract(FIRST_STAGE);
    public static final BigDecimal GAP_THREE = FOURTH_STAGE.subtract(THIRD_STAGE);

    public static final BigDecimal FIRST_DISTRIBUTE = new BigDecimal("0.05");
    public static final BigDecimal SECOND_DISTRIBUTE = new BigDecimal("0.35");
    public static final BigDecimal THIRD_DISTRIBUTE = new BigDecimal("0.65");
    public static final BigDecimal FOURTH_DISTRIBUTE = new BigDecimal("0.95");

    public static final String CHARACTER = "\\N";

    public static final String SHOW_ACTION = "show";
    public static final String ACTIVE_ACTION = "active";
    public static final String DEL_ACTION = "del";
}
