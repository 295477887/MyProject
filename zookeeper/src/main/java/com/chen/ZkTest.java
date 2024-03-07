package com.chen;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.jboss.netty.handler.codec.compression.ZlibDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * Created by chen on 2019/8/5.
 */
public class ZkTest {
    private static Logger logger = LoggerFactory.getLogger(ZkTest.class);
    private static ZooKeeper zk;
    private static String URI = "node-1:2181,node-2:2181,node-3:2181";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        zk= new ZooKeeper(URI, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                logger.info("watchedEvent.getPath():"+watchedEvent.getPath());
                logger.info("watchedEvent.getState():"+watchedEvent.getState());
                logger.info("watchedEvent.getType():"+watchedEvent.getType());
                //循环监听
                try {
                    String bb = new String(zk.getData("/bb",true,null));
                    logger.info("监听/bb变化=="+bb);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

//        String create = zk.create("/a", "a".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        logger.info("创建节点："+create);

//        Stat exists = zk.exists("/aa", false);
//        logger.info("节点是否存在"+exists);

//        byte[] data = zk.getData("/aa", false, null);
//        logger.info("获取节点数据:"+new String(data));

//        Stat aa = zk.setData("/aa", "我是aa".getBytes(), -1);
//        logger.info("修改："+aa);

//        zk.delete("/a",-1);

        //acl   digest：用户名密码，先添加用户，再设置acl
//        zk.addAuthInfo("digest","chen:0120".getBytes());
//        String a = zk.create("/a", "a".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
//        logger.info("创建："+a);

//        zk.addAuthInfo("digest","chen:chen".getBytes());
//        byte[] data = zk.getData("/aa", false, null);
//        logger.info("获取:"+new String(data));


            String bb = new String(zk.getData("/bb",true,null));
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);




    }
}
