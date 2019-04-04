package com.chen.test;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.*;

import java.io.PipedInputStream;

/**
 * @author: ChenJie
 * @date 2019/4/4
 */
public class MyConsumer extends Thread {
    private PipedInputStream inputStream;
    private String outputFile;

    public MyConsumer(PipedInputStream inputStream,String outputFile) {
        this.inputStream = inputStream;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {
        System.out.println("==consumer");
//        while (true) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            try {
//                int count = inputStream.available();
//
//                if (count > 0) {
//                    System.out.println("rest product count: " + count);
//                    System.out.println("get product: " + inputStream.read());
//                }
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
        boolean isStart=true;
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
        try {
            grabber.start();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }

        // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1280, 720, 1);

        recorder.setInterleaved(true);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 28
        recorder.setFormat("flv"); // rtmp的类型
        recorder.setFrameRate(25);
        recorder.setPixelFormat(0);

        // 开始取视频源
        recordByFrame(grabber, recorder, isStart);

    }

    private static void recordByFrame(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder, Boolean status)
    {
        try {//建议在线程中使用该方法
            recorder.start();
            Frame frame = null;
            while (status&& (frame = grabber.grabFrame()) != null) {
                recorder.record(frame);
            }
            recorder.stop();
            grabber.stop();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (grabber != null) {
                try {
                    grabber.stop();
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

