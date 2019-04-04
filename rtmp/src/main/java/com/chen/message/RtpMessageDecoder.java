package com.chen.message;

import com.chen.util.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/***
 * RTP ��Ϣ������
 *
 * @author ������
 * @date 2018/7/16 0016 18:08
 */
public class RtpMessageDecoder extends ByteToMessageDecoder {

    //RTP ���ͷ����󳤶ȣ�����ĳЩ�ֶ�û�У�����Ӧ��ȡ�����Ǹ����ȣ�
    private final int MIN_HEADER_LENGTH = 30;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        if (in == null || in.readableBytes() <= MIN_HEADER_LENGTH)  //����㣬����30���ֽ�ʱ���ܶ��������峤��
            return;

        in.markReaderIndex();
        //�����޹ؽ�Ҫ������
        in.skipBytes(5);

        RtpMessage msg = new RtpMessage();
        //M��1 bit����PT��7 bit�� ��ռ�� 1 ���ֽ�
        byte b = in.readByte();

        msg.setM((byte)((b >> 7) & 0x1));
        msg.setPT((byte)(b & 0x7f));

        msg.setSeq(in.readShort());
        byte[] simNum = new byte[6];
        in.readBytes(simNum);
        msg.setSimNum(StringUtil.convertByteToHexStringWithoutSpace(simNum));

        msg.setLogicChnnel(in.readByte());

        //�������ͣ�4 bit�����ְ�������ǣ�4 bit����ռ��һ���ֽ�
        b = in.readByte();

        msg.setDataType((byte) (b >> 4));
        msg.setFlag((byte) (b & 0x0f));

        if (msg.getDataType() != 4) {   //��Ϊ͸����������
            msg.setTimestamp(in.readLong());
        }

        if (msg.getDataType() != 3 && msg.getDataType() != 4) { //��Ƶ�������Ͳ��������ֶ�
            msg.setLIFI(in.readShort());
            msg.setLFI(in.readShort());
        }


        //�����峤��
        msg.setLength(in.readShort());

        if (in.readableBytes() < msg.getLength()) {
            in.resetReaderIndex();
            return;
        }
        //������
        byte[] body = new byte[msg.getLength()];
        in.readBytes(body);
        msg.setBody(body);
        list.add(msg);
    }
}