//package com.example.demo2.hive2redis;
//
///**
// * @author houxuebo on 2019-04-20 20:28
// **/
//import org.apache.hadoop.hive.ql.exec.Description;
//import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
//import org.apache.hadoop.hive.ql.metadata.HiveException;
//import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
//import org.apache.hadoop.hive.serde2.objectinspector.*;
//import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
//import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
//import org.apache.hadoop.io.IntWritable;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.Pipeline;
//
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Description(name = "redis_batch_hset",
//        value = "_FUNC_(host_and_port,keyField, array<map>) - Return ret "
//)
//public class RedisBatchHSetUDF extends GenericUDF {
//
//    private HostAndPort hostAndPort;
//    private String keyField;
//    private Object writableKeyField; //实际上是org.apache.hadoop.io.Text类型
//    private StandardListObjectInspector paramsListInspector;
//    private StandardMapObjectInspector paramsElementInspector;
//
//    @Override
//    public Object evaluate(DeferredObject[] arg0) throws HiveException {
//
//        try (
//                Jedis jedis = new Jedis(hostAndPort.getHost(), hostAndPort.getPort(), 10000, 60000);
//                Pipeline pipeline = jedis.pipelined()
//        ) {
//            for (int i = 0; i < paramsListInspector.getListLength(arg0[2].get()); i++) {
//                Object row = paramsListInspector.getListElement(arg0[2].get(), i);
//                Map<?, ?> map = paramsElementInspector.getMap(row);
////                Object obj = ObjectInspectorUtils.copyToStandardJavaObject(row,paramsElementInspector); //转成标准的java map，否则里面的key value字段为hadoop writable对象
//                if (map.containsKey(writableKeyField)) {
//                    String did = map.get(writableKeyField).toString();
//                    Map<String, String> data = new HashMap<>();
//                    for (Map.Entry<?, ?> entry : map.entrySet()) {
//                        if (!writableKeyField.equals(entry.getKey()) && entry.getValue() != null && !"".equals(entry.getValue().toString())) {
//                            data.put(entry.getKey().toString(), entry.getValue().toString());
//                        }
//                    }
//                    pipeline.hmset(did,data);
//                }
//            }
//            pipeline.sync();
//            return new IntWritable(1);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new HiveException(e);
//        }
//
//    }
//
//
//    @Override
//    public String getDisplayString(String[] arg0) {
//        return "redis_batch_hset(redishost_and_port,keyField, array<map<string,string>>)";
//    }
//
//
//    @Override
//    public ObjectInspector initialize(ObjectInspector[] arg0)
//            throws UDFArgumentException {
//        if (arg0.length != 3) {
//            throw new UDFArgumentException(" Expecting   two  arguments:<redishost:port>  <keyField> array<map<string,string>> ");
//        }
//        //第一个参数校验
//        if (arg0[0].getCategory() == Category.PRIMITIVE
//                && ((PrimitiveObjectInspector) arg0[0]).getPrimitiveCategory() == PrimitiveObjectInspector.PrimitiveCategory.STRING) {
//            if (!(arg0[0] instanceof ConstantObjectInspector)) {
//                throw new UDFArgumentException("redis host:port  must be constant");
//            }
//            ConstantObjectInspector redishost_and_port = (ConstantObjectInspector) arg0[0];
//
//            String[] host_and_port = redishost_and_port.getWritableConstantValue().toString().split(":");
//            hostAndPort = new HostAndPort(host_and_port[0], Integer.parseInt(host_and_port[1]));
//        }
//
//        //第2个参数校验
//        if (arg0[1].getCategory() == Category.PRIMITIVE
//                && ((PrimitiveObjectInspector) arg0[1]).getPrimitiveCategory() == PrimitiveObjectInspector.PrimitiveCategory.STRING) {
//            if (!(arg0[1] instanceof ConstantObjectInspector)) {
//                throw new UDFArgumentException("redis hset key   must be constant");
//            }
//            ConstantObjectInspector keyFieldOI = (ConstantObjectInspector) arg0[1];
//
//            keyField = keyFieldOI.getWritableConstantValue().toString();
//            writableKeyField = keyFieldOI.getWritableConstantValue();
//        }
//
//        //第3个参数校验
//        if (arg0[2].getCategory() != Category.LIST) {
//            throw new UDFArgumentException(" Expecting an array<map<string,string>> field as third argument ");
//        }
//        ListObjectInspector third = (ListObjectInspector) arg0[2];
//        if (third.getListElementObjectInspector().getCategory() != Category.MAP) {
//            throw new UDFArgumentException(" Expecting an array<map<string,string>> field as third argument ");
//        }
//        paramsListInspector = ObjectInspectorFactory.getStandardListObjectInspector(third.getListElementObjectInspector());
//        paramsElementInspector = (StandardMapObjectInspector) third.getListElementObjectInspector();
//        System.out.println(paramsElementInspector.getMapKeyObjectInspector().getCategory());
//        System.out.println(paramsElementInspector.getMapValueObjectInspector().getCategory());
//
//        return PrimitiveObjectInspectorFactory.writableIntObjectInspector;
//    }
//
//}