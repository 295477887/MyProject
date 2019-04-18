package com.chen.handler;

import com.navinfo.message.RtpMessage;
import com.chen.push.PushManager;
import com.chen.push.PushTask;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/***
 * RTP 封包处理类
 *
 */
public class RtpPacketHandler extends SimpleChannelInboundHandler<RtpMessage> {

    private Logger logger = LoggerFactory.getLogger(RtpPacketHandler.class);


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        logger.debug(ctx.channel().remoteAddress().toString());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        PushTask task = PushManager.get(ctx.name());
        if (task != null) {
            try {
                task.shutdown();
            } catch (Exception e) {
            }
            PushManager.remove(ctx.name());
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RtpMessage rtpMessage) throws Exception {
        //logger.debug(rtpMessage.toString());
        System.out.println(rtpMessage);

        if (rtpMessage.getDataType() != 3 && rtpMessage.getDataType() != 4) {
            String taskName = rtpMessage.getSimNum() + "_" + rtpMessage.getLogicChnnel();
            PushTask task = PushManager.get(ctx.name());
            if (task == null) {
                task = PushManager.newPublishTask(ctx.name(), taskName);
                task.start();
            }
            task.write(rtpMessage.getBody());
            if (rtpMessage.getFlag() == 0 || rtpMessage.getFlag() == 2) {
                task.flush();
            }
        }
    }


//    @Override
    protected void channelRead1(ChannelHandlerContext ctx, RtpMessage rtpMessage) throws Exception {
        System.out.println(rtpMessage);

        if (rtpMessage.getDataType() != 3 && rtpMessage.getDataType() != 4) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pis = new PipedInputStream(1000);
            pis.connect(pos);
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(pis);
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rmtp://10.30.50.195:1935/myapp/stream5", 352,288,0);
            grabber.start();

            recorder.setInterleaved(true);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 28
            recorder.setFormat("flv"); // rtmp的类型
            recorder.setFrameRate(25);
            recorder.setPixelFormat(0); // yuv420p
            recorder.start();

            bos.write(rtpMessage.getBody());
            bos.flush();
            pos.write(bos.toByteArray());
            pos.flush();
            bos.reset();

            try {
                Frame frame = grabber.grab();
                recorder.record(frame);
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            } catch (FrameRecorder.Exception e) {
                e.printStackTrace();
            }

        }
    }
}
