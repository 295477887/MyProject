package com.navinfo.message;

import com.navinfo.cache.DataPacketCache;
import com.navinfo.util.ArraysUtils;
import com.navinfo.util.Convert;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/***
 * RTP 消息解码器
 */
public class RtpMessageDecoder1078 extends ByteToMessageDecoder {
    Logger logger = LoggerFactory.getLogger(RtpMessageDecoder1078.class);

    /**
     * RTP 封包头部最大长度，视频帧长度为30，其他为18
     */
    private static final int MIN_HEADER_LENGTH = 30;
    /**
     * 包头
     * */
    private static final byte[] HEADER = new byte[]{0x30,0x31,0x63,0x64};

    public static void main(String[] args) {

        System.out.println(ArraysUtils.subarrays(HEADER,6,2));
    }


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        if (in != null && in.writerIndex() > in.readerIndex()) {
            String channelKey = channelHandlerContext.channel().remoteAddress().toString();
            byte[] startPacker = DataPacketCache.getInstance().get(channelKey);
            byte[] bytes = new byte[in.writerIndex() - in.readerIndex()];
            in.readBytes(bytes);
            logger.info("收到原始数据:"+Convert.bytesToHexString(bytes));
            if(startPacker != null){
                bytes = ArraysUtils.arraycopy(startPacker, bytes);
            }
            DataPacketCache.getInstance().remove(channelKey);
            if(bytes.length<MIN_HEADER_LENGTH){
                DataPacketCache.getInstance().add(channelKey,bytes);
            }
            else{
                while(bytes.length > HEADER.length){
                    RtpMessage msg = new RtpMessage();

                    //截取包头
                    byte [] head= ArraysUtils.subarrays(bytes,0,HEADER.length);

                    if(Arrays.equals(HEADER, head)){
                        if(bytes.length < MIN_HEADER_LENGTH){
                            DataPacketCache.getInstance().add(channelKey,bytes);
                            break;
                        }
                        int seq = Convert.byte2Int(ArraysUtils.subarrays(bytes,6,2),2);
                        msg.setSeq(seq);
                        String sim = Convert.bytesToHexString(ArraysUtils.subarrays(bytes,8,6));
                        msg.setSimNum(sim);
                        int logicChannel = Convert.byte2Int(ArraysUtils.subarrays(bytes,14,1),1);
                        msg.setLogicChnnel(logicChannel);

                        int fifteenth = Convert.byte2Int(ArraysUtils.subarrays(bytes,15,1),1);
                        int dataType = fifteenth >> 4;
                        msg.setDataType(dataType);
                        int dividedFlag = fifteenth & 0x0F;
                        msg.setFlag(dividedFlag);

                        //与上一关键帧字段的下标
                        int index = 16;
                        //只有非透传数据有时间戳
                        if(dataType != 4){
                            long time = Convert.byte2Long(ArraysUtils.subarrays(bytes,16,8),8);
                            //下标加上时间戳的长度
                            index += 8;
                            msg.setTimestamp(time);
                        }
                        //只有视频帧才有帧间隔
                        if(dataType < 3){
                            int lifi = Convert.byte2Int(ArraysUtils.subarrays(bytes,index,2),2);
                            index += 2;
                            msg.setLIFI(lifi);
                            int lfi = Convert.byte2Int(ArraysUtils.subarrays(bytes,index,2),2);
                            index += 2;
                            msg.setLFI(lfi);
                        }

                        int bodyLen =  Convert.byte2Int(ArraysUtils.subarrays(bytes,index,2),2);
                        msg.setLength(bodyLen);
                        index += 2;
                        //包体长度不够，说明包不完整，缓存
                        if(bytes.length - index < bodyLen){
                            DataPacketCache.getInstance().add(channelKey,bytes);
                            break;
                        }

                        byte [] body = ArraysUtils.subarrays(bytes,index,bodyLen);
                        msg.setBody(body);
                        logger.error("拆包报文:" + Convert.bytesToHexString(ArraysUtils.subarrays(bytes,0,bodyLen+index)));
                        //剩余报文
                        bytes = ArraysUtils.subarrays(bytes, index + bodyLen);
                        out.add(msg);
                    }
                    else{
                        //把假头截掉
                        bytes = ArraysUtils.subarrays(bytes,1);
                    }

                    if(bytes.length < MIN_HEADER_LENGTH){
                        DataPacketCache.getInstance().add(channelKey,bytes);
                        break;
                    }
                }
            }
        }
    }
}
