package com.navinfo.message;

public class RtpMessage {

    /**
     * 标志位，确定是否是完整数据帧的边界，占 1 bit
     */
    private int M;
    /**
     * 负载类型，占 7 bit
     */
    private int PT;
    /**
     * 包序号
     */
    private int seq;
    /**
     * sim 卡号
     */
    private String simNum;
    /**
     * 逻辑通道号
     */
    private int logicChnnel;
    /**
     * 数据类型
     * 0000：视频I帧
     * 0001：视频P帧
     * 0010：视频B帧
     * 0011：音频数据
     * 0100：透传数据
     */
    private int dataType;
    /**
     * 分包处理标记
     * 0000：原子包，不可被拆分
     * 0001：分包处理时的第一个包
     * 0010：分包处理时的最后一个包
     * 0011：分包处理时的中间包
     */
    private int flag;
    /**
     * 时间戳
     */
    private long timestamp;
    private int LIFI;
    private int LFI;
    /**
     * 数据体长度
     */
    private int length;
    /**
     * 数据体
     */
    private byte[] body;

    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public int getPT() {
        return PT;
    }

    public void setPT(int PT) {
        this.PT = PT;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getSimNum() {
        return simNum;
    }

    public void setSimNum(String simNum) {
        this.simNum = simNum;
    }

    public int getLogicChnnel() {
        return logicChnnel;
    }

    public void setLogicChnnel(int logicChnnel) {
        this.logicChnnel = logicChnnel;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getLIFI() {
        return LIFI;
    }

    public void setLIFI(int LIFI) {
        this.LIFI = LIFI;
    }

    public int getLFI() {
        return LFI;
    }

    public void setLFI(int LFI) {
        this.LFI = LFI;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RtpMessage{" +
                "M=" + M +
                ", PT=" + PT +
                ", seq=" + seq +
                ", simNum='" + simNum + '\'' +
                ", logicChnnel=" + logicChnnel +
                ", dataType=" + dataType +
                ", flag=" + flag +
                ", timestamp=" + timestamp +
                ", LIFI=" + LIFI +
                ", LFI=" + LFI +
                ", length=" + length +
                '}';
    }
}
