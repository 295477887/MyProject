/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.chen.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Create Date: 2014-11-4 下午02:42:21<br>
 * Create Author: lWX232692<br>
 * Description :
 */
public class MyDecoder extends ByteToMessageDecoder {


	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
			List<Object> out) throws Exception {
		//UnpooledUnsafeDirectByteBuf(ridx: 0, widx: 1, cap: 1024)
		if (buffer != null) {
			ByteBuffer msg = null;
			try {
				if(buffer.readableBytes() > 0 ){
					msg = ByteBuffer.allocate(buffer.readableBytes()) ;
					byte[] bb = new byte[buffer.readableBytes()] ;
					buffer.readBytes(bb) ;
					msg.put(bb);
					msg.flip();
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg = null ;
			}
			if (msg != null) {
				out.add(msg);
			}
		}
	}


}
