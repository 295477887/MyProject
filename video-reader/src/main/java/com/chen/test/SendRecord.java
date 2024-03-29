package com.chen.test;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.*;

/**
 * @author: ChenJie
 * @date 2019/4/3
 */
public class SendRecord {
    public static void main(String[] args)
            throws Exception {

//        String inputFile = "F:\\study\\rtmp\\windows\\orange.mp4";
        String inputFile = "F:\\study\\rtmp\\windows\\264\\320.264";
//
        String outputFile = "rtmp://10.30.50.195:1935/myapp/stream5";


//        String inputFile = "rtmp://10.30.50.195:1935/myapp/stream5";

//        String outputFile = "F:\\orange.mp4";

        frameRecord(inputFile, outputFile,1);
    }

    /**
     * 按帧录制视频
     *
     * @param inputFile-该地址可以是网络直播/录播地址，也可以是远程/本地文件路径
     * @param outputFile
     *            -该地址只能是文件地址，如果使用该方法推送流媒体服务器会报错，原因是没有设置编码格式
     * @throws FrameGrabber.Exception
     * @throws FrameRecorder.Exception
     * @throws FrameRecorder.Exception
     */
    public static void frameRecord(String inputFile, String outputFile, int audioChannel)
            throws Exception, FrameRecorder.Exception {

        boolean isStart=true;//该变量建议设置为全局控制变量，用于控制录制结束
        // 获取视频源
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1280, 720, audioChannel);

        recorder.setInterleaved(true);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 28
        recorder.setFormat("flv"); // rtmp的类型
        recorder.setFrameRate(25);
        recorder.setPixelFormat(0);

        // 开始取视频源
        recordByFrame(grabber, recorder, isStart);
    }

    private static void recordByFrame(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder, Boolean status)
            throws Exception, FrameRecorder.Exception {
        try {//建议在线程中使用该方法
            grabber.start();
            recorder.start();
            Frame frame = null;
            while (status&& (frame = grabber.grabFrame()) != null) {
                recorder.record(frame);
            }
            recorder.stop();
            grabber.stop();
        } finally {
            if (grabber != null) {
                grabber.stop();
            }
        }
    }



}
