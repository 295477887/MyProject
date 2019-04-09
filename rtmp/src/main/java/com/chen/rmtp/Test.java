package com.chen.rmtp;

import com.chen.util.ArraysUtils;
import com.chen.util.Convert;
import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.ActionContext;
import flex.messaging.io.amf.ActionMessage;
import flex.messaging.io.amf.AmfMessageSerializer;
import flex.messaging.io.amf.MessageBody;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
            FileInputStream fis = new FileInputStream("F:\\study\\rtmp\\windows\\264\\test.h264");
            byte [] content = new byte[913];
            byte [] buf = new byte[912];
            int len;
            int frameId = 1;
            while((len = fis.read(buf)) != -1){
                int dts = frameId*100;
                if(frameId ==1){
                    ArraysUtils.arrayappend(content,0,Convert.hexStringToBytes("17"));
                }
                else{
                    ArraysUtils.arrayappend(content,0,Convert.hexStringToBytes("27"));
                }
                ArraysUtils.arrayappend(content,1,buf);





                ActionMessage am = new ActionMessage();
                am.setVersion(3);
                MessageBody mb = new MessageBody();
//                String responseURI = "这里是你请求时传过来的responseURI";
//                mb.setResponseURI(responseURI);
//                mb.setTargetURI(responseURI+ MessageIOConstants.RESULT_METHOD);

                mb.setData(content);

                am.addBody(mb);

//                XStream xs = getXStream();
//                ActionMessage message = (ActionMessage) xs.fromXML(xml);
                // if (checkAckMessage(message))
                // return null;

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                ActionContext actionContext = new ActionContext();
                actionContext.setRequestMessage(am);

                AmfMessageSerializer amfMessageSerializer = new AmfMessageSerializer();
                SerializationContext serializationContext = SerializationContext.getSerializationContext();
                amfMessageSerializer.initialize(serializationContext, baos, null);

                try {
                    amfMessageSerializer.writeMessage(am);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }





                publisher.publishVideoData(content,len,dts,frameId++);
                System.out.println("amf=="+Convert.bytesToHexString(baos.toByteArray()));

                Thread.sleep(100);
                System.out.println("==========="+Convert.bytesToHexString(content));
//                if(frameId>10){
//                    break;
//                }
//                break;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
