package com.navinfo.message;

import com.navinfo.cache.DataPacketCache;
import com.navinfo.util.ArraysUtils;
import com.navinfo.util.Convert;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/***
 * RTP 消息解码器
 */
public class RtpMessageDecoder2 extends ByteToMessageDecoder {
    Logger logger = LoggerFactory.getLogger(RtpMessageDecoder2.class);

    //RTP 封包头部最大长度（可能某些字段没有，所以应该取最大的那个长度）
    private final int MIN_HEADER_LENGTH = 30;

    private static final byte[] HEADER = new byte[]{0x12,0x0C};


    private static final int LENGTH = 980;


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        //最坏打算，至少30个字节时才能读到数据体长度
        if (in == null || in.readableBytes() <= MIN_HEADER_LENGTH) {
            return;
        }
        String channelKey = channelHandlerContext.channel().remoteAddress().toString();
        byte[] startPacker = DataPacketCache.getInstance().get(channelKey);

        byte[] bytes = null;
        if (in.writerIndex() > in.readerIndex()) {
            bytes = new byte[in.writerIndex() - in.readerIndex()];
            in.readBytes(bytes);
            if (startPacker != null) {
                //有分包拼接上一包尾数据
                bytes = ArraysUtils.arraycopy(startPacker, bytes);
            }

            List<byte[]> result = new ArrayList<>();
            int index = -1;
            for (int i = 0, length = bytes.length; i < length; i++) {
                byte[] temp;
                //截取头标识
                byte [] fourByte= ArraysUtils.subarrays(bytes,i,HEADER.length);
                if (index == -1) {// 寻找包头
                    if (HEADER == fourByte) {
                        index = i;
//                        if((bytes.length - index) == LENGTH){
//                            result.add(temp);
//                        }
                    }
                } else {
                    if (HEADER == fourByte) {// 寻找下一包包头
                        temp = ArraysUtils.subarrays(bytes, index, i - index);
                        result.add(temp);
                        index = i;
                    }
                }

            }
            if (index != -1) {
                result.add(ArraysUtils.subarrays(bytes, index, bytes.length - index));
            }

            for (byte[] packet : result) {
                if (packet != null) {
                    byte[] packetHeader = ArraysUtils.subarrays(packet, 0, HEADER.length);

                    if (packetHeader == HEADER) {
                        //完整包
                        if(packet.length < 18){
                            DataPacketCache.getInstance().add(channelKey, packet);
                            continue;
                        }
                        else{



                        }


                        DataPacketCache.getInstance().remove(channelKey);
                        logger.info("T-->TA:" + Convert.bytesToHexString(packet));
                        //符合完整包派发
                        out.add(packet);
                    }

//                    else if (packet[0] == LCConstant.pkBegin) {
//                        //不符合加入缓存
//                        DataPacketCache.getInstance().add(channelKey, packet);
//                    }
                }
            }







            logger.info("收到视频原始报文:" + Convert.bytesToHexString(bytes));

            byte[] header = ArraysUtils.subarrays(bytes, 0, 26);
            RtpMessage msg = new RtpMessage();
            String sim = Convert.bytesToHexString(ArraysUtils.subarrays(header, 4, 6));
            msg.setSimNum(sim);
            msg.setLogicChnnel((byte) Convert.byte2Int(ArraysUtils.subarrays(header, 10, 1), 1));

            int length = Convert.byte2Int(ArraysUtils.subarrays(header, 24, 2), 2);
            msg.setLength(length);

            byte[] body = ArraysUtils.subarrays(bytes, 27);
            msg.setBody(body);

            out.add(msg);
        }
    }
}
