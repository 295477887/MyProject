package com.chen.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author: ChenJie
 * @date 2018/8/31
 */
@Slf4j
public class TestLog {
    @Test
    public void testlog(){
        logger.info("测试log");
    }
}
