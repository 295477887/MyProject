package com.chen.rmtp;

import com.navinfo.util.ArraysUtils;
import com.navinfo.util.Convert;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author: ChenJie
 * @date 2019/4/4
 */
public class Test1078 {
    public static void main(String[] args) {
        DefaultRtmpPublisher publisher = new DefaultRtmpPublisher();
        //连接 握手
//        publisher.connect("rmtp://172.16.1.86:1935/live/chen");
        publisher.connect("rtmp://10.30.50.195:1935/myapp/stream8");

        //createStream
        publisher.publish("live");

        try {
//            FileInputStream fis = new FileInputStream("F:\\study\\rmtp\\windows\\orange.mp4");
//            FileInputStream fis = new FileInputStream("F:\\study\\rtmp\\windows\\264\\640-1.264");

//            byte [] content = new byte[951];
            int frameId = 1;
            FileReader reader = new FileReader("F:\\study\\rtmp\\windows\\1078\\mini.txt");
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            while((line = br.readLine()) != null){
                line=line.trim();
                if(frameId ==1){
                    line = line.substring(1);
                }

                int dt = Convert.byte2Int(ArraysUtils.subarrays(Convert.hexStringToBytes(line),20,4),4);


                byte [] content = new byte[line.length()+1];
                System.out.println("line=="+line+"==");
                line = line.substring(60);
                int dts = frameId*100;

                if(frameId ==1){
                    ArraysUtils.arrayappend(content,0,Convert.hexStringToBytes("17"));
                }
                else{
                    ArraysUtils.arrayappend(content,0,Convert.hexStringToBytes("27"));
                }
                ArraysUtils.arrayappend(content,1,Convert.hexStringToBytes(line));

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



                publisher.publishVideoData(content,content.length,dt,frameId++);

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
