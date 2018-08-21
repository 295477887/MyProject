package com.chen.controller;

import com.chen.service.MqttGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ChenJie
 * @date 2018/8/21
 */
@RestController(value="/mqtt")
public class MessageController {

    @Autowired
    MqttGateway mqttGateway;

    @RequestMapping(value="/sendMsg")
    public String sendMsg(@RequestParam String message){

        mqttGateway.sendToMqtt("defualt-topic:"+message);
        return "success";
    }
}
