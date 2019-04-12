package com.chen.cache;

import com.chen.forward.ForwardThread;

import java.io.PipedOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ChenJie
 * @date 2019/4/11
 */
public class ForwardCache {
    private ForwardCache(){}
    public static ForwardCache cache = new ForwardCache();
    public static ForwardCache getInstance(){
        return cache;
    }


    private Map<String, ForwardThread> forwardThreadMap = new ConcurrentHashMap<>(100);

    public void putForwardThread(String name,ForwardThread thread){
        forwardThreadMap.put(name, thread);
    }

    public ForwardThread getForwardThread(String name){
        return forwardThreadMap.get(name);
    }

    public void deleteForwardThread(String name){
        forwardThreadMap.remove(name);
    }

    /**
     * 转发中转流缓存
     */
    private Map<String ,PipedOutputStream> outStreamMap = new ConcurrentHashMap<>(100);
    public void putOutStream(String name,PipedOutputStream outputStream){
        outStreamMap.put(name, outputStream);
    }

    public PipedOutputStream getOutStream(String name){
        return outStreamMap.get(name);
    }

    public void deleteOutStream(String name){
        outStreamMap.remove(name);
    }



}
