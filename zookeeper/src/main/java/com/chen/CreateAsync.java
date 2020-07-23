package com.chen;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by chen on 2019/8/6.
 */
public class CreateAsync implements Watcher {
    private static ZooKeeper zooKeeper = null;

    private CreateAsync() {}

    public CreateAsync(String connectString, int sessionTimeout) throws IOException, InterruptedException {
        System.out.println("创建客户端，并连接......");
        zooKeeper =  new ZooKeeper(connectString, sessionTimeout, new CreateAsync());
        TimeUnit.SECONDS.sleep(1);
    }


    /**
     * 返回的event对象主要有两部分构成，keeperstate(状态)和eventtype（事件类型）,
     * eventtype=none时session状态会发生改变
     */
    @Override
    public void process(WatchedEvent event) {
        System.out.println("获取到监听事件：" + event);
        if(event.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("正在处理相关业务......");
            doSomething();
        }
    }

    private void doSomething() {
        zooKeeper.create("/task-",
                "task".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT_SEQUENTIAL,
                new IStringCallBack(),
                "async");
    }

    static class IStringCallBack implements AsyncCallback.StringCallback {

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    System.out.println("CONNECTIONLOSS");
                    break;
                case OK:
                    System.out.println("OK - {" + path + ", " + name + ", " + ctx + "}");
                    break;
                default:
                    System.out.println("DEFAULT");
                    break;
            }
        }

    }

}
