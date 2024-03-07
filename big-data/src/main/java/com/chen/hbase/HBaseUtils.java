package com.chen.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import sun.awt.ConstrainableGraphics;

import java.io.IOException;

/**
 * Created by chen on 2019/8/8.
 */
public class HBaseUtils {
    private static final String ZK_CONNECT_KEY = "hbase.zookeeper.quorum";
    private static final String ZK_CONNECT_VALUE = "node-1:2181,node-2:2181,node-3:2181";
    private static Connection conn = null;
    private static Admin admin = null;

    public static void main(String[] args) throws IOException {
        getConnection();
        getAdmin();
        getAllTables();

    }

    private static Connection getConnection() {
        Configuration conf = HBaseConfiguration.create();
        conf.set(ZK_CONNECT_KEY,ZK_CONNECT_VALUE);
        try {
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Admin getAdmin() {
        try {
            admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public static void getAllTables() throws IOException {
        HTableDescriptor [] listTables = admin.listTables();
        for(HTableDescriptor table : listTables){
            String tableName = table.getNameAsString();
            System.out.print("表:"+tableName);
            HColumnDescriptor[] columnFamilies = table.getColumnFamilies();
            for(HColumnDescriptor col : columnFamilies){
                String colFamily = col.getNameAsString();
                System.out.print("\t 列族:"+colFamily);
            }
            System.out.println();
        }
    }
}
