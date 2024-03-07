package com.navinfo.netty.server;

import com.navinfo.netty.handler.TcpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TcpServer {
    private final int port;
    public TcpServer(int port){
        this.port = port;
    }

    public void chen() throws Exception{
        //处理io操作的线程池
        //接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        //处理客户端数据
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            //启动服务的辅助类，有关socket的参数可以通过ServerBootstrap进行设置
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class) //指定NioServerSocketChannel类初始化channel用来接受客户端请求
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>(){//通常会为新SocketChannel通过添加一些handler，来设置ChannelPipeline
                        //ChannelInitializer 是一个特殊的handler，其中initChannel方法可以为SocketChannel 的pipeline添加指定handler。
                        @Override
                        public void initChannel(SocketChannel ch)throws Exception{
                            ch.pipeline().addLast(new TcpServerHandler());
                        }
                    });
            //通过绑定端口，就可以对外提供服务了
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        }
        finally
        {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }
        else
        {
            port = 8080;
        }
        new TcpServer(port).chen();
    }
}
