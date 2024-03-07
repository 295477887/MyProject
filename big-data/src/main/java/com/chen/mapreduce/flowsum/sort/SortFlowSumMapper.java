package com.chen.mapreduce.flowsum.sort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by chen on 2019/7/9.
 */
public class SortFlowSumMapper extends Mapper<LongWritable,Text,SortFlowBean,Text> {

    SortFlowBean k = new SortFlowBean();
    Text v = new Text();


    @Override
    public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] fields = line.split("\t");
        String sim = fields[0];
        long upFlow = Long.parseLong(fields[1]);
        long downFlow = Long.parseLong(fields[2]);
        k.set(upFlow, downFlow);
        v.set(sim);
        context.write(k,v);
    }

}
