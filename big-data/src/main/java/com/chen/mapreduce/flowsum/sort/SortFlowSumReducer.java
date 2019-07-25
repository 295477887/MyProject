package com.chen.mapreduce.flowsum.sort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by chen on 2019/7/9.
 */
public class SortFlowSumReducer extends Reducer<SortFlowBean,Text,Text,SortFlowBean>{

    @Override
    public void reduce(SortFlowBean key,Iterable<Text> values,Context context) throws IOException, InterruptedException {
        context.write(values.iterator().next(),key);
    }
}
