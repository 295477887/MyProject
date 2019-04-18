package com.navinfo.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @date 2016/10/19
 * @modify 数据包缓存
 * @copyright opentsp
 */
public class DataPacketCache {

    private static Map<String, byte[]> cache = new ConcurrentHashMap<String, byte[]>();

    private static DataPacketCache instance = new DataPacketCache();
    private DataPacketCache(){}
    public static DataPacketCache getInstance (){
        return instance;
    }


    public Map<String, byte[]> get(){
        Map<String, byte[]> temp = cache;
        return temp;
    }

    public void add(String key , byte[] value){
        cache.put(key, value);
    }

    public void remove(String key){
        cache.remove(key);
    }


    public byte[] get(String key){

        return cache.get(key);
    }

}
