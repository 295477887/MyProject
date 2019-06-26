package com.chen.test;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by chen on 2019/6/26.
 */
public class TestHadoop {
    private FileSystem fs;

    @Before
    public void init(){
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://node-1:9000");

        //通过如下的方式进行客户端身份的设置
        System.setProperty("hadoop.home.dir","G:\\study\\bigdata\\hadoop-2.7.4");
        System.setProperty("HADOOP_USER_NAME", "chen");


        //通过 FileSystem 的静态方法获取文件系统客户端对象
        try{
            fs = FileSystem.get(conf);
            //也可以通过如下的方式去指定文件系统的类型 并且同时设置用户身份
            //FileSystem fs = FileSystem.get(new URI("hdfs://node-21:9000"), conf, "root");
            //创建一个目录
            fs.mkdirs(new Path("/hdfs-java"));
            //上传一个文件
            fs.copyFromLocalFile(new Path("G:\\study\\bigdata\\hadoop-2.7.4\\etc\\hadoop\\core-site.xml"), new Path("/hdfs-java"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 上传文件
     * */
    @Test
    public void testUpload(){
        try {
            //创建一个目录
            fs.mkdirs(new Path("/hdfs-java"));
            //上传一个文件
            fs.copyFromLocalFile(new Path("G:\\study\\bigdata\\hadoop-2.7.4\\etc\\hadoop\\core-site.xml"), new Path("/hdfs-java"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 流式上传
     * */
    @Test
    public void testStreamUpload(){
        try {
            FSDataOutputStream os = fs.create(new Path("/hdfs-java/1.txt"),true);
            FileInputStream fis = new FileInputStream("G:\\study\\bigdata\\hadoop-2.7.4\\etc\\hadoop\\core-site.xml");
            IOUtils.copy(fis,os);
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDownload(){
        try {
            fs.copyToLocalFile(new Path("/hdfs-java/1.txt"),new Path("d://1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
