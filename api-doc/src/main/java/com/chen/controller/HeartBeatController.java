package com.chen.controller;

import com.chen.controller.pojo.SubscribeChannel4Swagger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ChenJie
 * @date 2019/4/30
 */
@RestController
@Api(value = "HeartBeatController|一个用来测试swagger注解的控制器")
public class HeartBeatController {

    Logger logger = LoggerFactory.getLogger(HeartBeatController.class);


    /**
     * 心跳
     * @param subscribeChannel  订阅的通道
     * */
    @RequestMapping("/api/media/heartbeat")
    @ApiOperation(value="心跳通道", notes="test: 仅1和2有正确返回")
    public String heartBeat(@RequestBody SubscribeChannel4Swagger subscribeChannel){
        if (logger.isInfoEnabled()) {
            logger.info("心跳，{}", subscribeChannel);
        }

        return "2000";
    }
}
