package com.chen.rmtp;

import com.chen.util.ArraysUtils;
import com.chen.util.Convert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author: ChenJie
 * @date 2019/4/4
 */
public class Test {
    public static void main(String[] args) {
        DefaultRtmpPublisher publisher = new DefaultRtmpPublisher();
        //连接 握手
//        publisher.connect("rtmp://172.16.1.86:1935/live/chen");
        publisher.connect("rtmp://10.30.50.195:1935/myapp/stream5");

        //createStream
        publisher.publish("live");

        try {
//            FileInputStream fis = new FileInputStream("F:\\study\\rtmp\\windows\\orange.mp4");
            FileInputStream fis = new FileInputStream("F:\\study\\rtmp\\windows\\test.264");
            byte [] content = new byte[129];
            byte [] buf = new byte[128];
            int len;
            int frameId = 1;
            while((len = fis.read(buf)) != -1){
                int dts = frameId*100;
                ArraysUtils.arrayappend(content,0,Convert.hexStringToBytes("27"));
                ArraysUtils.arrayappend(content,1,buf);
                publisher.publishVideoData(content,len,dts,frameId++);
                Thread.sleep(100);
                System.out.println("==========="+Convert.bytesToHexString(content));
//                if(frameId>3){
//                    break;
//                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
