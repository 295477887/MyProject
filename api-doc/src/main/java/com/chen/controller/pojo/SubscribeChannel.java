package com.chen.controller.pojo;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author: ChenJie
 * @date 2019/4/25
 */
@Data
public class SubscribeChannel {
    /**
     * token码
     * */
    private String token;
    /**
     * 客户端id
     * */
    private String clientId;

    /**
     * 终端通信号
     * */
    @NotNull
    private long tid;
    //订阅、退订通道，每次只能订阅1个

    /**
     * 订阅的通道
     * */
    private Integer channel;
    //心跳的通道，每次可以心跳多个频道
    private List<Integer> channels;

//    public String getClientId() {
//        return clientId;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public void setClientId(String clientId) {
//        this.clientId = clientId;
//    }
//
//    public long getTid() {
//        return tid;
//    }
//
//    public void setTid(long tid) {
//        this.tid = tid;
//    }
//
//    public List<Integer> getChannels() {
//        return channels;
//    }
//
//    public void setChannels(List<Integer> channels) {
//        this.channels = channels;
//    }
}
