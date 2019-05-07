package cn.edu.bupt.zzy.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: HBaseUtils
 * @description: HBase操作工具类
 * @author: zzy
 * @date: 2019-05-07 10:24
 * @version: V1.0
 **/
public class HBaseUtils {

    HBaseAdmin admin = null;
    Configuration configuration = null;

    /**
     * 私有构造方法
     */
    private HBaseUtils() {
        configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", "hadoop000:2181");
        configuration.set("hbase.rootdir", "hdfs://hadoop000:8020/hbase");

        try (Connection connection = ConnectionFactory.createConnection(configuration)) {
            //admin = new HBaseAdmin(configuration);
            admin = (HBaseAdmin) connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HBaseUtils instance = null;

    /**
     * 单例模式进行实例化
     */
    public static synchronized HBaseUtils getInstance() {
        if (null == instance) {
            instance = new HBaseUtils();
        }
        return instance;
    }

    /**
     * 根据表名获取到HTable实例
     * @param tableName 表名
     * @return
     */
    public HTable getTable(String tableName) {

        HTable table = null;

        try {
            table = new HTable(configuration, tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

    /**
     * 添加一条记录到HBase表
     * @param tableName HBase表名
     * @param rowkey HBase表的rowkey
     * @param cf HBase表的columnfamily
     * @param column HBase表的列
     * @param value 写入HBase表的值
     */
    public void put(String tableName, String rowkey, String cf, String column, String value) {
        HTable table = getTable(tableName);

        Put put = new Put(Bytes.toBytes(rowkey));
        put.add(Bytes.toBytes(cf), Bytes.toBytes(column), Bytes.toBytes(value));

        try {
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据表名和输入条件获取HBase的记录数
     * @return
     */
    public Map<String, Long> query(String tableName, String condition) throws Exception {

        Map<String, Long> map = new HashMap<>();

        HTable table = getTable(tableName);
        String cf = "info";
        String qualifier = "click_count";

        Scan scan = new Scan();

        Filter filter = new PrefixFilter(Bytes.toBytes(condition));

        scan.setFilter(filter);

        ResultScanner rs = table.getScanner(scan);
        for (Result result : rs) {
            String row = Bytes.toString(result.getRow());
            long clickCount = Bytes.toLong(result.getValue(cf.getBytes(), qualifier.getBytes()));
            map.put(row, clickCount);
        }

        return map;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Long> map = HBaseUtils.getInstance().query("my_course_clickcount", "20190506");

        for (Map.Entry<String, Long> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

}
