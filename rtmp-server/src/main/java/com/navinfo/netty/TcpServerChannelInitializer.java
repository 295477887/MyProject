package com.navinfo.netty;

import com.navinfo.message.RtpMessageDecoder1078;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Initializer for channel which support Landu
 */
@Component
public final class TcpServerChannelInitializer extends ChannelInitializer<Channel> {

    private DispatchTcpHandler dispatchTcpHandler;

    @Value("${tcp.heartbeat.time:300}")
    private int heartbeat;


    @Autowired
    public TcpServerChannelInitializer(DispatchTcpHandler dispatchTcpHandler) {
        this.dispatchTcpHandler = dispatchTcpHandler;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //配置服务端监听读超时，即无法收到客户端发的心跳信息的最长时间间隔：默认5分钟
        pipeline.addLast("ping", new IdleStateHandler(heartbeat, 0, 0, TimeUnit.SECONDS));

//        pipeline.addLast(new RtpMessageDecoder());
//        pipeline.addLast(new RtpMessageDecoder2());
        pipeline.addLast(new RtpMessageDecoder1078());
        pipeline.addLast("handle", dispatchTcpHandler);
    }
}
