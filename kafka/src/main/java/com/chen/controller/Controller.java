package com.chen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ChenJie
 * @date 2018/11/9
 */
@RestController
public class Controller {
    @Autowired
    protected KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.producer.locationdata.topic}")
    private String topic;

    @RequestMapping(value="/send")
    public void send(@RequestParam String key){
        kafkaTemplate.send(topic, key,"hello "+ key );

    }

}
