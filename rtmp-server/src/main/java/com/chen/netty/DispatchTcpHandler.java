package com.chen.netty;

import com.chen.message.RtpMessage;
import com.chen.rtmp.SendThread;
import com.chen.util.Convert;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;


/**
 * 服务分发
 *
 * @author zhanhk
 * @version 1.0
 * @date 2015-09-01
 * @modify
 * @copyright Navi Tsp
 */
@Component
@ChannelHandler.Sharable
public class DispatchTcpHandler extends SimpleChannelInboundHandler<RtpMessage> {

    private static final Logger log = LoggerFactory.getLogger(DispatchTcpHandler.class);
    PipedInputStream in ;
    PipedOutputStream out ;

    /**
     * 先打开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
        String client = ctx.channel().remoteAddress().toString();
            log.info("channelRegistered {}", ctx.channel().remoteAddress().toString());
    }

    /**
     * 客户端连接后，再触发此方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SendThread sendThread = new SendThread();
        in = sendThread.getIn();
        out = new PipedOutputStream();
        out.connect(in);
        sendThread.start();

        ctx.fireChannelActive();
        String client = ctx.channel().remoteAddress().toString();
            log.info("channelActive {}", ctx.channel().remoteAddress().toString());
    }

    /**
     * 客户端主动断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String client = channel.remoteAddress().toString();
            log.info("channelInactive {}", client);
        ctx.fireChannelInactive();
    }

    /**
     * 客户端异常,断开连接后，再触发此方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
        log.error("[exceptionCaught] 客户端异常断开连接,[{}],{}", ctx.channel().remoteAddress().toString(), cause.getMessage());
    }

    /**
     * 心跳设置
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                // 超时关闭channel
                log.info("READ心跳超时");
                ctx.close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                log.info("WRITER_IDLE");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                log.info("ALL_IDLE");
                ctx.channel().write("ping\n");
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, RtpMessage rtpMessage){
        try {
            if (rtpMessage.getDataType() != 3 && rtpMessage.getDataType() != 4) {
                String taskName = rtpMessage.getSimNum() + "_" + rtpMessage.getLogicChnnel();
            }

            out.write(rtpMessage.getBody());
            log.info("发送="+ Convert.bytesToHexString(rtpMessage.getBody()));

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
