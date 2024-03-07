package com.chen.mapreduce.flowsum.partition;

import com.chen.mapreduce.flowsum.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

/**
 * Created by chen on 2019/7/9.
 */
public class ProvincePartitioner extends Partitioner<Text,FlowBean> {
    public static HashMap<String, Integer> provinceMap = new HashMap<String, Integer>();

    static{
        provinceMap.put("134", 0);
        provinceMap.put("135", 1);
        provinceMap.put("136", 2);
        provinceMap.put("137", 3);
        provinceMap.put("138", 4);
    }

    @Override
    public int getPartition(Text text, FlowBean flowBean, int i) {
        Integer code = provinceMap.get(text.toString().substring(0, 3));

        if (code != null) {
            return code;
        }
        return 5;
    }
}
