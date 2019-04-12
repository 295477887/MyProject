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
//        publisher.connect("rmtp://172.16.1.86:1935/live/chen");
        publisher.connect("rtmp://10.30.50.195:1935/myapp/stream7");

        //createStream
        publisher.publish("live");

        try {
//            FileInputStream fis = new FileInputStream("F:\\study\\rmtp\\windows\\orange.mp4");
            FileInputStream fis = new FileInputStream("F:\\study\\rtmp\\windows\\264\\640-1.264");
            byte [] content = new byte[9913];
            byte [] buf = new byte[9912];
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





//                ActionMessage am = new ActionMessage();
//                am.setVersion(3);
//                MessageBody mb = new MessageBody();
////                String responseURI = "这里是你请求时传过来的responseURI";
////                mb.setResponseURI(responseURI);
////                mb.setTargetURI(responseURI+ MessageIOConstants.RESULT_METHOD);
//
//                mb.setData(content);
//
//                am.addBody(mb);
//
////                XStream xs = getXStream();
////                ActionMessage message = (ActionMessage) xs.fromXML(xml);
//                // if (checkAckMessage(message))
//                // return null;
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//                ActionContext actionContext = new ActionContext();
//                actionContext.setRequestMessage(am);
//
//                AmfMessageSerializer amfMessageSerializer = new AmfMessageSerializer();
//                SerializationContext serializationContext = SerializationContext.getSerializationContext();
//                amfMessageSerializer.initialize(serializationContext, baos, null);
//
//                try {
//                    amfMessageSerializer.writeMessage(am);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//




                publisher.publishVideoData(content,len,dts,frameId++);

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
