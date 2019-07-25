package com.chen.mapreduce.flowsum.sort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 按照总流量倒序排列
 * 把上一步的输出作为输入，map的输出key为bean，输出value为sim
 *
 * Created by chen on 2019/7/3.
 */
public class SortFlowBean implements WritableComparable<SortFlowBean>{

    private  long  upFlow;

    private  long  downFlow;

    private  long  sumFlow;

    public SortFlowBean() {
    }

    public SortFlowBean(long upFlow, long downFlow, long sumFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = sumFlow;
    }


    public SortFlowBean(long upFlow, long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow+downFlow;
    }

    public void  set(long upFlow, long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow+downFlow;
    }

    //这就序列化方法
    @Override
    public void write(DataOutput out) throws IOException {

        out.writeLong(upFlow);

        out.writeLong(downFlow);

        out.writeLong(sumFlow);

    }

    @Override
    public String toString() {
        return upFlow+"\t"+downFlow+"\t"+sumFlow;
    }

    //这是反序列化方法
    //反序列时候  注意序列化的顺序
    //先序列化的先出来
    @Override
    public void readFields(DataInput in) throws IOException {
        this.upFlow = in.readLong();
        this.downFlow=in.readLong();
        this.sumFlow=in.readLong();

    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    @Override
    public int compareTo(SortFlowBean o) {
        return this.sumFlow> o.getSumFlow() ? -1:1;
    }
}
