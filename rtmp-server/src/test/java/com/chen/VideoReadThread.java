package com.chen;

import com.navinfo.util.ArraysUtils;
import com.navinfo.util.Constants;
import com.navinfo.util.Convert;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author: ChenJie
 * @date 2019/4/11
 */
public class VideoReadThread extends Thread{
    private static final int CONTENT_SIZE = 980;
    private static final int BODY_SIZE = 950;
    private int interval = 10;
    private String fileName;
    private String tid;
    private int channelId;
    private String ip;
    private int port;

    public VideoReadThread(String fileName,String tid,int channelId,String ip,int port){
        this.fileName = fileName;
        this.tid = tid;
        this.channelId = channelId;
        this.ip = ip;
        this.port = port;
    }


    @Override
    public void run() {
        byte [] content = new byte[CONTENT_SIZE];
        //头信息
        byte [] header = new byte[30];
        fixHeader(header,tid,channelId);
        try{
            Socket socket = new Socket(ip,port);
            OutputStream os = socket.getOutputStream();
            FileInputStream fis = new FileInputStream(fileName);
            byte [] buf = new byte[BODY_SIZE];
            int length;
            int i=0;
            while((length = fis.read(buf)) != -1){
//                System.out.println("发送=="+Convert.bytesToHexString(buf)+"==!");
                //包序号
                ArraysUtils.arrayappend(header,6,Convert.intTobytes(i++,2));
                //时间戳 ms
                ArraysUtils.arrayappend(header,16,Convert.longTobytes(i*100,8));
                ArraysUtils.arrayappend(content,0,header);
                ArraysUtils.arrayappend(content,30,buf);
                System.out.println("=="+Convert.bytesToHexString(content));
                os.write(content);
                os.flush();
                Thread.sleep(interval);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void fixHeader(byte [] header,String tid,int channelId){
        //帧头标识
        ArraysUtils.arrayappend(header,0, Constants.DELIMITER);
        //v p x cc
        byte [] v = new byte[]{1,0,  0,  0,  0,0,0,1};
        ArraysUtils.arrayappend(header,4, Convert.longTobytes(Convert.binary2Long(v,1),1));
        //M PT
        byte [] m = new byte[]{1,  0,0,0,0,0,0,0};
        ArraysUtils.arrayappend(header,5, Convert.longTobytes(Convert.binary2Long(m,1),1));

        //sim卡号
        ArraysUtils.arrayappend(header,8,Convert.hexStringToBytes(tid));
        //逻辑通道号
        ArraysUtils.arrayappend(header,14,Convert.intTobytes(channelId,1));
        //数据类型 ００００：视频 Ｉ 帧；,０００１：视频 Ｐ 帧；,００１０：视频 Ｂ 帧；,００１１：音频帧；,０１００：透传数据,
        //分包处理标记  ００００：原子包，不可被拆分；,０００１：分包处理时的第一个包；,００１０：分包处理时的最后一个包；,００１１：分包处理时的中间包
        ArraysUtils.arrayappend(header,15,Convert.intTobytes(0,1));

        //Last I Frame Interval
        ArraysUtils.arrayappend(header,24,Convert.longTobytes(100,2));
        //Last Frame Interval
        ArraysUtils.arrayappend(header,26,Convert.longTobytes(100,2));
        //数据体长度
        ArraysUtils.arrayappend(header,28,Convert.longTobytes(BODY_SIZE,2));
    }
}
