package com.chen.forward;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.io.PipedInputStream;

/**
 * @author: ChenJie
 * @date 2019/4/10
 */
public class SendThread extends Thread{
    //输入流, 默认缓冲区大小为1024
    private PipedInputStream in = new PipedInputStream();

    public PipedInputStream getIn() {
        return in;
    }

    @Override
    public void run() {
        readMessage();
    }
    private void readMessage() {
        byte [] buf = new byte[1024];
        try {

            boolean isStart=true;
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(in);
            try {
                grabber.start();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }

            // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rtmp://10.30.50.195:1935/myapp/stream5", 600, 400, 0);

            recorder.setInterleaved(true);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 28
            recorder.setFormat("flv"); // rtmp的类型
            recorder.setFrameRate(25);
            recorder.setPixelFormat(0);

            // 开始取视频源
            recordByFrame(grabber, recorder, isStart);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
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
