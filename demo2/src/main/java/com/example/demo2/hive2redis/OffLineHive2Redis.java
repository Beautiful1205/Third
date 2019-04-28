package com.example.demo2.hive2redis;

import com.lianjia.bigdata.growth.streaming.transfer.job.sdk.config.ConfigUtils;
import com.lianjia.bigdata.growth.streaming.transfer.job.sdk.config.Props;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import com.lianjia.bigdata.growth.streaming.transfer.job.sdk.utils.LogUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author houxuebo on 2019-04-20 19:56
 **/
public class OffLineHive2Redis {
    private static final int exitCode = -1;
    private static final Logger LOGGER = LogUtils.initLogger(OffLineHive2Redis.class);
    private static final String path = "application.properties";


    public static void main(String[] args) throws Exception {
        final String date = args[0];
        String pt = date.substring(0, 8);
        LOGGER.info("pt = " + pt);


        final Props props = ConfigUtils.init(path);
        final String warehouseLocation = props.getProperty("hive.warehouseLocation");
        final String hiveTable = props.getProperty("hive.hiveTable");
        //设置批处理量
        final int batchSize = Integer.parseInt(props.getProperty("batch.Size"));
        final String redisHost = props.getProperty("redis.offLine.host");
        final int redisPort = Integer.parseInt(props.getProperty("redis.offLine.port"));

        final String hql = String.format(props.getProperty("hive.hql"), hiveTable, pt);
        LOGGER.info("hql is {}", hql);

        String columns = "";
        Matcher matcher = Pattern.compile("select\\s(.+)from\\s(.+)where\\s(.*)").matcher(hql);
        if (matcher.find()) {
            columns = matcher.group(1).trim();
        }
        int columnSize = columns.split(",").length;

        /*SparkConf conf = new SparkConf();
        conf.setAppName("OffLineHive2Redis");
        SparkSession spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();*/

        SparkSession spark = SparkSession.builder()
                .appName("OffLineHive2Redis")
                .enableHiveSupport()
                .config("spark.sql.warehouse.dir", warehouseLocation)
                .getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        final Jedis jedis = new Jedis(redisHost, redisPort, 10000, 60000);


        Dataset<Row> sqlDF = spark.sql(hql);
        final Broadcast<Integer> columnSize_broadcast = jsc.broadcast(Integer.valueOf(columnSize));
        final Broadcast<String> pt_broadcast = jsc.broadcast(pt);

        sqlDF.javaRDD().foreachPartition(new VoidFunction<Iterator<Row>>() {
            public void call(Iterator<Row> rows) throws Exception {
                int count = 0;
                Pipeline pipeline = jedis.pipelined();
                while (rows.hasNext()) {
                    Row row = (Row) rows.next();
                    StringBuffer key = new StringBuffer(pt_broadcast.getValue()).append("-");
                    StringBuffer value = new StringBuffer();

                    for (int i = 1; i < columnSize_broadcast.getValue()-2; i++) {
                        key.append(row.get(i)).append("-");
                    }
                    for (int i = columnSize_broadcast.getValue()-2; i < columnSize_broadcast.getValue(); i++) {
                        value.append(row.get(i)).append("-");
                    }

                    String redisKey = key.substring(0, key.length() - 1);
                    String reg = "{\"type\":%s,\"score\":%s}";
                    String redisValue = String.format(reg, value.toString().split("-")[0], value.toString().split("-")[0]);

                    pipeline.set(redisKey, redisValue);
                    count++;

                    if(count % batchSize == 0){
                        pipeline.sync();
                        count = 0;
                    }
                }
                jedis.disconnect();
                jedis.close();
            }
        });



    }

}
