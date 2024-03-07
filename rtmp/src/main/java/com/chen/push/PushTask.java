package com.chen.push;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.*;

import java.io.*;

public class PushTask extends Thread {

    private volatile boolean stop = false;

    private String name;

    private FFmpegFrameGrabber grabber;
    private FFmpegFrameRecorder recorder;

    private PipedOutputStream pos;
    private PipedInputStream pis;

    private ByteArrayInputStream is = new ByteArrayInputStream(new byte[1000]);


    private ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public PushTask(String name) throws IOException {
        this.name = name;
        pos = new PipedOutputStream();
        pis = new PipedInputStream(500);
        pis.connect(pos);
    }

    @Override
    public void run() {
        try {

            grabber = new FFmpegFrameGrabber(is);
            grabber.start();

//            recorder = new FFmpegFrameRecorder("rmtp://127.0.0.1/live/" + name, 352,288,0);
            recorder = new FFmpegFrameRecorder("rmtp://10.30.50.195:1935/myapp/stream5", 352,288,0);
//            recorder = new FFmpegFrameRecorder("rmtp://172.16.1.86:1935/live/chen", 352,288,0);
            recorder.setInterleaved(true);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 28
            recorder.setFormat("flv"); // rmtp???
            recorder.setFrameRate(25);
            recorder.setPixelFormat(0); // yuv420p

            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        while (!stop && !this.isInterrupted()) {
//            try {
//                Frame frame = grabber.grab();
//                recorder.record(frame);
//            } catch (FrameGrabber.Exception e) {
//                e.printStackTrace();
//            } catch (FrameRecorder.Exception e) {
//                e.printStackTrace();
//            }
//        }
        Frame frame =null ;
        try {
            while ((frame = grabber.grabFrame()) != null) {
                recorder.record(frame);
            }
            recorder.stop();
            grabber.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        }


    }

    public void write(byte[] buff) throws IOException {
        bos.write(buff);

        is = new ByteArrayInputStream(buff);

    }

    public void flush() throws IOException {
        bos.flush();
        pos.write(bos.toByteArray());
        pos.flush();
        bos.reset();
    }

    public void shutdown() throws FrameGrabber.Exception, FrameRecorder.Exception {
        grabber.stop();
        recorder.stop();
        stop();
    }
}
