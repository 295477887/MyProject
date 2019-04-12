package com.chen.forward;

import com.chen.message.VideoProperty;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PipedInputStream;

/**
 * @author: ChenJie
 * @date 2019/4/10
 */
public class ForwardThread extends Thread{
    private Logger logger = LoggerFactory.getLogger(ForwardThread.class);
    //输入流, 默认缓冲区大小为1024
    private PipedInputStream in = new PipedInputStream();

    private VideoProperty videoProperty ;
    /**
     * 真实的url
     * */
    private String toUrl;

    public ForwardThread(String toUrl,VideoProperty videoProperty){
        this.toUrl = toUrl;
        this.videoProperty = videoProperty;
    }

    public PipedInputStream getIn() {
        return in;
    }

    @Override
    public void run() {
        readMessage();
    }
    private void readMessage() {
        try {

            boolean isStart=true;
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(in);
            try {
                grabber.start();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }

            // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(toUrl, videoProperty.getWidth(), videoProperty.getHeight(), videoProperty.getAudioChannels());
            recorder.setInterleaved(videoProperty.isInterleaved());
            // 28
            recorder.setVideoCodec(videoProperty.getVideoCodec());
            // rtmp的类型
            recorder.setFormat(videoProperty.getFormat());
            recorder.setFrameRate(videoProperty.getFrameRate());
            recorder.setPixelFormat(videoProperty.getPixelFormat());

            // 开始取视频源
            recordByFrame(grabber, recorder, isStart);

        } catch (Exception e) {
            logger.error("recordFrame error, ",e);
        } finally {
        }
    }


    private void recordByFrame(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder, Boolean status)
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
            logger.error("recordByFrame error, ",e);
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
