package com.chen.mapreduce.flowsum.partition;

import com.chen.mapreduce.flowsum.FlowBean;
import com.chen.mapreduce.flowsum.FlowSumMapper;
import com.chen.mapreduce.flowsum.FlowSumReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Created by Itcast .
 */
public class PartitionFlowSumDriver {
    public static void main(String[] args) throws Exception{
        //通过Job来封装本次mr的相关信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //指定本次mr job jar包运行主类
        job.setJarByClass(PartitionFlowSumDriver.class);

        //指定本次mr 所用的mapper reducer类分别是什么
        job.setMapperClass(FlowSumMapper.class);
        job.setReducerClass(FlowSumReducer.class);

        //指定本次mr mapper阶段的输出  k  v类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        //指定本次mr 最终输出的 k v类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //至少6個分區，因为分区类分了6个分区，
        //如果少于6个，则有的数据找不到分区报错
        //如果多于6个，则多的生成为空数据
        job.setPartitionerClass(ProvincePartitioner.class);

        job.setNumReduceTasks(6);

        //指定本次mr 输入的数据路径 和最终输出结果存放在什么位置
        FileInputFormat.setInputPaths(job,"G:\\study\\bigdata\\data\\input");
        FileOutputFormat.setOutputPath(job,new Path("G:\\study\\bigdata\\data\\output"));

//        job.submit();
        //提交程序  并且监控打印程序执行情况
        boolean b = job.waitForCompletion(true);
        System.exit(b?0:1);
    }
}
