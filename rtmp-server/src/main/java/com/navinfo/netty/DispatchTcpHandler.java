package com.navinfo.netty;

import com.navinfo.cache.ForwardCache;
import com.navinfo.forward.ForwardThread;
import com.navinfo.message.RtpMessage;
import com.navinfo.message.VideoProperty;
import com.navinfo.util.ArraysUtils;
import com.navinfo.util.Convert;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


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

    @Autowired
    private VideoProperty videoProperty;

    private static Map<String,byte[]> cache = new ConcurrentHashMap<>();


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

        ctx.fireChannelActive();
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
    public void channelRead0(ChannelHandlerContext channelHandlerContext, RtpMessage rtpMessage) {
        String channel = rtpMessage.getSimNum() + "_" + rtpMessage.getLogicChnnel();
        try {
            //分包
            int divideFlag = rtpMessage.getFlag();
            //第一个包
            if(divideFlag == 1){
                cache.remove(channel);
                cache.put(channel,rtpMessage.getBody());
                return;
            }
            //中间包
            else if(divideFlag == 3){
                byte [] start = cache.get(channel);
                //合并
                cache.put(channel,ArraysUtils.arraycopy(start,rtpMessage.getBody()));
                return;
            }
            //最后1包
            else if(divideFlag == 2){
                byte [] start = cache.get(channel);
                //合并
                rtpMessage.setBody(ArraysUtils.arraycopy(start,rtpMessage.getBody()));
                cache.remove(channel);
            }
            //不分包
            else if(divideFlag == 0){
                cache.remove(channel);
            }
            //非视频数据不处理
            if (rtpMessage.getDataType() != 3 && rtpMessage.getDataType() != 4) {
                ForwardThread sendThread = ForwardCache.getInstance().getForwardThread(channel);
                PipedOutputStream out = ForwardCache.getInstance().getOutStream(channel);
                if (sendThread == null) {
                    //视频流如需修改参数，在此修改
                    String toUrl = videoProperty.getToUrlPrefix() + channel;
                    sendThread = new ForwardThread(toUrl,videoProperty);
                    ForwardCache.getInstance().putForwardThread(channel, sendThread);
                    out = new PipedOutputStream();
                    ForwardCache.getInstance().putOutStream(channel,out);

                    PipedInputStream in = sendThread.getIn();
                    out.connect(in);
                    sendThread.start();
                }

                //判断sps、pps
                //sps、pps帧的时候先缓存起来，
                //待到取出 I 帧时先将前面缓存的 sps、pps发送出去，然后再将I帧发送出去，
                byte [] body = rtpMessage.getBody();
                int frameFlag = Convert.byte2Int(ArraysUtils.subarrays(body,4,1),1);
                //SPS
                if(frameFlag == 0x67){
                    byte [] sps = ArraysUtils.subarrays(body,0,18);
                    out.write(sps);
                    log.info(channel + "=发送sps=" + Convert.bytesToHexString(sps));

                    byte [] pps = ArraysUtils.subarrays(body,18,8);
                    out.write(pps);
                    log.info(channel + "=发送pps=" + Convert.bytesToHexString(pps));

                    byte [] iFrame = ArraysUtils.subarrays(body,26);
                    out.write(iFrame);
                    log.info(channel + "=发送I帧=" + Convert.bytesToHexString(iFrame));

                }
                else if(frameFlag == 0x41){
                    out.write(body);
                    log.info(channel + "=发送P帧=" + Convert.bytesToHexString(body));

                }

//                out.write(rtpMessage.getBody());
//                log.info(channel + "=发送=" + Convert.bytesToHexString(rtpMessage.getBody()));
            }
        } catch (Exception e) {
            log.error("转发媒体流失败:", e);
        }
    }
}
