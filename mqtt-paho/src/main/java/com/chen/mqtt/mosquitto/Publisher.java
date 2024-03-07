package com.chen.mqtt.mosquitto;

import com.google.common.base.Stopwatch;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.TimeUnit;

/**
 * @author: ChenJie
 * @date 2018/8/10
 */
public class Publisher {
    //tcp://MQTT安装的服务器地址:MQTT定义的端口号
    public static final String HOST = "tcp://172.16.1.223:1883";
    //定义一个主题
    public static final String TOPIC = "P/123";
    //定义MQTT的ID，可以在MQTT服务配置中指定
    private static final String clientid = "server84";

    private MqttClient client;
    private MqttTopic topic;
    private String userName = "mosquitto";
    private String password = "";
    private MqttMessage message;

    /**
     * 构造函数
     * @throws MqttException
     */
    public Publisher() throws MqttException {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        connect();
    }

    private void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
//        options.setUserName(userName);
//        options.setPassword(password.toCharArray());
        //超时时长
        options.setConnectionTimeout(100);
        //心跳时长
        options.setKeepAliveInterval(20);
        options.setServerURIs(new String[]{HOST}  );
        try{
            client.setCallback(new PushCallback() );
            client.connect(options);
            topic = client.getTopic(TOPIC);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void publish(MqttTopic topic,MqttMessage message) throws MqttException {
        MqttDeliveryToken token = topic.publish(message);

//        System.out.println("等待发送成功:"+token.isComplete());
        token.waitForCompletion();
//        System.out.println("已经发送成功:"+token.isComplete());
    }

    public static void main(String[] args) throws MqttException {

        send();




    }

    public static void send() throws MqttException {
        Publisher server = new Publisher();
        server.message = new MqttMessage();
        server.message.setQos(2);
        server.message.setRetained(false);
        Stopwatch stopwatch =Stopwatch.createStarted();

        for(int i=0;i<10000;i++){
            server.message.setPayload((i+"===========================" +
                    "========================================================= "+i).getBytes());
            server.publish(server.topic,server.message);
//            System.out.println("=======================================================");
        }
        stopwatch.stop();
        System.out.println("耗时="+stopwatch.elapsed(TimeUnit.MILLISECONDS));
        System.exit(0);
    }


}
