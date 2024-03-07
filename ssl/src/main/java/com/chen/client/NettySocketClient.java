package com.chen.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.net.ssl.SSLEngine;

import com.chen.server.ContextSSLFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslHandler;

public class NettySocketClient {
	
	private EventLoopGroup group ;
	
	private Channel channel = null ; 
	public void connect(String ip , int port){
		group = new NioEventLoopGroup();
		try{
			Bootstrap strap = new Bootstrap();
			strap.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.option(ChannelOption.SO_KEEPALIVE , true)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					ChannelPipeline pieple = socketChannel.pipeline() ;
					pieple.addLast("decoder" , new MyClientDecoder()) ;
					pieple.addLast("encoder" , new MyClientEncoder()) ;
					pieple.addLast("handler" , new NettySocketSSLClientHandler()) ;
					 SSLEngine engine = ContextSSLFactory.getSslContext2().createSSLEngine();
				     engine.setUseClientMode(true);
				     pieple.addFirst("ssl", new SslHandler(engine));
				}
			});
		SocketAddress address = new InetSocketAddress(ip, port);
		final ChannelFuture future = strap.connect(address).sync();
		channel = future.awaitUninterruptibly().channel();
		System.out.println("连接成功， channel =" + channel.remoteAddress());
		}catch(Exception e ){
			e.printStackTrace();
			group.shutdownGracefully() ;
		}finally{
			
		}
	}
	private static SslHandler sslHandlerClient = null ;
	public static SslHandler getSslHandler(){
		if(sslHandlerClient == null){
			SSLEngine sslEngine = ContextSSLFactory.getSslContext2().createSSLEngine() ;
			sslEngine.setUseClientMode(true) ;
			sslHandlerClient = new SslHandler(sslEngine);
		}
		return sslHandlerClient ;
	}
	public static void main(String[] args) {
		new NettySocketClient().connect("172.16.1.86", 22222) ;
	}
}
