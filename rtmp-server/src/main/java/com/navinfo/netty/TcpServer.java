package com.navinfo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.Lifecycle;

import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author: ChenJie
 * @date 2019/1/25
 */
public class TcpServer implements Lifecycle {
    private static final Logger LOG = LoggerFactory.getLogger(TcpServer.class);
    private final ServerBootstrap serverBootstrap;
    private final EventLoopGroup group;
    private volatile boolean started;
    private ChannelFuture channel;
    private final String host;
    private final int port;

    public TcpServer(String host, int port, ChannelHandler channelHandler) {
        this.host = host;
        this.port = port;
        this.serverBootstrap = new ServerBootstrap();
        this.group = new NioEventLoopGroup();
        this.serverBootstrap.channel(NioServerSocketChannel.class);
        this.serverBootstrap.group(this.group);
        this.serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        this.serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        this.serverBootstrap.childHandler(channelHandler);
    }


    @Override
    public void start() {
        try {
            InetAddress addr = InetAddress.getByName(this.host);
            this.channel = this.serverBootstrap.bind(new InetSocketAddress(addr, this.port)).sync();
        } catch (Exception e) {
            LOG.error("Unable to start TCP server '", e);
            this.started = false;
            return;
        }
        LOG.info("Gateway tcp Server started on {}:{}",  this.host, this.port);
        this.started = true;
    }

    @PreDestroy
    @Override
    public void stop() {
        if(this.started) {
            try {
                this.channel.channel().close();
                return;
            } catch (Exception e) {
                LOG.error("Unable to stop server " , e);
            } finally {
                this.group.shutdownGracefully(0L, 0L, TimeUnit.MILLISECONDS);
            }

        }
    }

    @Override
    public boolean isRunning() {
        return started;
    }
}
