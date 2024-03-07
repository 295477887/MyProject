package com.navinfo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: ChenJie
 * @date 2019/4/4
 */
public class Log {
    public static final Logger LOG = LoggerFactory.getLogger(Log.class);

    public static void d(String tag,String cause){
        LOG.debug(tag+cause);
    }

    public static void i(String tag,String cause){
        LOG.info(tag + cause);
    }

    public static void w(String tag,String cause){
        LOG.warn(tag+cause);
    }

    public static void e(String tag,String cause){
        e(tag+cause,null);
    }

    public static void e(String tag,String cause,Throwable throwable){
        LOG.error(tag+cause,throwable);
    }

}
