package com.chen.mapreduce.flowsum;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by chen on 2019/7/3.
 *  * 在mr程序中，也可以使用我们自定义的类型作为mr的数据类型，前提是需要实现hadoop的序列化机制 writeable
 */
public class FlowSumMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

    @Override
    public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] fields = line.split("\t");
        String sim = fields[1];
        long upFlow = Long.parseLong(fields[fields.length-3]);
        long downFlow = Long.parseLong(fields[fields.length-2]);

        context.write(new Text(sim),new FlowBean(upFlow,downFlow));

    }
}
