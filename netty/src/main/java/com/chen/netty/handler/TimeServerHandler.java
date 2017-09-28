package com.chen.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: ChenJie
 * @Description:
 * @Date 2017/8/14
 * @Modified by:
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("服务器读取到客户端请求...");
        ByteBuf buf=(ByteBuf) msg;
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");
        System.out.println("the time server receive order:"+body);
        String curentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())):"BAD ORDER";
        ByteBuf resp= Unpooled.copiedBuffer(curentTime.getBytes());
        ctx.write(resp);
        System.out.println("服务器做出了响应");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器readComplete 响应完成");
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println("服务器异常退出"+cause.getMessage());
        ctx.close();
    }
}
