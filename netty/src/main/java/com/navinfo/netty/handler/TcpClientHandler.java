package com.navinfo.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TcpClientHandler extends ChannelInboundHandlerAdapter{
    private final ByteBuf firstMessage;
    public TcpClientHandler(int firstMessageSize)
    {
        if(firstMessageSize<=0)
        {
            throw new IllegalArgumentException("firstMessageSize:"+firstMessageSize);
        }
        firstMessage = Unpooled.buffer(firstMessageSize);
        for(int i=0;i<firstMessage.capacity();i++){
            firstMessage.writeByte((byte)i);
        }

    }@Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        ctx.close();
    }

}
