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
//            FileReader reader = new FileReader("F:\\study\\rtmp\\windows\\1078\\rtmp.txt");
            FileReader reader = new FileReader("D:\\Program Files (x86)\\SecureCRT\\download\\send.log");
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            while((line = br.readLine()) != null){
                line=line.trim();
                if(frameId ==1){
                    line = line.substring(1);
                }

//                int dt = Convert.byte2Int(ArraysUtils.subarrays(Convert.hexStringToBytes(line),20,4),4);


                byte [] content = new byte[line.length()+9];
//                byte [] body = Convert.hexStringToBytes(line.substring(60));
                byte [] body = Convert.hexStringToBytes(line);
                int i=0;
                int bodySize = body.length;
                int dts = frameId*100;

                int frameFlag = Convert.byte2Int(ArraysUtils.subarrays(body,4,1),1);
                //I帧 先发sps pps,再发I帧
                if(frameFlag == 0x67){
                    byte [] spspps = new byte[33];
                    String spsStr = "4200298D8D40B04B403C22114E";
                    String ppsStr = "CA43C8";
                    byte [] sps = ArraysUtils.subarrays(body,5,13);
                    //FrameType: 1(key frame) + CodeID: 7(AVC)
                    ArraysUtils.arrayappend(spspps,0,Convert.hexStringToBytes("17"));
                    //AVCPackType == 0, AVC sequence header
                    ArraysUtils.arrayappend(spspps,1,Convert.hexStringToBytes("00"));
                    //Composition Time
                    ArraysUtils.arrayappend(spspps,2,Convert.hexStringToBytes("000000"));
                    //AVCDecoderConfigurationRecord
                    ArraysUtils.arrayappend(spspps,5,Convert.hexStringToBytes("1"));
                    //sps
                    ArraysUtils.arrayappend(spspps,6,Convert.hexStringToBytes(spsStr.substring(0,2)));
                    ArraysUtils.arrayappend(spspps,7,Convert.hexStringToBytes(spsStr.substring(2,4)));
                    ArraysUtils.arrayappend(spspps,8,Convert.hexStringToBytes(spsStr.substring(4,6)));
                    ArraysUtils.arrayappend(spspps,9,Convert.hexStringToBytes("FF"));
                    ArraysUtils.arrayappend(spspps,10,Convert.hexStringToBytes("E1"));
                    //sps_len
                    ArraysUtils.arrayappend(spspps,11,Convert.intTobytes((13 >> 8) & 0xff,1));
                    ArraysUtils.arrayappend(spspps,12,Convert.intTobytes(13 & 0xff,1));
                    //sps
                    ArraysUtils.arrayappend(spspps,14,sps);

                    //PPS
                    byte [] pps = ArraysUtils.subarrays(body,23,3);
                    ArraysUtils.arrayappend(spspps,27,Convert.hexStringToBytes("1"));
                    ArraysUtils.arrayappend(spspps,28,Convert.intTobytes((3 >> 8) & 0xff,1));
                    ArraysUtils.arrayappend(spspps,29,Convert.intTobytes(3 & 0xff,1));
                    ArraysUtils.arrayappend(spspps,30,pps);
                    publisher.publishVideoData(spspps,spspps.length,0,1);

                    ArraysUtils.arrayappend(content,0,Convert.hexStringToBytes("17"));

                }
                //P帧，直接发送
                else if(frameFlag == 0x41){
                    ArraysUtils.arrayappend(content,0,Convert.hexStringToBytes("27"));
                }
                ArraysUtils.arrayappend(content,1,Convert.hexStringToBytes("1"));
                ArraysUtils.arrayappend(content,2,Convert.hexStringToBytes("0"));
                ArraysUtils.arrayappend(content,3,Convert.hexStringToBytes("0"));
                ArraysUtils.arrayappend(content,4,Convert.hexStringToBytes("0"));
                ArraysUtils.arrayappend(content,5,Convert.intTobytes(bodySize>>24 &0xff,1));
                ArraysUtils.arrayappend(content,6,Convert.intTobytes(bodySize>>16 &0xff,1));
                ArraysUtils.arrayappend(content,7,Convert.intTobytes(bodySize>>8  &0xff,1));
                ArraysUtils.arrayappend(content,8,Convert.intTobytes(bodySize     &0xff,1));


//                if(frameId ==1){
//                    ArraysUtils.arrayappend(content,0,Convert.hexStringToBytes("17"));
//                }
//                else{
//                    ArraysUtils.arrayappend(content,0,Convert.hexStringToBytes("27"));
//                }
                ArraysUtils.arrayappend(content,9,Convert.hexStringToBytes(line));

                System.out.println("content=="+content+"==");

                publisher.publishVideoData(content,content.length,dts,frameId++);
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
