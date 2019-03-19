package com.chen.pojo;

import java.util.List;

/**
 * @author: ChenJie
 * @date 2019/3/18
 */
public class Data {
    private List<CourseRate> datas;

    public List<CourseRate> getDatas() {
        return datas;
    }

    public void setDatas(List<CourseRate> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "Data{" +
                "datas=" + datas +
                '}';
    }
}
